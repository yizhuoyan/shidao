;+function () {
    var $userNameEL;
    var $modifyPasswordFormEL;

    $(function () {


        $userNameEL=$("#headingUserNameEL");
        $modifyPasswordFormEL=$("#modifyPasswordForm");
        //获取用户信息

        loadUserInfoModel();
        $modifyPasswordFormEL.submit(handlerModifyPasswordFormSubmit);


    });
    var loadUserInfoModel=function () {
        var name=window.session.getUserName();
        $userNameEL.text(name);
    }
    var handlerModifyPasswordFormSubmit=function () {
        var url="/user/modifypassword.json";
        $.ajaxPost(url,$(this).serialize()).done(function (resp) {
                window.top.toast("修改成功，跳转到主页");
                //去主页
                window.location.href="/index/view.html";
        });
        return false;
    };

}();