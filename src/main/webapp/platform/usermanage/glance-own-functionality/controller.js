;+function () {
    var userId=window.location.search.substr(1);

    var loadUserModelURL = "/platform/user/get?id="+userId;
    var loadUserGrantedFunctionalitysModelURL="/platform/user/glanceOwnFunctionalitys?id="+userId;

    var totalRowsArr=[];
    var $headingUserNameEL;
    var $totalOwnFunctionalitysEL;
    var $totalGrantedRolesEL;

    var $functionalityTableTreeEL;

    var tree;

    $(function () {
         $headingUserNameEL=$("#headingUserNameEL")
         $totalOwnFunctionalitysEL=$("#totalOwnFunctionalitysEL");
         $totalGrantedRolesEL=$("#totalGrantedRolesEL")
        $functionalityTableTreeEL=$("#functionalityTableTree");


        //加载用户信息
        $.load(loadUserModelURL,function (userModel) {
            updateUserInfoView(userModel);
        });

        //加载用户拥有功能
        $.load(loadUserGrantedFunctionalitysModelURL,function (userFunctionalityArr) {
            //更新总功能数
            $totalOwnFunctionalitysEL.text(userFunctionalityArr.length);
            updateFunctionalityTableTree(userFunctionalityArr);

        });
    });




    var updateUserInfoView=function (u) {
        $headingUserNameEL.text(u.name);
    }

    var countGrantedRoles=function (r) {
        if(totalRowsArr.indexOf(r.id)===-1){
            totalRowsArr.push(r.id);
        }
    }


    
    var updateUserGrantedRolesView=function (rows) {
        $totalGrantedRolesEL.text(rows.length);
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
            //处理角色
            item.roles=item.roles.map(function (r) {
                countGrantedRoles(r);
                return r.name;
            });
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
    var updateFunctionalityTableTree = function (list) {




        list=covertFlatList2Tree(list);
        //更新角色总数
        $totalGrantedRolesEL.text(totalRowsArr.length);

        $functionalityTableTreeEL.fancytree({
            source: list,
            extensions: ["table"],
            table: {
                nodeColumnIdx: 0,
                indentation: 16
            },
            renderColumns: function (evt, ctx) {
                var node = ctx.node;
                var model = node.data;
                var cells = node.tr.getElementsByTagName("td");

                cells[1].innerHTML = model.code;
                cells[2].innerHTML = ["目录", "页面", "按钮"][model.kind];
                cells[3].innerHTML = model.roles.join(",");
            },
            strings: {
                loading: "加载中...请稍后",
                loadError: "加载数据异常，请联系管理员",
                moreData: "More...",
                noData: "无功能数据"
            },
            init: function (evt, ctx, flag) {
                tree = ctx.tree;

            }

        });
    };




}();