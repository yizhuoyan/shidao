;+function () {

    var $menuTreeEL;

    var $logoutBtnEL;

    var mainFrameEL;

    var functionalityList;

    var menuTree;

    var $userNameEL;
    var $userAccountEL;
    var $userLoginIpEL;
    var $userAvatorEL;



//入口
    $(function () {
        $menuTreeEL = $("#menuTreeEL");
        mainFrameEL = $("#mainFrame")[0];
        $logoutBtnEL = $("#logoutBtn");
        $userNameEL=$("#userNameEL");
        $userAccountEL=$("#userAccountEL");
        $userLoginIpEL=$("#userLoginIpEL");
        $userAvatorEL=$("#userAvatorEL");

        loadUserInfoModel();
        //加载菜单
        loadMenuModel().then(function (data) {
            functionalityList = data;
            //构建菜单树
            updateMenuTreeView(data);
        });

        $logoutBtnEL.click(handleLogoutBtnClick);
    });

    var loadUserInfoModel=function () {
        var user=window.session.getCurrentUser();
        $userNameEL.text(user.name);
        $userAccountEL.text(user.account);
        $userLoginIpEL.text(user.loginIp);
        if(user.avator) {
            $userAvatorEL.src = user.avator;
        }
    };


    var loadMenuModel = function () {
        var url = "/myMenu";
        return $.load(url);
    };

    var covertFlatList2Tree = function (list) {
        //保存所有node的map
        var nodeMap = {};
        //定义不同类型功能图标（目录，菜单）
        var iconClass = ["fa fa-suitcase", "fa fa-desktop"];
        //保存所有根节点
        var roots = [];

        list.filter(function (item) {
            if (item.kind !== 2) {//过滤掉非菜单功能
                //return false;
            }
            if (item.kind === 0) {//目录
                item.folder = true;
            }
            item.key = item.id;
            item.title = item.name;
            item.icon = iconClass[item.kind];
            nodeMap[item.id] = item;
            if (item.parentId) {
                return true;
            } else {
                roots.push(item);
                return false;
            }
        }).forEach(function (item) {
            var parent = nodeMap[item.parentId];
            if (parent.children) {
                parent.children.push(item);
            } else {
                parent.children = [item];
            }
        });
        return roots;
    };

    var updateMenuTreeView = function (data) {
        if (!data)return;
        //上一次展开节点
        var lastExpanedNode;
        //转换list为树形结构
        $menuTreeEL.fancytree({
            source: covertFlatList2Tree(data),
            strings: {
                loading: "加载中...请稍后",
                loadError: "加载数据异常，请联系管理员",
                moreData: "More...",
                noData: "无功能数据"
            },
            init: function (evt, ctx, flag) {
                tree = ctx.tree;
                //选择根节点首页
            },
            activate: function (evt, ctx) {
                var node = ctx.node;
                var tree = node.tree;
                if (node.isFolder()) {
                    //展开
                    node.setExpanded(true);
                } else {
                    //在iframe中打开
                    mainFrameEL.src = node.data.url;
                }
            },
            expand: function (evt, ctx) {
                var node = ctx.node;

                //已展开的收缩
                if (lastExpanedNode) {
                    //同一节点，不处理
                    if(lastExpanedNode===node){
                        return;
                    }
                    //不是当前节点的父节点,则收缩
                    if (node.getParentList().indexOf(lastExpanedNode) === -1) {
                        lastExpanedNode.setExpanded(false);
                        lastExpanedNode=null;
                    } else {
                        //是父节点，则不收缩,不记录，一会直接收缩父节点
                        return;
                    }
                }
                lastExpanedNode = node;
            }
        });

    };

//退出
    var handleLogoutBtnClick = function () {
        var url = "/logout";
        $.ajaxGet(url, function (resp) {
                window.location.href = "/login/view.html";
        });
    }


}();