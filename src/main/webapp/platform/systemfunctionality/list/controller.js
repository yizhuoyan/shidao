;+function () {

    "use strict";
//当前选中的功能id
    var selectFunctionalityId = null;
    var selectFunctionalityName;
//tabLayout组件
    var detailTabLayoutComponent;
//放置功能树的容器
    var $functionalityTreeBoxEL;

    var $delBtnEL;
//功能树
    var tree;

//页面加载完毕事件处理
    $(function () {
        $functionalityTreeBoxEL = $("#functionalityTreeBox");
        $delBtnEL = $("#delBtn");
        //加载功能模块树
        updateFunctionalityTableTree();
        //创建tablayout
        detailTabLayoutComponent = new TabLayout({
            dom: document.getElementById("detailTabLayout"),
            activeListener: handleTabActived
        });
        //add the delete event
        $delBtnEL.click(handleDelBtnClick);

        hanlderBroadcast();
    });

    var hanlderBroadcast = function () {
        //收到其他页面删除功能广播
        window.onbroadcast("delFunctionalityOk", function (fid) {
            tree.removeNode(fid);
        });

//收到某个功能被修改的通知
        window.onbroadcast("modFunctionalityOk", function (fid) {
            //重新加载
            updateFunctionalityTableTree().done(function () {
                if (fid) {
                    tree.activateKey(fid);
                }
            });
        });
    };

    /**
     *Tab选项卡被选中处理
     */
    var handleTabActived = function (tab, card) {
        if (selectFunctionalityId) {
            card.src = card.getAttribute("data-src") + "?" + selectFunctionalityId;
        }
    };


    var covertFlatList2Tree = function (list) {
        //保存所有node的map
        var nodeMap = {};
        //定义不同类型功能图标（目录，页面，按钮）
        var iconClass = ["fa fa-suitcase", "fa fa-desktop", "fa fa-hand-pointer-o"];
        //保存所有根节点
        var roots = [];

        list.filter(function (item) {
            item.key = item.id;
            item.title = item.name;
            item.icon = iconClass[item.kind];
            nodeMap[item.id] = item;
            if (item.parentId) {
                return true;
            } else {
                item.folder = true;
                roots.push(item);
                return false;
            }
        }).forEach(function (item) {
            var parent = nodeMap[item.parentId];
            if (parent.children) {
                parent.children.push(item);
            } else {
                parent.children = [item];
                parent.folder = true;
            }
        });
        //expand the first root
        roots[0].expanded = true;
        return roots;
    };

    var updateFunctionalityTableTree = function () {
        if (tree) {
            return tree.reload();
        }
        var url = "/platform/functionality/list";
        $functionalityTreeBoxEL.fancytree({
            source: {
                url: url,
                cache: false
            },
            postProcess: function (evt, data) {
                var resp = data.response;
                if (resp.ok) {
                    data.result = covertFlatList2Tree(resp.data);
                } else {
                    toast(resp.message);
                }
            },
            checkbox: false,
            minExpandLevel: 1,
            selectMode: 1,
            strings: {
                loading: "加载中...请稍后",
                loadError: "加载数据异常，请联系管理员",
                moreData: "More...",
                noData: "无功能数据"
            },
            init: function (evt, ctx, flag) {
                tree = ctx.tree;
                //选择第一个
                var firstChild = tree.getFirstChild();
                if (firstChild) {
                    firstChild.setActive(true);
                }
            },
            activate: function (evt, ctx) {
                var node = ctx.node;

                handleFunctionalityNodeSelected(node);
            }
        });
    };


    /**
     处理功能模块节点被选中处理
     */
    var handleFunctionalityNodeSelected = function (node) {
        selectFunctionalityId = node.key;
        selectFunctionalityName = node.title;
        detailTabLayoutComponent.active("card-base");
    };

    var handleDelBtnClick = function () {
        if (!selectFunctionalityName) {
            toast("请先选择要删除的功能！");
            return;
        }
        var message = "警告!删除功能会级联删除其子功能！\n确认删除功能【" + selectFunctionalityName + "】?";
        window.top.confirmDialog(message, function (yes) {
            if (yes) {
                var url = "/platform/functionality/del?id=" + selectFunctionalityId;
                $.ajaxDelete(url, function (data) {
                    toast("删除成功！");
                    updateFunctionalityTableTree();
                });
            }
        })
    };

}();



