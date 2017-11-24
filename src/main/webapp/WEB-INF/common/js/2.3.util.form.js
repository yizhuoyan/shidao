
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
     * 处理lable和输入框
     * 查找网页中所有label，使其关联到后面的对应的input，textarea
     */
    var extendFormLabel=function (form) {
       //查找表单下的label
        var labels=form.getElementsByTagName("label");
        for(var i=labels.length,label;i-->0;){
            label=labels[i];
            //已关联的跳过
            if(!label.getAttribute("for")) {
                //如果label后面是input，textarea才处理
                var nextSiblingElement = label.nextElementSibling;
                if (nextSiblingElement) {
                    switch (nextSiblingElement.tagName) {
                        case "INPUT":
                        case "TEXTAREA":
                            //如果输入框已有id，则直接使用，否则使用其name属性
                            if (nextSiblingElement.name) {
                                if (!nextSiblingElement.id) {
                                    nextSiblingElement.id = nextSiblingElement.name;
                                }
                                label.setAttribute("for", nextSiblingElement.id);
                            }
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


