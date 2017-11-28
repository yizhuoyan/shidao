;+function (window,document) {
    "use strict";

    var $totalRowsEL;

    var $dataFoundTableBodyEL;
    var $dataNotFoundTableBodyEL;
    var $dataQueryingTableBodyEL;
    var $pageInfoSectionEL;
    var queryViewGroup;


    $(function () {

        $dataFoundTableBodyEL = $("#dataFoundTableBody");
        $dataNotFoundTableBodyEL = $("#dataNotFoundTableBody");
        $dataQueryingTableBodyEL = $("#dataQueryingTableBody");

        $totalRowsEL = $("#totalRowsEL");
        $pageInfoSectionEL=$("#pageInfoSection");

        queryViewGroup=new ViewGroup().add([$dataFoundTableBodyEL,$pageInfoSectionEL])
            .add($dataNotFoundTableBodyEL)
            .add($dataQueryingTableBodyEL);

        loadAllKind();


    });

    var loadAllKind = function () {
        //显示查询UI
        paintQuerying();
        var url="/questionhub/questionkind/list";
        //提交查询请求
        $.ajaxGet(url, $(this).serialize()).done(function (rows) {
            if (rows.length > 0) {
                paintResult(rows);
            } else {
                paintNoResult();
            }
        });
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