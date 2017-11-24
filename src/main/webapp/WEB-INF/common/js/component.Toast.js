;+function (window, document, undefined) {
    "use strict";

    var defautSetting = {
        timeout: 3000,
        fadeIn: 1000,
        fadeOut: 1000,
    };

    var toastEL = (function (setting) {
        var toastEL = document.createElement("div");
        var messageEL = document.createElement("span");
        toastEL.appendChild(messageEL);
        var divCss = {
            position: "fixed",
            zIndex: 2147483647,
            bottom: "150px",
            left: 0,
            right: 0,
            border: "none",
            textAlign: "center"
        };
        var spanCss = {
            "background": "#000",
            "color": "#fff",
            "border": "1px solid black",
            "font-size": "14px",
            "padding": "5px 1em",
            "border-radius": "5px",
            "box-shadow": "inset 0 1px 1px rgba(0, 0, 0, .075)",
            "display": "none"
        };
        var k;
        for (k in divCss) {
            toastEL.style[k] = divCss[k];
        }
        for (k in spanCss) {
            messageEL.style[k] = spanCss[k];
        }
        window.addEventListener("load", function () {
            window.document.body.appendChild(toastEL);
        });
        return messageEL;
    })(defautSetting);

    //save all messages
    var messages = [];

    var doToast = function (message, timeout, whenDone) {
        timeout = timeout || defautSetting.timeout;
        messages.push({message: message, timeout: timeout, done: whenDone});
        if (toastEL.style.display === "none") {
            toastEL.style.display = "inline-block";
            toastMessages();
        }
    };
    //显示消息
    var toastMessages = function () {
        if (messages.length === 0) {
            //无消息,不再显示
            toastEL.style.display = "none";
            return;
        }
        var m = messages.shift();
        toastEL.innerHTML = m.message;
        fadeIn(toastEL, defautSetting.fadeIn, function () {
            setTimeout(function () {
                fadeOut(toastEL, defautSetting.fadeOut, function () {
                    if (m.done) {
                        m.done.apply(m.message);
                    }
                    toastMessages();
                });
            }, m.timeout);
        });
    };
    /**
     * 淡入
     * @param {Object} e 目标
     * @param {Object} t 时间
     * @param {Object} f 完成回调
     */
    var fadeIn = function (e, t, f) {
        e.style.opacity = 0;
        e.opacity = 0;
        var timeout = 30;//1000/30;
        //每次递增
        var increase = (100 * timeout / t) ^ 0;

        function run() {
            var next = e.opacity + increase;

            if (next >= 100) {
                e.style.opacity = 1;
                delete e.opacity;
                f();
                return;
            }
            e.opacity = next;
            e.style.opacity = next / 100;
            setTimeout(run, timeout);
        }

        run(timeout);
    };
    /**
     * 淡入
     * @param {Object} e 目标
     * @param {Object} t 时间
     * @param {Object} f 完成回调
     */
    var fadeOut = function (e, t, f) {
        e.style.opacity = 1;
        e.opacity = 100;
        var timeout = 30;//1000/30;
        //每次递增
        var increase = (100 * timeout / t) ^ 0;

        function run() {
            var next = e.opacity - increase;
            if (next <= 0) {
                e.style.opacity = 0;
                delete e.opacity;
                f();
                return;
            }
            e.opacity = next;
            e.style.opacity = next / 100;
            setTimeout(run, timeout);
        }

        run(timeout);
    };
    //expode 
    window.toast = function () {
        var message, timeout, whenDone;
        for (var i = arguments.length, arg; i-- > 0;) {
            arg = arguments[i];

             if (typeof arg === "function") {
                whenDone = arg;
            } else if (typeof arg === "number") {
                timeout = arg;
            }else{
                     message = arg;
             }
        }
        if(Array.isArray(message)){
            message=message.join("<br>");
        }
        doToast(message, timeout, whenDone);
    };

}(window, document);
    
    
    
    
    
    
