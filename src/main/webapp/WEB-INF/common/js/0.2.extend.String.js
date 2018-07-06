;+function () {


    String.prototype.startWidth = function () {

    };

    String.prototype.escape = function () {
        return this.replace(/[<>&'"]/g, function (c) {
            return {
                "<": "&lt;",
                ">": "&gt;",
                "&": "&amp;"
            }[c];
        });
    };

    String.escape = function (obj) {
        if (typeof obj === "string") {
            return obj.escape();
        } else {
            if (typeof obj === "undefined") {
                return /*undefined*/;
            }
            if (obj === null)return null;
            return String(obj).escape();
        }
    }
}();