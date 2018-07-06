;+function () {
    "use strict";

    var $addFormEL;
    var $addFormSubmitBtnEL;

    $(function () {
        $addFormEL = $("#addForm");
        $addFormSubmitBtnEL = $addFormEL.find("button[type=submit]");

        //添加表单提交事件
        $addFormEL.submit(handleAddFormSubmit);

    });

    /**
     处理表单提交事件
     */
    var handleAddFormSubmit = function () {
        $addFormSubmitBtnEL.disabled(true);
        var url = "/platform/config/add";
        $.ajaxPost(url, $(this).serialize(), function (resp) {
            toast("新增成功!");
        }).always(function () {
            $addFormSubmitBtnEL.disabled(false);
        });

        return false;
    };

}();