;+function (window) {
    "use strict";
    window.addEventListener("load", function (evt) {
        var css = "";
        css += ".confirmDialog{" +
            "	position: fixed;" +
            "	top:0;" +
            "	left: 0;" +
            "	width: 100vw;" +
            "	height:100vh ;" +
            "	display: flex;" +
            "	justify-content: center;" +
            "	align-items: center;" +
            "	background: #000;" +
            "	background-color:rgba(0,0,0,0.5);" +
            "}";
        css += ".confirmDialog>div{" +
            "	border: 1px solid black;" +
            "	border-radius: 5px;" +
            "	padding: .5em;" +
            "	background: #fff;" +
            "	min-width: 50vw;" +
            "	color: #000;" +
            "}";
        css += ".confirmDialog>div>section{" +
            "	padding: 1em 0;" +
            "}";
        css += ".confirmDialog>div>footer{" +
            "	text-align: right;" +
            "}";
        css += ".confirmDialog button.yes{" +
            "	margin-right: 1em;" +
            "}";


        var style = document.createElement("style");
        style.setAttribute("type", "text/css");
        if (style.styleSheet) {// IE
            style.styleSheet.cssText = css;
        } else {// w3c
            style.appendChild(document.createTextNode(css));
        }
        document.getElementsByTagName("head")[0].appendChild(style);

    });


    var $ = function (e, doc) {
        doc = doc || window.document;
        return doc.querySelector(e);
    };
    /**
     * 通过参数类型来获取参数值
     */
    $.getArgByType = function (args, type) {
        for (var i = args.length; i-- > 0;) {
            if (typeof args[i] === type) {
                return args[i];
            }
        }
        return null;
    };
    $.extend = function () {
        var a = arguments, t = a[0], k, x, i = 1, z = a.length;
        while (i < z)if (x = a[i++])for (k in x)t[k] = x[k];
        return t;
    };
    var defaultSetting = {
        message: null,
        callback: null,
        positiveBtnText: "确定",
        negativeBtnText: "取消"

    };

    /**
     * constructor
     * @param setting
     */
    function ConfirmDialog(setting) {
        this.setting = $.extend({}, defaultSetting, setting);

        this.dialogEL;
        this.headerEL;
        this.contentEL;
        this.footerEL;

        privateMethod.init.apply(this, arguments);
    }

    var privateMethod = {
        init: function () {
            var document = window.document;
            var setting = this.setting;
            var dialog = document.createElement("article");
            dialog.className = "confirmDialog";
            var html = "";
            html += " <div>";
            html += "  <section></section>";
            html += "  <footer></footer>";
            html += " </div>";
            dialog.innerHTML = html;


            document.body.appendChild(dialog);

            this.dialogEL = dialog;
            this.contentEL = dialog.getElementsByTagName("section")[0];
            this.footerEL = dialog.getElementsByTagName("footer")[0];
            this.contentEL.innerHTML = this.setting.message;

            var yesBtn = privateMethod.createDialogBtn.call(this, setting.positiveBtnText, "yes");
            this.footerEL.appendChild(yesBtn);
            var noBtn = privateMethod.createDialogBtn.call(this, setting.negativeBtnText, "no");
            this.footerEL.appendChild(noBtn);
        },
        btnClickHandler: function (evt) {
            var dialog = this.dialog;
            var setting = dialog.setting;
            var confirm = this.innerHTML === setting.positiveBtnText;
            var callback = setting.callback;
            if (!callback.call(this, confirm)) {
                dialog.hide();
            }
        },
        createDialogBtn: function (txt, cls) {
            var document = window.document;
            var btn = document.createElement("button");
            btn.innerHTML = txt;
            btn.className = cls;
            btn.dialog = this;
            btn.addEventListener("click", privateMethod.btnClickHandler);
            return btn;
        }

    };

    var publicMethod = {
        hide: function () {
            this.dialogEL.parentNode.removeChild(this.dialogEL);
        }

    };
    //export methed
    ConfirmDialog.prototype = {
        hide: publicMethod.hide
    };


    //export
    window.confirmDialog = function (message, callback, opts) {
        var setting = {
            message: message,
            callback: callback
        };
        if (opts) {
            if (opts["true"]) {
                setting.positiveBtnText = opts["true"];
            }
            if (opts["false"]) {
                setting.negativeBtnText = opts["false"];
            }
        }
        var dialog = new ConfirmDialog(setting);
        return dialog;
    }


}(window);