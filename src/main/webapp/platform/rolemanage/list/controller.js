;+function (window, document) {

    var $queryBtnEL;
    var $queryFormEL;
    var $totalRowsEL;
    var $dataFoundTableBodyEL;
    var $dataNotFoundTableBodyEL;
    var $dataQueryingTableBodyEL;
    var $pageInfoSectionEL;
    var queryViewGroup;

    $(function () {
        $queryFormEL = $("#qryForm");
        $queryBtnEL = $queryFormEL.find("button[type=submit]");
        $totalRowsEL = $("#totalRowsEL");
        $dataFoundTableBodyEL = $("#dataFoundTableBody");
        $dataNotFoundTableBodyEL = $("#dataNotFoundTableBody");
        $dataQueryingTableBodyEL = $("#dataQueryingTableBody");
        $pageInfoSectionEL = $("#pageInfoSection");
        //查询相关元素作为一个组
        queryViewGroup=new ViewGroup()
            .add([$dataFoundTableBodyEL,$pageInfoSectionEL])
            .add($dataNotFoundTableBodyEL)
            .add($dataQueryingTableBodyEL);

        //添加查询按钮点击事件
        $queryFormEL.submit(handleQueryFormSubmit);
        //默认进行一次查询
        $queryFormEL.submit();
    });


    var handleQueryFormSubmit = function (evt) {
            //禁用按钮,防止重复提交
            $queryBtnEL.attr("disabled", true);
            //显示查询UI
            showQueryingView();
            //提交查询请求
            var url = "/platform/role/list";
            $.ajaxGet(url, $(this).serialize()).done(function (queryResult) {
                //判断是否有数据
                if (queryResult.length) {
                    updateFoundView(queryResult);
                } else {
                    updateNotFoundView();
                }

            }).always(function () {
                $queryBtnEL.attr("disabled", false);
            });
            return false;
    };

    /**
     *创建查询结果视图
     **/
    var updateFoundView = function (rows) {
        $dataFoundTableBodyEL.html(dataFoundTableBodyTemplate(rows));
        $totalRowsEL.text(rows.length);

        queryViewGroup.onlyShow($dataFoundTableBodyEL);
    };
    /**
     * 创建未找到结果视图
     */
    var updateNotFoundView = function () {

       queryViewGroup.onlyShow($dataNotFoundTableBodyEL);
    };
    /***
     创建查询中视图
     */
    var showQueryingView = function () {
        queryViewGroup.onlyShow($dataQueryingTableBodyEL);
    };

}(window, document);