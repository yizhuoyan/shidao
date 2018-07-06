;+function () {
    //得到id
    var systemconfigId;
    //当前的模型
    var systemconfigModel = null;
    //
    var $modFormEL;
    var $modFormSubmitBtnEL;
    var $modFormResetBtnEL;

$(function () {
    systemconfigId = window.location.search.substr(1);
    $modFormEL=$("#modForm");
    $modFormSubmitBtnEL = $modFormEL.find("button[type=submit]");
    $modFormResetBtnEL=$modFormEL.find("button[type=reset]");
    //加载数据
    loadSystemConfigInfo(systemconfigId).done(function (m) {
        systemconfigModel = m;
        updateView(m);
        $modFormEL.submit(handleModFormSubmit);
        $modFormResetBtnEL.click(handleResetBtnClick);
    });


});


var loadSystemConfigInfo = function (id) {
    var url="/platform/config/get?id="+id;
    return $.load(url);
};
var handleModFormSubmit=function () {

    $modFormSubmitBtnEL.disabled(true);
    var url="/platform/config/mod";
    $.ajaxPut(url, $(this).serialize()).done(function (resp) {
            toast("修改成功!");
    }).always(function () {
        $modFormSubmitBtnEL.disabled(false);
    });
    return false;
}
var handleResetBtnClick=function (evt) {
    evt.preventDefault();
    if(systemconfigModel) {
        updateView(systemconfigModel);
    }
}


var updateView = function (m) {
    var form=$modFormEL[0];
    form.name.value = $$(m.name);
    form.value.value = $$(m.value);
    form.remark.value = $$(m.remark);
    //状态
    form.checkRadio("status", String(m.status));
};
}();