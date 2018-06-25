;+function () {
    "use strict";

    var knowledgePointId=window.location.search.substr(1);
    var knowledgePointModel = null;
    var loadKnowledgePointURL = "/questionhub/knowledgePoint/get?id="+knowledgePointId;
    var modifyKnowledgePointURL = "/questionhub/knowledgePoint/mod?id="+knowledgePointId;


    var $modFormEL;
    var $modFormSubmitBtnEL;
    var $pickParentBtnEL;
    var $clearParentBtnEL;
    var $parentKnowledgePointIdEL;
    var $parentKnowledgePointNameEL;

    $(function () {

        $modFormEL = $("#modForm");
        $modFormSubmitBtnEL = $modFormEL.find("button[type=submit]");
        $pickParentBtnEL = $("#pickParentBtn");
        $clearParentBtnEL = $("#clearParentBtn");
        $parentKnowledgePointIdEL = $("#parentIdEL");
        $parentKnowledgePointNameEL = $("#parentNameEL");

        //加载数据
        $.load(loadKnowledgePointURL, function (model) {
            knowledgePointModel = model;
            updateKnowledgePointDetailView(model);
            $modFormEL.submit(handleModFormSubmit);
            $pickParentBtnEL.click(handlePickParentBtnClick);
            $clearParentBtnEL.click(handlerClearParentBtnClick);
        });


    });


    /**
     处理表单提交事件
     */
    var handleModFormSubmit = function () {
        $modFormSubmitBtnEL.disabled(true);
        $.ajaxPost(modifyKnowledgePointURL, $(this).serialize()).done(
            function (resp) {
                toast("修改成功!");
                window.broadcast("modifyKnowledgePointDone", knowledgePointId);
            }).always(function () {
            $modFormSubmitBtnEL.disabled(false);
        });
        return false;
    };

    var handlePickParentBtnClick = function (evt) {
        //广播通知key
        var broadcastKey = "pickParentDone";
        var parentId = $parentKnowledgePointIdEL.val();
        var url = "/component/knowledgePoint-parent-picker/view.html?";
        var setting = {
            id: knowledgePointId,
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


//更新详情区域
    var updateKnowledgePointDetailView = function (m) {
        var form = $modFormEL[0];
        if (m.parent) {
            var parent = m.parent;
            form.parentId.value = $$(parent.id);
            $parentKnowledgePointNameEL.val($$(parent.name));
        } else {
            $parentKnowledgePointNameEL.val("");
            form.parentId.value = "";
        }
        form.name.value = $$(m.name);
        form.remark.value = $$(m.remark);
    };


//父窗口提示
    var toast = function () {
        window.parent.toast(arguments[0]);
    };
}();