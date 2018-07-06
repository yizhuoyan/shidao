;+function () {
    "use strict";

//当前选中的节点
    var currentSelectNode = null;
//tabLayout组件
    var detailTabLayoutComponent;
//放置功能树的容器
    var $treeEL;
//the delete btn
    var $delBtnEL;
//树
    var tree;

//页面加载完毕事件处理
    $(function () {
        $treeEL = $("#treeEL");
        $delBtnEL = $("#delBtn");
        //加载功能模块树
        updateTreeView();
        //创建tablayout
        detailTabLayoutComponent = new TabLayout({
            dom: document.querySelector(".layout-tab"),
            activeListener: handleTabActived
        });
        //add the delete event
        $delBtnEL.click(handleDelBtnClick);
        //处理广播事件
        hanlderBroadcast();
    });

    var hanlderBroadcast = function () {
        //收到其他页面删除成功广播
        window.onbroadcast("deleteKnowledgePointDone", function (id) {
            tree.removeNode(id);
        });

        //收到被修改的通知
        window.onbroadcast("modifyKnowledgePointDone", function (id) {
            //重新加载
            updateTreeView().done(function () {
                if (id) {
                    tree.activateKey(id);
                }
            });
        });
    };

    /**
     *Tab选项卡被选中处理
     */
    var handleTabActived = function (tab, card) {
        if (currentSelectNode) {
            card.src = card.getAttribute("data-src") + "?" + currentSelectNode.key;
        }
    };


    var covertFlatList2Tree = function (list) {
        //保存所有node的map
        var nodeMap = {};
        //定义不同类型图标（枝节点，叶节点）
        var iconClass = ["fa fa-cubes", "fa fa-cube"];
        //保存所有根节点
        var roots = [];

        list.filter(function (item) {
            item.key = item.id;
            item.title = item.name;
            item.icon = iconClass[item.childrenAmount>0?0:1];
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
        if(roots.length>0) {
            roots[0].expanded = true;
        }
        return roots;
    };

    var updateTreeView = function () {
        if (tree) {
            return tree.reload();
        }
        var url = "/questionhub/knowledgePoint/list";
        $treeEL.fancytree({
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
                noData: "无数据"
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

                handleNodeSelected(node);
            }
        });
    };


    /**
     处理节点被选中处理
     */
    var handleNodeSelected = function (node) {
        currentSelectNode=node;
        detailTabLayoutComponent.active("card-base");
    };

    var handleDelBtnClick = function () {

        var message = "警告!删除知识点会级联删除！\n确认删除知识点【" + currentSelectNode.title + "】?";
        window.top.confirmDialog(message, function (yes) {
            if (yes) {
                var url = "/questionhub/knowledgePoint/del?id=" + currentSelectNode.key;
                $.ajaxDelete(url, function (data) {
                    toast("删除成功！");
                    updateTreeView();
                });
            }
        })
    };

}();



