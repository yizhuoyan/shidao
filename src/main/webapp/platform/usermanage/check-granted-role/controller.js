;+function () {
    var userId;
    var userGrantRolesModel;
    var $userNameEL;
    var $userTotalGrantRolesEL;
    var $grantedRoleBox;
    var $modifyGrantedBtnEL;

    $(function () {
        userId = window.location.search.substr(1);
        $userNameEL = $("#userNameEL");
        $userTotalGrantRolesEL = $("#userTotalGrantRoleEL");
        $grantedRoleBox = $("#grantedRoleBox");
        $modifyGrantedBtnEL=$("#modifyGrantedBtn");


        //加载页面
        reloadPageView();
    });
    /**
     * 重新加载此页面
     */
    var reloadPageView=function () {
        //加载用户数据模型
        loadUserModel(userId).done(function (userModel) {
            //更新用户信息
            updateUserInfoView(userModel);
            //加载用户拥有角色
            loadRolesOfUser(userId).done(function (userGrantRoles) {
                userGrantRolesModel=userGrantRoles;
                //更新分配角色视图
                updateGrantRolesView(userGrantRoles);
                //添加修改授权按钮点击事件
                $modifyGrantedBtnEL.click(handleModifyGrantedBtnClick);

            });

        });
    };

    var loadUserModel = function (id) {
        var url = "/platform/usermanage/get.json?id="+userId;
        return $.load(url);
    };
        //加载拥有角色
    var loadRolesOfUser = function (userId) {
        var url = "/platform/usermanage/ofRoles.json?id="+userId;
        return $.load(url);
    };




    var updateGrantRolesView = function (roles) {
        $grantedRoleBox.html(grantedRolesTemplate(roles));
        $userTotalGrantRolesEL.text(roles.length);
    };

    var updateUserInfoView=function (u) {
        $userNameEL.text(u.name);
    };






    var handleModifyGrantedBtnClick = function (evt) {
        var broadcastKey="grant-role-done";
        var url = "/platform/usermanage/grant-role/view.html?";
        var fw=new window.top.FrameWindow();
        var setting={
            broadcastKey:broadcastKey,
            id:userId
        };
        fw.open(url+$.param(setting));

        window.onbroadcast(broadcastKey,function () {
            reloadPageView();
        });
    };


}();