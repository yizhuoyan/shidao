;+function () {
    "use strict";

    var $addFormEL;
    var $addFormSubmitBtnEL;
    var $questionKindSelectEL;

    $(function () {
        $addFormEL = $("#addForm");
        $addFormSubmitBtnEL = $("button[type=submit]", $addFormEL);
        $questionKindSelectEL=$("#questionKindSelect");
        //加载题目类型列表
        loadQuestionKindList();
        //添加表单提交事件
        $addFormEL.submit(handleFormSubmit);

    });
    var loadQuestionKindList=function () {
        var url="/questionhub/questionkind/list";
        $.load(url,function (list) {
            var frg=document.createDocumentFragment();
            for(var i=0,z=list.length,item;i<z;i++){item=list[i];
                var option=document.createElement("option");
                option.value=item.id;
                option.textContent=item.name;
                frg.appendChild(option);
            }

            $questionKindSelectEL.empty().append(frg);
        })
    }
    /**
     处理表单提交事件
     */
    var handleFormSubmit = function () {
        $addFormSubmitBtnEL.disabled(true);
        var url="/questionhub/question/add";
        $.ajaxPost(url,$(this).serialize())
            .done(function (data) {
                        window.top.confirmDialog("新增成功!是否继续?", function (yes) {
                            if (!yes) {
                                window.location.href ="/questionhub/question/list/view.html";
                            }
                        })
                })
            .always(function () {
                $addFormSubmitBtnEL.disabled(false);
            });
        return false;
    };
}();