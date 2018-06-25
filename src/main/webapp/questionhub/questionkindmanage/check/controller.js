;+function () {
    "use strict";
    
    //得到id
    var questionkindId=window.location.search.substr(1);
    var loadQuestionKindModelURL="/questionhub/questionkind/get?id="+questionkindId;
    //当前的模型
    var questionKindModel = null;
    //视图元素
    var $modFormEL;
    var $modFormSubmitBtnEL;
    var $modFormResetBtnEL;

$(function () {
    $modFormEL=$("#modForm");
    $modFormSubmitBtnEL = $modFormEL.find("button[type=submit]");
    $modFormResetBtnEL=$modFormEL.find("button[type=reset]");
    //加载数据
    $.load(loadQuestionKindModelURL,function (m) {
        questionKindModel = m;
        updateView(m);
        $modFormEL.submit(handleModFormSubmit);
        $modFormResetBtnEL.click(handleResetBtnClick);
    });


});




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
    form.parserClassName.value=$$(m.parserClassName);
    form.instruction.value=$$(m.instruction);
    form.remark.value = $$(m.remark);
};
}();