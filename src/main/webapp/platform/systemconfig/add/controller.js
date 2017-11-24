$(function () {
    //添加表单提交事件
    $("#add-form").submit(handleModSubmit);

});

/**
 处理表单提交事件
 */
var handleModSubmit = function () {
    var url="/platform/systemconfig/add";
    $.ajaxPost(url, $(this).serialize())
        .done(
            function (resp) {
                    toast("新增成功!");
            });
    return false;
};