<%@page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <base href="/">
    <title>师道教学系统</title>


    <style>
        body {
            margin: 0;
            overflow: auto;
            height: 100vh;
            min-width: 1024px;
            min-height: 500px;
        }

        body > iframe {
            width: 100%;
            height: 100%;
            border: 0;
            display: none;
        }

    </style>

</head>
<body>
<article class="loading">
    加载中。。。
</article>
<%
    if(session.getAttribute("TOKEN")==null){
%>
<iframe src="/login/view.html"></iframe>
<%}else{%>
<iframe src="/index/view.html"></iframe>
<%}%>
<script src="/component.FrameWindow.js"></script>
<script src="/component.Toast.js"></script>
<script src="/component.Broadcast.js"></script>
<script src="/component.ConfirmDialog.js"></script>
<script>
    document.querySelector("iframe").addEventListener("load",function (evt) {
        this.style.display="block";
        document.querySelector(".loading").style.display="none";
    });
</script>
</body>
</html>
