;+function (window, document) {
    "use strict";
    //----view
    //已分配功能总数量标签
    var $totalGrantFunctionalitysEL;
    //确定修改按钮
    var $confirmGrantBtn;
    //功能树标签容器
    var $tableTreeEL;


    //---support
    //角色id//从地址栏url传入
    var roleId= window.location.search.substr(1);
    var loadRoleURL = "/platform/role/get?id=" + roleId;
    var loadRoleFunctionalityURL = "/platform/role/ofFunctionality?id=" + roleId;

    //角色数据模型
    var roleInfoModel;
    //已分配功能数组
    var grantFunctionalityArray;
    //当前已选中功能模块数量
    var totalGrantFunctionalityInt = 0;
    //fancyTree树对象，数据加载完毕后赋值
    var tree = null;


    //onload
    $(function () {
        //全局变量赋值
        $tableTreeEL = $("#functionalityTableTree");
        $totalGrantFunctionalitysEL = $("#totalGrantFunctionalitysEL");
        $confirmGrantBtn = $("#confirmGrantBtn");
        //加载所有功能模块
        updateFunctionalityTableTree().done(function () {
            //加载角色已分配功能
            $.load(loadRoleFunctionalityURL,function (data) {
                grantFunctionalityArray = data;
                updateGrantFunctionalityView(grantFunctionalityArray);
            });
            //添加确定选择按钮点击事件
            addSelectedConfirmBtnClickHandler();
        });
        //加载角色信息
        $.load(loadRoleURL,function (data) {
            roleInfoModel = data;
            updateRoleInfoView(data);
        });


    });


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


    var updateGrantFunctionalityView = function (arr) {
        //选择已选择的节点
        for (var i = arr.length, f; i-- > 0;) {
            f = arr[i];
            var node = tree.getNodeByKey(f.id);
            if (node) {
                //直接改变选择状态，避免级联
                node.selected = true;
                //展开
                node.setActive(true);
                totalGrantFunctionalityInt++;
            }
        }
        //更新选中总数
        $totalGrantFunctionalitysEL.text(totalGrantFunctionalityInt);

    };

    var updateRoleInfoView = function (m) {
        $("#roleNameEL").text(m.name);
    };

    var updateFunctionalityTableTree = function () {
        var deferred = $.Deferred();
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
            selectMode: 3,
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

                cells[2].innerHTML = model.code;
                cells[3].innerHTML = ["目录", "页面", "按钮"][model.kind];
                cells[4].innerHTML = $$(model.url);
            },
            strings: {
                loading: "加载中...请稍后",
                loadError: "加载数据异常，请联系管理员",
                moreData: "More...",
                noData: "无功能数据"
            },
            init: function (evt, ctx, flag) {
                tree = ctx.tree;
                deferred.resolve();
            },
            activate: function (evt, ctx) {
                var node = ctx.node;
            },
            select: function (evt, ctx) {
                var node = ctx.node;

                $totalGrantFunctionalitysEL.text(node.tree.getSelectedNodes().length);
            }
        });
        return deferred.promise();
    };


    var addSelectedConfirmBtnClickHandler = function () {
        $confirmGrantBtn.click(function () {
            $confirmGrantBtn.disabled(true);
            if (!tree)return;
            var selectedNodes = tree.getSelectedNodes().map(function (node) {
                //定义返回数据结构
                return node.key;
            });

            var url = "/platform/role/grantFunctionality";
            $.ajaxPost(url, {
                id: roleId,
                functionalityIds: selectedNodes.join(",")
            }).done(function (resp) {
                toast("分配成功！");
            }).always(function () {
                  $confirmGrantBtn.disabled(false);
            });
        });
    };

}(window, document);