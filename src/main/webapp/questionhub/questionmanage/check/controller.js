;+function () {
    "use strict";

    var qid=window.location.search.substr(1);
    var loadQuestionModelURL="/questionhub/question/get?id="+qid;
    var $formEL;
    var $formSubmitBtnEL;
    var $kindNameEL,$creatTimeEL,$creatorNameEL;


    $(function () {
        $formEL = $("form");
        $formSubmitBtnEL = $("button[type=submit]", $formEL);
        $kindNameEL=$("#kindNameEL");
        $creatorNameEL=$("#creatorNameEL");
        $creatTimeEL=$("#createTimeEL");


        //加载题目详情
        $.load(loadQuestionModelURL,function (m) {
            updateFormView(m);
        })

        //添加表单提交事件
        $formEL.submit(handleFormSubmit);

    });
    /**
     处理表单提交事件
     */
    var handleFormSubmit = function () {
        $formSubmitBtnEL.disabled(true);
        var url="/questionhub/question/mod?id="+qid;
        $.ajaxPost(url,$(this).serialize())
            .done(function (data) {
                toast("修改成功！");
            })
            .always(function () {
                $formSubmitBtnEL.disabled(false);
            });
        return false;
    };

    var  updateFormView=function (m) {
        var form=$formEL[0];
        form.difficult.value=$$(m.difficult);
        $kindNameEL.text($$(m.kind.name));
        $creatTimeEL.text(Date.format(m.createTime));
        $creatorNameEL.text($$(m.createUser.name));
        form.content.textContent=$$(m.content);
        form.kindId.value=$$(m.questionKindId);
    }
}();