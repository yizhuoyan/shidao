;+function (window, document) {
    window.addEventListener("load", function () {
        var tables = document.getElementsByClassName("table");
        if (tables) {
            for (var i = tables.length, table; i-- > 0;) {
                table = tables[i];
                var theadColgroup = table.querySelector("header>table>colgroup");
                var tbodyColgroup = theadColgroup.cloneNode(true);

                var tbodyTable = table.querySelector("main>table");
                tbodyTable.appendChild(tbodyColgroup);
            }
        }
    });


}(window, document);