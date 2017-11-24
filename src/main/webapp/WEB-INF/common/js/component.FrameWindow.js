;+function (window, document, undefined) {
    "use strict";
    window.addEventListener("load", function (evt) {
        var css = "";
        css += ".framewindow {" +
            "	position: fixed;" +
            "	left: 0;" +
            "	top: 0;" +
            "	width: 100vw;" +
            "	height: 100vh;" +
            "	background: #000;" +
            "	background-color:rgba(0,0,0,0.5);" +
            "	display: none;" +
            "	justify-content: center;" +
            "	align-items: center;" +
            "}";
        css += ".framewindow>div {" +
            "	background: #fff;" +
            "	border-radius: 5px;" +
            "	padding:5px;" +
            "}";

        css += "	.framewindow>div>iframe {" +
            "	border: 0;" +
            "	width: 100%;" +
            "	height: 100%;" +
            "}";

        css += "	.framewindow>div>a {" +
            "	position: absolute;" +
            "	top:5px;" +
            "	right:5px;" +
            "	font-size:50px;" +
            "	color: #fff;" +
            "	width:50px;" +
            "	text-align:center;" +
            "	cursor: pointer;" +
            "	text-decoration: none;" +
            "}";

        var style = document.createElement("style");
        style.setAttribute("type", "text/css");
        if (style.styleSheet) {// IE
            style.styleSheet.cssText = css;
        } else {// w3c
            style.appendChild(document.createTextNode(css));
        }
        var heads = document.getElementsByTagName("head");
        if (heads.length) {
            heads[0].appendChild(style);
        } else {
            document.documentElement.appendChild(style);
        }
    });
    var $ = function () {

    };
    $.extend = function () {
        var a = arguments, t = a[0], k, x, i = 1, z = a.length;
        while (i < z)if (x = a[i++])for (k in x)t[k] = x[k];
        return t;
    };

    var defaultSetting = {
        width: "80vw",
        height: "80vh",
        url: null,
        onclose: null,
        onload: null
    };

    //you can set min zIndex
    FrameWindow.currentZIndex = 999;
    /**
     * 构造器
     * @param setting
     */
    function FrameWindow(setting) {
        this.setting = $.extend({}, defaultSetting, setting);

        this.windowEL = null;
        this.frameEL = null;
        this.closeBtnEL = null;


        this.showing = false;

        //call method
        privateMethod.init.call(this);
    }

    var privateMethod = {
        init: function () {
            privateMethod.createView.call(this);
            privateMethod.addEventListener.call(this);
        },
        createView: function () {
            var setting = this.setting;

            //crate window box
            var windowbox = document.createElement('div');
            windowbox.style.width = setting.width;
            windowbox.style.height = setting.height;

            var closeBtnEL = document.createElement("a");
            closeBtnEL.innerHTML = "×";
            closeBtnEL.className = "closeBtn";
            windowbox.appendChild(closeBtnEL);


            var contentFrameEL = document.createElement("iframe");
            contentFrameEL.frameBorder = "0";
            windowbox.appendChild(contentFrameEL);

            var artical = document.createElement("article");
            artical.className = "framewindow";
            artical.style.zIndex = FrameWindow.currentZIndex++;
            artical.appendChild(windowbox);

            document.body.appendChild(artical);

            this.windowEL = artical;
            closeBtnEL.frameWindow = this;
            this.closeBtnEL = closeBtnEL;

            contentFrameEL.frameWindow = this;
            this.frameEL = contentFrameEL;
        },

        addEventListener: function () {
            //close btn
            this.closeBtnEL.addEventListener("click", function (evt) {
                var fw = this.frameWindow;
                fw.close();
            });

            this.frameEL.addEventListener("load", function (evt) {
                var fw = this.frameWindow;
                var win = this.contentWindow;
                var setting = fw.setting;
                //回调加载完成
                var onloadCallback = setting.onload;
                if (onloadCallback) {
                    onloadCallback.apply(win);
                }
                //添加窗口关闭监听
                win.close = function () {
                    fw.close();
                };
            });

        }


    };
    //public method
    FrameWindow.prototype = {
        open: function (url) {
            if (!this.showing) {
                this.windowEL.style.display = "flex";
                this.showing = true;
            }
            this.frameEL.src = url;
        },
        close: function () {
            if (this.showing) {
                this.windowEL.style.display = "none";
                this.showing = false;
                this.destroy();
            }
        },
        destroy: function () {
            this.frameEL = null;
            this.closeBtnEL = null;
            this.windowEL.parentNode.removeChild(this.windowEL);
        }
    };


    //expose
    window.FrameWindow = FrameWindow;

    window.addEventListener("load", function (evt) {
        var as = document.querySelectorAll("a[target='frameWindow']");
        for (var i = as.length; i-- > 0;) {
            as[i].addEventListener("click", function (evt) {
                evt.preventDefault();
                var fw = new FrameWindow();
                fw.open(this.href);
            });
        }
    });

}(window, window.document);