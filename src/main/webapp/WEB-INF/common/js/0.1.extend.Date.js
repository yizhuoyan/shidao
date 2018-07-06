;+function (window) {
    Date.prototype.format = function (fmt) {
        fmt = fmt || "yyyy-MM-dd HH:mm:ss";
        var date = this;
        var o = {
            "M+": date.getMonth() + 1, //月份
            "d+": date.getDate(), //日
            "H+": date.getHours(), //小时
            "m+": date.getMinutes(), //分
            "s+": date.getSeconds(), //秒
            "S": date.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        var k;
        for (k in o) {
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        }
        return fmt;
    };

    Date.format = function (d, fmt) {
        if (typeof d === "number") {
            return new Date(d).format(fmt);
        } else if (d instanceof Date) {
            return d.format(fmt);
        } else if(typeof d==="undefined"||d===null){
            return "";
        }else{
            return String(d);
        }
    }
}(window);


