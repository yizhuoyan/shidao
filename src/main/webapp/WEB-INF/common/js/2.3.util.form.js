
;+function (window) {
    "use strict";
    //找到页面中所有Form表单
    window.addEventListener("load",function () {
       var forms=document.forms;
        for(var i=forms.length,form;i-->0;){
            form=forms[i];
            extendFormLabel(form);
            extendMethod2Form(form);
        }
    });

    /**
     * 查找网页中所有label，使其关联到后面的对应的input，textarea,select
     */
    var extendFormLabel=function (form) {
       //查找表单下的label
        var labels=form.getElementsByTagName("label");
        for(var i=labels.length,label;i-->0;){
            label=labels[i];
            //已关联的跳过
            if(!label.getAttribute("for")) {
                //如果label后面是input，textarea,select才处理
                var nextSiblingElement = label.nextElementSibling;
                if (nextSiblingElement) {
                    switch (nextSiblingElement.tagName) {
                        case "INPUT":
                        case "SELECT":
                        case "TEXTAREA":
                            //如果输入框已有id，则直接使用，否则使用其name属性
                            var targetId=nextSiblingElement.id;
                            if (!targetId) {
                                targetId= nextSiblingElement.name;
                                //如果name属性也不存在,自动生成一个
                                if(!targetId){
                                    targetId="el"+String(Math.random()).substr(2);
                                }
                            }
                            nextSiblingElement.id=targetId;
                            label.setAttribute("for", targetId);
                    }
                }
            }
        }
    };





    var extendMethod = {
        "checkRadio": function (radioName, checkValue) {
            var radios = this[radioName];
            if (radios) {
                checkValue = String(checkValue);

                for (var i = radios.length, r; i-- > 0;) {
                    if ((r = radios[i]).value === checkValue) {
                        if (!r.checked) {
                            r.checked = true;
                        }
                        return;
                    }
                }
            }
        }
    };
    var extendMethod2Form=function (form) {
        //extends
        var names = Object.getOwnPropertyNames(extendMethod);
        for(var name in extendMethod){
            if(extendMethod.hasOwnProperty(name)){
                form[name]=extendMethod[name];
            }
        }
    }

}(window);


