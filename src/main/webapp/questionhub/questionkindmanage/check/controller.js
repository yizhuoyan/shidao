;+function () {
    "use strict";
    
    //得到id
    var questionkindId;
    //当前的模型
    var questionKindModel = null;
    //
    var $modFormEL;
    var $modFormSubmitBtnEL;
    var $modFormResetBtnEL;

$(function () {
    questionkindId = window.location.search.substr(1);
    $modFormEL=$("#modForm");
    $modFormSubmitBtnEL = $modFormEL.find("button[type=submit]");
    $modFormResetBtnEL=$modFormEL.find("button[type=reset]");
    //加载数据
    loadSystemConfigInfo(questionkindId).done(function (m) {
        questionKindModel = m;
        updateView(m);
        $modFormEL.submit(handleModFormSubmit);
        $modFormResetBtnEL.click(handleResetBtnClick);
    });


});


var loadSystemConfigInfo = function (id) {
    var url="/questionhub/questionkind/get?id="+id;
    return $.load(url);
};

var handleModFormSubmit=function () {

    $modFormSubmitBtnEL.disabled(true);
    var url="/questionhub/questionkind/mod";
    $.ajaxPut(url, $(this).serialize()).done(function (resp) {
            toast("修改成功!");
    }).always(function () {
        $modFormSubmitBtnEL.disabled(false);
    });
    return false;
}

var handleResetBtnClick=function (evt) {
    evt.preventDefault();
    if(questionKindModel) {
        updateView(questionKindModel);
    }
}


var updateView = function (m) {
    var form=$modFormEL[0];
    form.id.value = $$(m.id);
    form.name.value = $$(m.name);
    form.remark.value = $$(m.remark);
};
}();