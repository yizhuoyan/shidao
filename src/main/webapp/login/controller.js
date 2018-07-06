;+function () {
    "use strict";

    var $loginFormEL;
    var $loginBtnEL;

    $(function () {
        $loginFormEL = $("#loginForm");
        $loginBtnEL = $loginFormEL.find("button[type=submit]");

        $loginFormEL.submit(handleLoginFormSubmit);


    });


    var handleLoginFormSubmit = function (evt) {

        $loginBtnEL.attr("disabled", true);
        $loginBtnEL.data("old-text", $loginBtnEL.text());
        $loginBtnEL.text("登录中...");
        var url = "/login";
        $.ajaxPost(url, $(this).serialize())
            .done(function (currentUser) {
                window.top.toast("登录成功");
                window.session.saveCurrentUser(currentUser);

                if (currentUser.firstLogin) {
                    window.location.href = "/first-login/view.html";
                } else {
                    window.location.href = "/index/view.html";
                }
            })
            .fail(function () {
                shakeIt($loginFormEL[0]);
            })
            .always(function () {
                $loginBtnEL.attr("disabled", false);
                $loginBtnEL.text($loginBtnEL.data("old-text"));
            });

        return false;
    };


    var shakeIt = function (e) {
        var offsets = [0, 2, -2, 4, -4, 8, -8, 16, -16];
        offsets = offsets.concat(offsets, offsets);
        var run = function () {
            e.style.transform = "translate(" + offsets.pop() + "px,0px)";
            if (offsets.length) {
                setTimeout(run, 1000 / 30);
            }
        };
        run();
    };

}();