;+function (window, document) {
    "use strict";

    var userId;
    var totalGrantedRolesInt=0;
    //功能树标签容器
    var $tableTreeEL;

    var $headUserNameEL;
    var $totalGrantRolesEL;
    var $confirmGrantBtnEL;
    //已选择列表标签
    var $selectedListEl;

    //fancyTree树对象，数据加载完毕后赋值
    var tree = null;
    /**
     * 通过url路径传入覆盖默认配置
     */
    var setting = {
        broadcastKey: "grant-role-done", //选择后广播消息Key
        id:null//用户id
    };
//onload
    $(function () {
        //全局变量赋值
        $tableTreeEL = $("#functionalityTableTree");
        $selectedListEl = $("#currentSelectedList");
        $headUserNameEL=$("#headingUserNameEL");
        $totalGrantRolesEL=$("#totalGrantRolesEL");
        $confirmGrantBtnEL=$("#confirmGrantBtn");
        //获取选项
        loadSetting(setting);
        userId=setting.id;
        //加载用户信息
        loadUserInfo().done(function (uesrInfo) {
            updateUserInfoView(uesrInfo);
        });
        //加载所有角色
        loadAllRoles().done(function (rolesArr) {
            //更新角色列表视图
            updateRoleTreeView(rolesArr);
            //加载用户已拥有角色
            loadUserGrantedRoles().done(function (grantedRolesArr) {
                //更新已授权角色视图
                updateGrantedRolesView(grantedRolesArr);
                //添加确定选择按钮点击事件
                $confirmGrantBtnEL.click(handleConfirmGrantBtnClick);
                //添加取消点击事件
                $selectedListEl.click(handleSelectedItemRevokeClick);
            });
        });

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



    var loadUserInfo=function () {
        var url="/platform/user/get?id="+userId;
       return $.load(url);
    };
    
    var updateUserInfoView=function (u) {
        $headUserNameEL.text(u.name);
    };

    var loadAllRoles=function () {
        var url="/platform/role/list";

        return $.load(url);
    };
    
    var loadUserGrantedRoles=function () {
        var url = "/platform/user/ofRoles?id="+userId;
        return $.load(url);
    };
    //转换角色列表符合fancyTree组件的格式
    var covertFlatList2Tree = function (list) {
        //保存所有node的map
        var nodeMap = {};
        //定义不同类型角色图标（目录，页面，按钮）
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
    var updateRoleTreeView = function (roles) {
        roles=covertFlatList2Tree(roles);

        $tableTreeEL.fancytree({
            source: roles,
            checkbox: true,
            selectMode: 2,
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
                cells[3].innerHTML = $$(model.remark);
            },
            strings: {
                loading: "加载中...请稍后",
                loadError: "加载数据异常，请联系管理员",
                moreData: "More...",
                noData: "无数据"
            },
            init: function (evt, ctx, flag) {
                tree = ctx.tree;
            },
            select: function (evt, ctx) {
                var node = ctx.node;
                handlerItemSelected(node, node.selected);
            }
        });
    };
    var updateGrantedRolesView=function (roles) {
        //选择已选择的节点
        if (roles&&roles.length>0) {
            for (var i = roles.length; i-- > 0;) {
                var node = tree.getNodeByKey(roles[i].id);
                if (node) {
                    node.setSelected(true);
                }
            }
        }else{
            $totalGrantRolesEL.text(0);
        }
    }

    var handlerItemSelected = function (item, selected) {
        if (selected) {
            var li = document.createElement("li");
            li.innerHTML = item.title;
            li.id = "li-" + item.key;
            $selectedListEl.append(li);
            totalGrantedRolesInt++;
        } else {
            $("#li-" + item.key).remove();
            totalGrantedRolesInt--;
        }
        $totalGrantRolesEL.text(totalGrantedRolesInt);
    };
    var handleConfirmGrantBtnClick = function (evt) {
            var roleIdArr = tree.getSelectedNodes().map(function (node) {
                //定义返回数据结构
                return node.key;
            });
            doGrantRole2User(userId,roleIdArr).done(function () {
                window.broadcast(setting.broadcastKey);
                //关闭窗口
                window.close();
            });



    };
    var handleSelectedItemRevokeClick = function (evt) {
        if (evt.target.tagName === "LI") {
            var li = evt.target;
            var nodeKey = li.id.substr(3);//去掉li-
            var node = tree.getNodeByKey(nodeKey);
            node.setSelected(false);
        }
    };

    var doGrantRole2User=function (userId,roleIdArr) {
            var url = "/platform/user/grantRoles";
            return $.ajaxPut(url, {
                id: userId,
                roleIds: roleIdArr.join(",")
            },function (data) {
                    window.top.toast("授于用户角色成功!");
            });
    };



}(window, document);