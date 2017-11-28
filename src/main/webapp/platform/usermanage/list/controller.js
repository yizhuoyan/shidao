;+function () {
    //查询表单
    var $queryForm;
    //提交按钮
    var $submitBtnEL;
    //数据表格体
    var $dataFoundTableBodyEL;
    var $dataNotFoundTableBodyEL;
    var $dataQueryingTableBodyEL;
    //分页信息区域标签
    var $paginationRegionEL;

    var queryViewGroup=new ViewGroup();
    //分页数据标签
    var $pageSizeEL, $totalRowsEL, $totalPagesEL, $pageNoEL;

    //四个分页按钮
    var $firstPageBtn, $previousPageBtn, $nextPageBtn, $lastPageBtn;

    //查询数据模型
    var queryResultModel;

    $(function () {
        $queryForm = $("#queryForm");
        $submitBtnEL = $queryForm.find("button[type=submit]");

        $dataFoundTableBodyEL = $("#dataFoundTableBody");
        $dataNotFoundTableBodyEL = $("#dataNotFoundTableBody");
        $dataQueryingTableBodyEL= $("#dataQueryingTableBody");
        $paginationRegionEL = $("#paginationRegionEL");

        queryViewGroup.add([$dataFoundTableBodyEL,$paginationRegionEL])
            .add($dataQueryingTableBodyEL)
            .add($dataNotFoundTableBodyEL);

        //分页数据展示标签
        $pageSizeEL = $("#pageSizeEL");
        $totalPagesEL = $("#totalPagesEL");
        $pageNoEL = $("#pageNoEL");
        $totalRowsEL = $("#totalRowsEL");

        //四个分页按钮
        $firstPageBtn = $("#firstPageBtn");
        $previousPageBtn = $("#previousPageBtn");
        $nextPageBtn = $("#nextPageBtn");
        $lastPageBtn = $("#lastPageBtn");

        //关联提交事件并提交
        $queryForm.submit(handleQueryFormSubmit).submit();
        //分页按钮绑定事件
        $firstPageBtn.click(handlePaginationBtnsClick);
        $previousPageBtn.click(handlePaginationBtnsClick);
        $nextPageBtn.click(handlePaginationBtnsClick);
        $lastPageBtn.click(handlePaginationBtnsClick);

    });


//查询表单提交事件
    var handleQueryFormSubmit = function (evt) {
        //禁用提交按钮,防止重复提交
        $submitBtnEL.disabled(true);
        //显示查询UI
        showQueryingView();
        var url="/platform/user/list";
        //提交查询请求
        $.ajaxGet(url,$(this).serialize()).done(function (result) {
            queryResultModel=result;
            //判断是否有数据
            if (result.totalRows > 0) {
                updateFoundView(result);
            } else {
                updateNotFoundView();
            }
        }).always(function () {
            $submitBtnEL.disabled(false);
        });
        return false;
    };


//查询结果页码跳转
    var goPage = function (no) {
        $queryForm[0].pageNo.value = String(no);
        $queryForm.submit();

    };

    var handlePaginationBtnsClick = function (evt) {
        if (!queryResultModel)return;
        var m = queryResultModel;
        var btn = evt.target;
        switch (btn.id) {
            case  "firstPageBtn":
                goPage(1);
                break;
            case "previousPageBtn":
                goPage(m.pageNo - 1);
                break;
            case "nextPageBtn":
                goPage(m.pageNo + 1);
                break;
            case "lastPageBtn":
                goPage(m.totalPages);
                break;
        }
    };
    var showQueryingView=function () {
        queryViewGroup.onlyShow($dataQueryingTableBodyEL);
    };

    var updateFoundView = function (m) {
        //显示数据
        $dataFoundTableBodyEL.html(dataFoundTableTemplate(m.rows));
        //改变分页按钮状态
        $previousPageBtn.disabled(m.pageNo <= 1);
        $firstPageBtn.disabled($previousPageBtn.disabled());
        $nextPageBtn.disabled(m.pageNo >= m.totalPages);
        $lastPageBtn.disabled($nextPageBtn.disabled());
        //显示分页数据
        $pageSizeEL.text(m.pageSize);
        $totalRowsEL.text(m.totalRows);
        $totalPagesEL.text(m.totalPages);
        $pageNoEL.text(m.pageNo);

        queryViewGroup.onlyShow($dataFoundTableBodyEL);

    };

    var updateNotFoundView = function () {
        queryViewGroup.onlyShow($dataNotFoundTableBodyEL);
    };

}();