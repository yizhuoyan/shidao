;+function (window,document) {


    var $queryFormEL;
    var $queryFormSubmitBtnEL;
    var $totalRowsEL;

    var $dataFoundTableBodyEL;
    var $dataNotFoundTableBodyEL;
    var $dataQueryingTableBodyEL;
    var $pageInfoSectionEL;

    var queryViewGroup;


    $(function () {
        $queryFormEL = $("#queryForm");
        $queryFormSubmitBtnEL = $queryFormEL.find("button[type=submit]");

        $dataFoundTableBodyEL = $("#dataFoundTableBody");
        $dataNotFoundTableBodyEL = $("#dataNotFoundTableBody");
        $dataQueryingTableBodyEL = $("#dataQueryingTableBody");

        $totalRowsEL = $("#totalRowsEL");
        $pageInfoSectionEL = $("#pageInfoBox");

        queryViewGroup=new ViewGroup().add([$dataFoundTableBodyEL,$pageInfoSectionEL])
            .add($dataNotFoundTableBodyEL)
            .add($dataQueryingTableBodyEL);


        //默认查询全部
        $queryFormEL.submit(handleQuerySubmit).submit();


    });
//查询表单提交事件
    var handleQuerySubmit = function (evt) {
        //禁用提交按钮,防止重复提交
        $queryFormSubmitBtnEL.disabled(true);
        //显示查询UI
        paintQuerying();
        var url="/platform/config/list";
        //提交查询请求
        $.ajaxGet(url, $(this).serialize()).done(function (rows) {
            if (rows.length > 0) {
                paintResult(rows);
            } else {
                paintNoResult();
            }
        }).always(function () {
            $queryFormSubmitBtnEL.disabled(false);
        });
        return false;
    };

    var paintQuerying = function () {
        queryViewGroup.onlyShow($dataQueryingTableBodyEL);
    };
    var paintNoResult = function () {
        queryViewGroup.onlyShow($dataNotFoundTableBodyEL);
    };
    var paintResult = function (rows) {
        $dataFoundTableBodyEL.html(dataFoundTableBodyTemplate(rows));
        $totalRowsEL.text(rows.length);
        queryViewGroup.onlyShow($dataFoundTableBodyEL);
    };

}(window,document);