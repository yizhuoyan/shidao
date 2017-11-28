;+function () {
    //用户id
    var userId;
    //tabLayout组件
    var tabLayout;
    //用户数据模型
    var userModel;

    //修改表单
    var $modFormEL;
    //表单提交按钮
    var modFormSubmitBtnEL;
    //表单重置按钮
    var modFormResetBtnEL;




    //form标签区域控件
    var $userAccountEL;
    var $userNameEL;
    var $userLastLoginTimeEL;
    var $userLastModPasswordEL;
    var $userCreateTimeEL;
    var $avatorEL;

    //actionBar
    //删除用户按钮
    var $deleteUserBtnEL;
    //重置密码按钮
    var $resetPasswordBtnEL;
    //
    var $headingUserNameEL;
    //拥有功能一览
    var $glanceOwnFunctionalityBtnEL;
    $(function () {
        userId = window.location.search.substr(1);
        tabLayout = new TabLayout({
            dom: ".layout-tab",
            activeListener: handleWhenTabActive
        });
        $modFormEL = $("#modForm");
        modFormSubmitBtnEL = $modFormEL.find("button[type=submit]")[0];
        modFormResetBtnEL = $modFormEL.find("button[type=reset]")[0];
        $resetPasswordBtnEL = $("#resetPasswordBtn");
        $deleteUserBtnEL = $("#deleteUserBtn");

        $userAccountEL = $("#userAccountEL");
        $userNameEL = $("#userNameEL");
        $userLastLoginTimeEL = $("#userLastLoginTimeEL");
        $userLastModPasswordEL = $("#userLastModPasswordTimeEL");
        $userCreateTimeEL = $("#userCreateTimeEL");
        $avatorEL=$("#avatorEL");

        $headingUserNameEL=$("#headingUserNameEL");
        $glanceOwnFunctionalityBtnEL=$("#glanceOwnFunctionalityBtn");

        //加载用户数据模型
        $.load("/platform/user/get?id="+userId,function (data) {
            userModel=data;
            //更新actionBar标题
            updateActionBarView(userModel);

            //更新表单视图
            updateFormView(userModel);
            //添加表单提交事件
            $modFormEL.submit(handleFormSubmit);
            //添加表单重置事件
            $(modFormResetBtnEL).click(handleModFormResetBtnClick);
            //添加重置密码按钮点击事件
            $resetPasswordBtnEL.click(handleResetPasswordBtnClick);
            //
            $glanceOwnFunctionalityBtnEL.click(handleGlanceOwnFunctionalityBtnClick);
            //添加删除用户按钮点击事件
            $deleteUserBtnEL.click(handleDeleteUserBtnClick);
            //点击头像查看大图
            $avatorEL.click(handleAvatorClick);
        });


    });

    /**
     添加tab激活事件
     */
    var handleWhenTabActive = function (tab, card) {
        if (card.tagName === "IFRAME") {
            var frame = card;
            if (!frame.src) {
                frame.src = frame.getAttribute("data-src") + "?" + userId;
            }
        }
    };


//密码重置
    var handleResetPasswordBtnClick = function (account) {

        $.ajaxPost("/platform/user/resetPassword", {
            id: userId
        }).done(function (resp) {
                toast("重置账号[" + account + "]密码成功!");
        });
    };


    /**处理表单提交*/
    var handleFormSubmit = function () {
        var url="/platform/user/mod";
        $.ajaxPost(url, $(this).serialize())
            .done(function (resp) {
                    toast("修改成功");
            });
        return false;
    };

    var handleDeleteUserBtnClick = function () {
        var userName = $$(userModel.name);
        var message = "确定删除用户【" + userName + "】?";
        window.confirmDialog(message, function (yes) {
            if (yes) {
                var url = "/platform/user/del?id="+userId;
                $.ajaxDelete(url, function (resp) {
                        window.toast("删除成功！");
                });
            }
        })
    };

    var handleModFormResetBtnClick = function (evt) {
        evt.preventDefault();
        updateFormView(userModel);
    };
    var updateActionBarView=function (m) {
        $headingUserNameEL.text(m.name);
    }
    
    var updateFormView = function (m) {
        var form = $modFormEL[0];
        $userAccountEL.text($$(m.account));
        $userNameEL.text($$(m.name));
        if(m.avator) {
            $avatorEL.src = $$(m.avator);
        }
        form.remark.textContent = $$(m.remark);
        form.checkRadio("status", m.status);

        if (m.createTime) {
            $userCreateTimeEL.text(Date.format(m.createTime));
        } else {
            $userCreateTimeEL.text("未知");
        }
        if (m.lastModPasswordTime) {
            $userLastModPasswordEL.text(Date.format(m.lastModPasswordTime));
        } else {
            $userLastModPasswordEL.text("还未修改过密码");
        }
        if (m.lastLoginTime) {
            $userLastLoginTimeEL.text(Date.format(new Date(m.lastLoginTime)));
        } else {
            $userLastLoginTimeEL.text("还未登陆过");
        }

    };
    
    var handleGlanceOwnFunctionalityBtnClick=function (evt) {
        var fw=new window.top.FrameWindow();
        var url="/platform/usermanage/glance-own-functionality/view.html?"+userId;
        fw.open(url);
    }

    var handleAvatorClick=function (evt) {
        var fw=new window.FrameWindow();
        var url=this.src;
        fw.open(url);
    }

}();