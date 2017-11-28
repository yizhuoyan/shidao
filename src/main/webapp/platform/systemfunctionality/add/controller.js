;+function () {
    


var $addFormEL;
var $addFormSubmitBtnEL;
var $pickParentBtnEL;
var $clearParentBtnEL;
var $parentFunctionalityIdEL;
var $parentFunctionalityNameEL;
var $parentFunctionalityCodeEL;
var $inputCodeEL;


$(function () {
    $addFormEL=$("#addForm");
    $addFormSubmitBtnEL=$addFormEL.find("button[type=submit]");
    $pickParentBtnEL=$("#pickParentBtn");
    $clearParentBtnEL=$("#clearParentBtn");
    $parentFunctionalityIdEL=$("#parentIdEL");
    $parentFunctionalityNameEL=$("#parentNameEL");
    $parentFunctionalityCodeEL=$("#parentCodeEL");
    $inputCodeEL=$("#inputCodeEL");
    //添加表单提交事件
    $addFormEL.submit(handleFormSubmit);
    //添加选择父功能按钮点击事件
    $pickParentBtnEL.click(handlePickParentBtnClick);
    //添加清空父功能按钮点击事件
    $clearParentBtnEL.click(handlerClearParentBtnClick);
});





/**
 处理表单提交事件
 */
var handleFormSubmit = function () {
    $addFormSubmitBtnEL.disabled(true);
    this.code.value=$parentFunctionalityCodeEL.text()+$inputCodeEL.val();
    var url="/platform/functionality/add";
    $.ajaxPost(url,$(this).serialize()).done(function (resp) {
            window.top.confirmDialog("新增成功,是否继续添加？", function (yes) {
                if (!yes) {
                    window.location.href = "/platform/systemfunctionality/list/view.html";
                }
            });
    }).always(function () {
        $addFormSubmitBtnEL.disabled(false);
    });
    return false;
};

var handlePickParentBtnClick = function (evt) {

    var parentId = $parentFunctionalityIdEL.val();
    var broadcastKey = "pickParentDone";
    var url = "/component/functionality-parent-picker/view.html?";
    var setting = {
        broadcastKey: broadcastKey,
        select: parentId
    };
    var fw = new window.top.FrameWindow();
    fw.open(url + $.param(setting));

    window.onbroadcast(broadcastKey,function(parent) {
        if (parent) {
            $parentFunctionalityIdEL.val(parent.id);
            $parentFunctionalityNameEL.val(parent.name+"("+parent.code+")");
            $parentFunctionalityCodeEL.text(parent.code+"/");
        }
    });
};
var handlerClearParentBtnClick = function (evt) {
    $parentFunctionalityIdEL.val("");
    $parentFunctionalityNameEL.val("");
    $parentFunctionalityCodeEL.text("/");
};

}();