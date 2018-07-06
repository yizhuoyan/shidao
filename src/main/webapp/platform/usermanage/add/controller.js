;+function () {
    "use strict";
    var $addFormEL;
    var $formSubmitBtnEL;

    $(function () {
        $addFormEL = $("#addForm");
        $formSubmitBtnEL = $addFormEL.find("button[type=submit]");
        //添加表单提交事件
        $addFormEL.submit(handleFormSubmit);


    });

    /**
     处理表单提交事件
     */
    var handleFormSubmit = function () {
        $formSubmitBtnEL.disabled(true);
        var url="/platform/user/add";
        $.ajaxPost(url, $(this).serialize())
            .done(function (resp) {
                    var message = "新增成功!是否继续新增?";
                    window.confirmDialog(message, function (yes) {
                        if (!yes) {
                            window.location.href = "/platform/usermanage/list/view.html";
                        }
                    });
            }).always(function () {
            $formSubmitBtnEL.disabled(false);
        });
        return false;
    };

}();