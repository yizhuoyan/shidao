;+function () {
    "use strict";

    var $addForm;
    var $formSubmitBtn;

    $(function () {
        $addForm = $("#addForm");
        $formSubmitBtn = $("button[type=submit]", $addForm);
        //添加表单提交事件
        $addForm.submit(handleFormSubmit);

    });
    /**
     处理表单提交事件
     */


    var handleFormSubmit = function () {
        $formSubmitBtn.disabled(true);
        var url="/platform/role/add";
        $.ajaxPost(url,$(this).serialize())
            .done(function (data) {
                        window.top.confirmDialog("新增成功!是否继续?", function (yes) {
                            if (!yes) {
                                window.location.href ="/platform/rolemanage/list/view.html";
                            }
                        })
                })
            .always(function () {
                $formSubmitBtn.disabled(false);
            });
        return false;
    };
}();