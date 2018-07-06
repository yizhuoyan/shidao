;+function ($) {
    //全局ajax设置
    $.ajaxSetup({
        dataType:"json",
        traditional:true
    });


    /**
     * 注册全局的ajax错误处理
     */
    $(document).ajaxError(function (event, jqXHR, ajaxSettings, thrownError) {
        console.log("ajax请求失败，详细原因是：");
        console.log(thrownError);
        window.top.toast("请求失败，请联系管理员");
    });

    $.load=function (url,done) {
        var defer=$.Deferred();
        $.getJSON(url,function (resp) {
            if(resp.ok){
                if(done){
                    done(resp.data);
                }
                defer.resolve(resp.data);
            }else{
                handleAjaxFail(resp);
                defer.reject(resp);
            }
        });
        return defer.promise();
    };
    /***
     * 封装GET，POST，PUT，DELETE方法
     */
    $.ajaxGet=function (url,data,done) {
        if(typeof data==="function"){
            done=data;
            data=null;
        }
        var defer=$.Deferred();
        $.getJSON(url,data,function (resp) {
            if(resp.ok){
                if(done){
                    done(resp.data);
                }
                defer.resolve(resp.data);
            }else{
                handleAjaxFail(resp);
                defer.reject(resp);
            }
        });
        return defer.promise();
    };

    $.ajaxDelete=function (url,data,done) {
        if(typeof data==="function"){
            done=data;
            data=null;
        }
        var defer=$.Deferred();
        $.post(url,data,function (resp) {
            if(resp.ok){
                if(done){
                    done(resp.data);
                }
                defer.resolve(resp.data);
            }else{
                handleAjaxFail(resp);
                defer.reject(resp);
            }
        });
        return defer.promise();
    };
    $.ajaxPost=function (url,data,done) {
        if(typeof data==="function"){
            done=data;
            data=null;
        }
        var defer=$.Deferred();
        $.post(url,data,function (resp) {
            if(resp.ok){
                if(done){
                    done(resp.data);
                }
                defer.resolve(resp.data);
            }else{
                handleAjaxFail(resp);
                defer.reject(resp);
            }
        });
        return defer.promise();
    };
    $.ajaxPut=function (url,data,done) {
        if(typeof data==="function"){
            done=data;
            data=null;
        }
        var defer=$.Deferred();
        $.post(url,data,function (resp) {
            if(resp.ok){
                if(done){
                    done(resp.data);
                }
                defer.resolve(resp.data);
            }else{
                handleAjaxFail(resp);
                defer.reject(resp);
            }
        });
        return defer.promise();
    };
    /**
     * 清理label标签中的特殊字符
     * @param text
     * @returns {string}
     */
    var clearLabelText=function (text) {
        return text.trim().replace(/[:*：]/g,"");
    }
    /**
     * 获取网页中所有label，构建如下的map
     * key为label标签for属性指向的标签的name属性
     * value为lable标签的文本内容，当然是清理后的
     * @returns {{}}
     */
    var getLabelNameMap=function () {
        var labels=document.getElementsByTagName("label");
        var result={};
        var name;
        for(var i=labels.length,label;i-->0;){
            label=labels[i];
            //查找name属性
            name=label.getAttribute("name");

            if(!name) {
                //查找for属性
                var forValue = label.getAttribute("for");
                if(!forValue){
                    //for属性未定义，直接忽略
                    continue;
                }
                var forElement = document.getElementById(forValue);
                //如果找不到对应，则直接使用for属性值
                if(!forElement) {
                    name=forValue;
                }else{
                    var forElementName = forElement.getAttribute("name");
                    //如果关联标签name属性不存在，则忽略
                    if (!forElementName) {
                        continue;
                    }else{
                        name=forElementName;
                    }
                }
            }

            label.setAttribute("name",name);
            result[name]=clearLabelText(label.textContent);
        }
        return result;
    };
    /**
     * 保存可用于消息替换的map
     */
    var messageKeyMap;
    /**
     * 全局处理ajax请求失败
     * 处理消息中的可替换内容
     */
    var handleAjaxFail=function (resp) {
        var messages=resp.messages;
        if(!messageKeyMap){
            messageKeyMap=getLabelNameMap();
        }
        for(var i=0,z=messages.length,m;i<z;i++){
            messages[i]=handleMessage(messages[i],messageKeyMap);
        }
        if(window.toast) {
            window.toast(messages);
        }
    };
    var MESSAGE_VAR=/\[([^\]]+)\]/g;
    var handleMessage=function (m,context) {
        var lastPos = 0;
        var varContent;
        var varResult;
        var message="";
        //解析消息中变量代码
        while (varContent = MESSAGE_VAR.exec(m)) {
            if (lastPos < varContent.index) {
                message+=m.substring(lastPos, varContent.index);
            }
            varResult=context[varContent[1]];
            if(varResult){
                message+=varResult;
            }else{
                message+=varContent[0];
            }
            lastPos = varContent.index + varContent[0].length;
        }
        //如果最后还有html代码
        if (lastPos < m.length) {
            message+=m.substr(lastPos);
        }
        return message;
    }
}($);