;+function (window) {
    var $$ = function (str, defaultValue) {
        var out = str;
        if (typeof str === "undefined" || str === null) {
            out = defaultValue || "";
        }
        out = String(out);
        if (out.escape) {
            return out.escape();
        }
        return out;
    };


    $$.queryString = function (key) {
        var reg = new RegExp("(?:\\?|&)" + key + "=([^&]*)(?:&|#)");
        var result = window.location.search.match(reg);
        if (result) {
            return decodeURI(result[1]);
        }
        return null;
    };


    window.$$ = $$;
}(window);

