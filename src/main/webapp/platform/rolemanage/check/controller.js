;+function () {
    "use strict";
    //当前操作的角色id
    var roleId = window.location.search.substr(1);
    var loadRoleURL = "/platform/role/get?id=" + roleId;
    var modifyRoleURL = "/platform/role/mod?id=" + roleId;
    var deleteRoleURL = "/platform/role/del?id=" + roleId;
    //角色数据模型
    var roleModel;


    var tabLayout;//tablayout对象
    var $modForm;
    var $formSubmitBtn;
    var $delBtn;
    var $formResetBtn;
    var $headingRoleNameEL;

    $(function () {
        $modForm = $("#modForm");
        $formSubmitBtn = $modForm.find("button[type=submit]");
        $formResetBtn = $modForm.find("button[type=reset]");
        $delBtn = $("#delBtn");
        $headingRoleNameEL = $("#headingRoleNameEL");
        //创建tablayout
        tabLayout = new TabLayout({
            dom: ".layout-tab",
            activeListener: handleTabActive
        });
        //加载表单数据模型
        $.load(loadRoleURL, function (data) {
            roleModel = data;
            //更新表单视图
            updateFormView(roleModel);
            //点击表单提交事件
            $modForm.submit(handleFormSubmit);
            //删除按钮点击事件
            $delBtn.click(handleDelBtnClick);
            //重置按钮点击事件
            $formResetBtn.click(handleFormResetBtnClick);
        });
    });


    /**
     tab激活事件
     */
    var handleTabActive = function (tab, card) {
        if (card.id === "card-functionalitys") {
            var frame = card;
            if (!frame.src) {
                frame.src = frame.getAttribute("data-src") + "?" + roleId;
            }
        }
    };

    /**处理表单提交*/
    var handleFormSubmit = function () {
        $formSubmitBtn.disabled(true);
        $.ajaxPost(modifyRoleURL, $(this).serialize())
            .done(function (resp) {
                toast("修改成功!");
            })
            .always(function () {
                $formSubmitBtn.disabled(false);
            });
        return false;
    };

    var updateFormView = function (m) {
        var form = $modForm[0];
        $headingRoleNameEL.text($$(m.name));
        form.name.value = $$(m.name);
        form.code.value = $$(m.code);
        form.remark.value = $$(m.remark);
    };
    var handleFormResetBtnClick = function (evt) {
        updateFormView(roleModel);
    };

    var handleDelBtnClick = function (evt) {
        $.ajaxPost(deleteRoleURL).done(function (resp) {
            window.top.toast("删除成功！");
            window.location.href = "../list/view.html";
        })
    };
}();