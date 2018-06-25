;+function (window,document) {
    "use strict";

//展示的功能id
var functionalityId;

var $grantRolesBoxEL;
var $grantTotalRolesEL;



$(function () {
    functionalityId = window.location.search.substr(1);
    $grantRolesBoxEL=$("#grantRolesBox");
    $grantTotalRolesEL=$("#grantTotalRolesEL");
    //加载数据构建页面
    loadRolesOfFunctionality(functionalityId);
});

/**
 加载功能模块所属角色
 **/
var loadRolesOfFunctionality = function (fid) {
    var url = "/platform/functionality/ofRoles?id="+fid;
    $.load(url,function (data) {
            showRolesOfFunctionality(data);
    });
};
/**
 构建功能模块拥有的角色视图
 */
var showRolesOfFunctionality = function (roles) {
    $grantRolesBoxEL.html(showGrantRolesTemplate(roles));
    $grantTotalRolesEL.text(roles.length);
};


}(window,document);