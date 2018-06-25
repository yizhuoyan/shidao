;+function () {
    "use strict";

    var $addFormEL;
    var $addFormSubmitBtnEL;
    var $pickParentBtnEL;
    var $clearParentBtnEL;
    var $parentKnowledgePointIdEL;
    var $parentKnowledgePointNameEL;
    $(function () {
        $addFormEL = $("form");
        $addFormSubmitBtnEL = $addFormEL.find("button[type=submit]");
        $pickParentBtnEL = $("#pickParentBtn");
        $clearParentBtnEL = $("#clearParentBtn");
        $parentKnowledgePointIdEL = $("#parentIdEL");
        $parentKnowledgePointNameEL = $("#parentNameEL");
        //添加事件
        $addFormEL.submit(handleAddFormSubmit);
        $pickParentBtnEL.click(handlePickParentBtnClick);
        $clearParentBtnEL.click(handlerClearParentBtnClick);

    });

    /**
     处理表单提交事件
     */
    var handleAddFormSubmit = function () {
        $addFormSubmitBtnEL.disabled(true);
        var url = "/questionhub/knowledgePoint/add";
        $.ajaxPost(url, $(this).serialize(), function (resp) {
            window.top.confirmDialog("新增成功，是否继续添加?",function (yes) {
                if(!yes){
                    window.location.href="/questionhub/knowledgePoint/list/view.html";
                }
            })
        }).always(function () {
            $addFormSubmitBtnEL.disabled(false);
        });

        return false;
    };
    var handlePickParentBtnClick = function (evt) {
        //广播通知key
        var broadcastKey = "pickParentDone";
        var parentId = $parentKnowledgePointIdEL.val();
        var url = "/component/knowledgePoint-parent-picker/view.html?";
        var setting = {
            broadcastKey: broadcastKey,
            select: parentId
        };
        var fw = new window.top.FrameWindow();
        fw.open(url + $.param(setting));

        window.onbroadcast(broadcastKey, function (parent) {
            if (parent) {
                $parentKnowledgePointIdEL.val(parent.id);
                $parentKnowledgePointNameEL.val(parent.name);
            }
        });

    };
    var handlerClearParentBtnClick = function (evt) {
        $parentKnowledgePointIdEL.val("");
        $parentKnowledgePointNameEL.val("");
    };

}();