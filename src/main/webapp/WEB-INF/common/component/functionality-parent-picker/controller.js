;+function (window, document) {
    "use strict";

//功能树标签容器
    var $tableTreeEL;
//已选择列表标签
    var $selectedListEl;
//fancyTree树对象，数据加载完毕后赋值
    var tree = null;
//childNode
    var childFunctionality = null;
    /**
     * 配置，通过url路径传入覆盖默认配置
     *
     */
    var setting = {
        broadcastKey: "selected", //选择后广播消息Key
        select: null, //默认选择
        id: null //当前子节点
    };
//onload
    $(function () {
        //全局变量赋值
        $tableTreeEL = $("#functionalityTableTree");
        $selectedListEl = $("#currentSelectedList");
        //获取选项
        loadSetting(setting);
        console.debug("获得配置:");
        console.debug(setting);
        //加载所有功能模块
        updateFunctionalityTableTree();
        //添加确定选择按钮点击事件
        addSelectedConfirmBtnClickHandler();

        //添加取消点击事件
        addSelectedItemClickHandler();
    });


    /**
     * 加载配置
     * @param setting
     */
    var loadSetting = function (setting) {
        var args = window.location.search;
        if (!args)return;
        args = args.substr(1);
        if (!args)return;
        var kvs = args.split("&");
        for (var i = kvs.length, kv; i-- > 0;) {
            kv = kvs[i].split("=");
            setting[decodeURI(kv[0])] = decodeURI(kv[1]);
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


    var updateFunctionalityTableTree = function () {
        $tableTreeEL.fancytree({
            source: {
                url: "/platform/functionality/list",
                cache: true
            },
            postProcess: function (evt, data) {
                var resp = data.response;
                if (resp.ok) {
                    data.result = covertFlatList2Tree(resp.data);
                } else {
                    toast(resp.message);
                }
            },
            checkbox: true,
            minExpandLevel: 1,
            selectMode: 1,
            extensions: ["table"],
            table: {
                checkboxColumnIdx: 0,
                nodeColumnIdx: 1,
                indentation: 16
            },
            renderColumns: function (evt, ctx) {
                var node = ctx.node;
                var model = node.data;
                var cells = node.tr.getElementsByTagName("td");

                cells[2].textContent = model.code;
                cells[3].textContent = ["目录", "页面", "按钮"][model.kind];
                cells[4].textContent = $$(model.url);
            },
            strings: {
                loading: "加载中...请稍后",
                loadError: "加载数据异常，请联系管理员",
                moreData: "More...",
                noData: "无功能数据"
            },
            init: function (evt, ctx, flag) {
                tree = ctx.tree;
                //过滤掉不能作为父节点的节点
                if (setting.id) {
                    var current = tree.getNodeByKey(setting.id);
                    if (current) {
                        current.remove();
                    }
                }
                //选择已选择的节点
                if (setting.select) {
                    tree.activateKey(setting.select);
                }
            },
            activate: function (evt, ctx) {
                var node = ctx.node;
                node.setSelected(true);
            },
            deactivate: function (evt, ctx) {
                var node = ctx.node;
                node.setSelected(false);
            },
            select: function (evt, ctx) {
                var node = ctx.node;
                handlerItemSelected(node, node.selected);
            }
        });
    };


    var handlerItemSelected = function (item, selected) {
        if (selected) {
            var li = document.createElement("li");
            li.innerHTML = item.title;
            li.id = "li-" + item.key;
            $selectedListEl.append(li);
        } else {
            $("#li-" + item.key).remove();
        }
    };

    var addSelectedConfirmBtnClickHandler = function () {
        $("#confirmPickBtn").click(function () {
            try {
                if (!tree)return;
                var selectedNodes = tree.getSelectedNodes().map(function (node) {
                    //定义返回数据结构
                    return {
                        id: node.key,
                        name: node.title,
                        code:node.data.code
                    };
                });
                window.broadcast(setting.broadcastKey, selectedNodes[0]);
            } finally {
                //保证一定关闭窗口
                window.close();
            }
        });
    };
    var addSelectedItemClickHandler = function () {
        $selectedListEl.click(function (evt) {
            if (evt.target.tagName === "LI") {
                var li = evt.target;
                var nodeKey = li.id.substr(3);//去掉li-
                var node = tree.getNodeByKey(nodeKey);
                node.setSelected(false);
            }
        });
    }

}(window, document);