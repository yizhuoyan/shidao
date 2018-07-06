;+function () {
    "use strict";

    var functionalityId=window.location.search.substr(1);
    var functionalityModel = null;
    var loadFunctionalityURL = "/platform/functionality/get?id="+functionalityId;
    var modifyFunctionalityURL = "/platform/functionality/mod?id="+functionalityId;
    var deleteFunctionalityURL = "/platform/functionality/del?id="+functionalityId;

    var $modFormEL;
    var $modFormSubmitBtnEL;
    var $modFormResetBtnEL;
    var $pickParentBtnEL;
    var $clearParentBtnEL;
    var $parentFunctionalityIdEL;
    var $parentFunctionalityNameEL;
    var $parentFunctionalityCodeEL;
    var $inputCodeEL;
    var $createTimeEL;

    $(function () {

        $modFormEL = $("#modForm");
        $modFormSubmitBtnEL = $modFormEL.find("button[type=submit]");
        $modFormResetBtnEL = $modFormEL.find("button[type=reset]");
        $pickParentBtnEL = $("#pickParentBtn");
        $clearParentBtnEL = $("#clearParentBtn");
        $parentFunctionalityIdEL = $("#parentIdEL");
        $parentFunctionalityNameEL = $("#parentNameEL");
        $parentFunctionalityCodeEL = $("#parentCodeEL");
        $inputCodeEL = $("#inputCodeEL");
        $createTimeEL = $("#createTimeEL");


        //加载数据
        $.load(loadFunctionalityURL, function (model) {
            functionalityModel = model;
            updateFunctionalityDetailView(model);
            $modFormEL.submit(handleModFormSubmit);
            $modFormResetBtnEL.click(handleResetBtnClick)
            $pickParentBtnEL.click(handlePickParentBtnClick);
            $clearParentBtnEL.click(handlerClearParentBtnClick);
        });


    });


    /**
     处理表单提交事件
     */
    var handleModFormSubmit = function () {
        $modFormSubmitBtnEL.disabled(true);
        //处理功能代号
        var inputCode=$inputCodeEL.val();
        this.code.value=$parentFunctionalityCodeEL.text()+inputCode;
        $.ajaxPost(modifyFunctionalityURL, $(this).serialize()).done(
            function (resp) {
                toast("修改成功!");
                window.broadcast("modFunctionalityOk", functionalityId);
            }).always(function () {
            $modFormSubmitBtnEL.disabled(false);
        });
        return false;
    };
    /**
     处理重置按钮点击事件
     **/
    var handleResetBtnClick = function (evt) {
        evt.preventDefault();
        if (functionalityModel) {
            updateFunctionalityDetailView(functionalityModel);
        }
    };


    var handlePickParentBtnClick = function (evt) {
        //广播通知key
        var broadcastKey = "pickParentDone";
        var parentId = $parentFunctionalityIdEL.val();
        var url = "/component/functionality-parent-picker/view.html?";
        var setting = {
            id: functionalityId,
            broadcastKey: broadcastKey,
            select: parentId
        };
        var fw = new window.top.FrameWindow();
        fw.open(url + $.param(setting));

        window.onbroadcast(broadcastKey, function (parent) {
            if (parent) {
                $parentFunctionalityIdEL.val(parent.id);
                $parentFunctionalityNameEL.val(parent.name + "(" + parent.code + ")");
                $parentFunctionalityCodeEL.text(parent.code + "/");

            }
        });

    };
    var handlerClearParentBtnClick = function (evt) {
        $parentFunctionalityIdEL.val("");
        $parentFunctionalityNameEL.val("");
        $parentFunctionalityCodeEL.text("/");
    };
    /**
     处理删除按钮点击
     */
    var handleDelBtnClick = function () {
        window.top.confirmDialog("删除功能模块会导致用户无法使用,确认删除此?", function (yes) {
            if (yes) {
                $.ajaxDelete(deleteFunctionalityURL, function (resp) {
                    //通知各个关联视图
                    window.broadcast("delFunctionalityOk", id);
                    toast("删除成功,已更新界面");
                });
            }
        });
    };

//更新详情区域
    var updateFunctionalityDetailView = function (m) {
        var form = $modFormEL[0];
        var code = m.code;
        form.code.value = $$(code);
        $inputCodeEL.val(code.substr(code.lastIndexOf('/') + 1));
        if (m.parent) {
            var parent = m.parent;
            form.parentId.value = $$(parent.id);
            $parentFunctionalityNameEL.val($$(parent.name + "(" + parent.code + ")"));
            $parentFunctionalityCodeEL.text(parent.code + "/");
        } else {
            $parentFunctionalityNameEL.val("");
            form.parentId.value = "";
            $parentFunctionalityCodeEL.text("/");
        }
        form.name.value = $$(m.name);
        form.url.value = $$(m.url);
        form.orderCode.value = $$(m.orderCode);
        form.remark.value = $$(m.remark);
        $createTimeEL.text(new Date(m.createTime).format());
        form.checkRadio("status", m.status);
        form.checkRadio("kind", m.kind);
    };


//父窗口提示
    var toast = function () {
        window.parent.toast(arguments[0]);
    };
}();