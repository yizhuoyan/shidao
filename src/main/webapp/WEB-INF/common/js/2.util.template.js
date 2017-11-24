;+function () {
    "use strict";
    //匹配js代码正则 <- ... ->
    var REG_JS = /<-([\s\S]*?)->/g,
        //匹配表达式正则 $()
        REG_EXP = /\$\(([^)]+?)\)/g;

    /**
     * 通过<script type="text/tempate">标签构建模板方法
     *
     * @param {String} id 方法名称，格式为xxx(a,b)
     * @param {String} template 字符串模板
     */
    var TemplateFunction = function (id, templateHtml) {
        this.id = id;
        //模板字符串
        this.templateSource = templateHtml;
        //模板方法名称
        this.functionName;
        //模板方法参数
        this.functionArgumentNames;
        //模板方法体
        this.body = [];

        //生成方法引用
        this.functionReference;

        privateMethod.init.apply(this);
    };
    var privateMethod = {
        init: function () {
            var id = this.id;
            var result = staticMethod.parseFunctionNameAndArgumentNames(id);
            this.functionName = result[0];
            this.functionArgumentNames = result[1];
        }
    };
    /**
     * 公开方法
     */
    TemplateFunction.prototype = {
        //构建模板方法
        build: function () {
            var out = this.body;
            out.push("var _='';");
            //添加try-finally保证模板方法返回值
            out.push("try{");
            //添加工具方法,用于过滤null和undefined和特殊字符
            out.push("function $(s){if(s===null||s===undefined)return '';return String(s).replace(/[<>&]/g,function (c){return {'<':'&lt;','>':'&gt;','&':'&amp;'}[c]})}");
            //构建方法体
            staticMethod.parse(this.templateSource, out);
            //添加finally保证模板方法返回值
            out.push("}finally{return _;}");

            var funBody = out.join("\n");
            var args = this.functionArgumentNames;
            try {
                //创建方法
                var f = this.functionReference = args ? new Function(args, funBody) : new Function(funBody);
                return f;
            } catch (e) {
                console.log(e);
                console.log(funBody);
            }
        },
        export: function (ctx) {
            ctx = ctx || window;
            if (!this.functionReference) {
                this.build();
            }
            ctx[this.functionName] = this.functionReference;
        }
    };

    var staticMethod = {
        parseFunctionNameAndArgumentNames: function (funName) {
            var begin = funName.indexOf("(");
            if (begin == -1) {
                return [funName, null];
            } else {
                return [funName.substring(0, begin), funName.substring(begin + 1, funName.lastIndexOf(")"))];
            }
        },
        parse: function (template, out) {
            var lastPos = 0;
            var jsContent;
            var js = staticMethod.parseJs;
            var html = staticMethod.parseHtml;

            //解析模板中js代码和普通html代码
            while (jsContent = REG_JS.exec(template)) {
                if (lastPos < jsContent.index) {
                    html(template.substring(lastPos, jsContent.index), out);
                }
                js(jsContent[1], out);
                lastPos = jsContent.index + jsContent[0].length;
            }
            //如果最后还有html代码
            if (lastPos < template.length) {
                html(template.substr(lastPos), out);
            }
        },
        parseJs: function (code, out) {
            out.push(code);
        },
        parseExp: function (exp, out) {
            out.push("_+=$(" + exp + ");");
        },
        parseHtml: function (html, out) {
            //不处理空白字符
            if ((html = html.trim()).length === 0) {
                return;
            }
            //html代码中可以书写表达式
            var lastPartIndex = 0;
            var expContent = null;
            var text = staticMethod.parseText;
            var exp = staticMethod.parseExp;
            while (expContent = REG_EXP.exec(html)) {
                if (lastPartIndex < expContent.index) {
                    text(html.substring(lastPartIndex, expContent.index), out);
                }
                exp(expContent[1], out);
                lastPartIndex = expContent.index + expContent[0].length;
            }
            if (lastPartIndex < html.length) {
                text(html.substr(lastPartIndex), out);
            }
        },
        parseText: function (txt, out) {
            if (txt) {
                //双引号转义
                txt = txt.replace(/"/g, '\\"');
                //换行符分割
                var lines = txt.split(/\r?\n/);
                if (lines.length > 0) {
                    var i = 0, z = lines.length - 1
                    while (i < z) {
                    //一行一行输出,每行多输出一个换行
                        out.push('_+="' + lines[i++] + '";');
                    }
                    //最后一个不输出换行
                    out.push('_+="' + lines[z] + '";');
                }
            }
        }

    };


//expode
    var templates = document.querySelectorAll("script[type='text/template']");
    Array.prototype.forEach.call(templates, function (t) {
        var templateHTML = t.innerHTML;
        var name= t.getAttribute("name");
        if(!name){
            name=t.id;
        }
        var tf = new TemplateFunction(name, templateHTML);
        //直接把模板id暴露为window下方法
        tf.export(window);
    });
}(window, document);