<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/11/30
  Time: 11:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type= "text/javascript">
        var websocket = null;
        function connection() {
            var username = document.getElementById("name").value;
            if ('WebSocket' in window) {
                websocket = new WebSocket("ws://" + document.location.host + "/websocket/" + username);
            } else {
                alert("不支持wensocket")
            }
            websocket.onopen = function () {
                document.getElementById("message").innerHTML = "建立链接了";
            }
            websocket.onmessage = function (event) {
                var data = event.data;
                document.getElementById("message").innerHTML = data;
            }
            websocket.onerror = function () {
                document.getElementById("message").innerHTML = "出现异常";
            }
            websocket.onclose = function () {
                document.getElementById("message").innerHTML = "链接关闭了";
            }
            window.onbeforeunload = function () {
                if (websocket != null) {
                    websocket.close()
                }
            }
        }

        function sendMessage() {
            //获取发送给谁
            var toUser = document.getElementById("toUser").value;
            //获取发送内容
            var toMessage = document.getElementById("toMessage").value;
            if (websocket != null) {
                var message = '{"toUser":"' + toUser + '","toMessage":"' + toMessage + '"}';
                websocket.send(message);
            }
        }
    </script>
</head>
<body>
发送者：<input type="text" id="name" name="name">
<button onclick="connection()">链接</button><br>
接收者名字：<input type="text" id="toUser" name="toUser"><br>
内容：<input type="text" id = "toMessage" name="toMessage">
<button onclick="sendMessage()">发送</button><br><br>
<span id="message"></span>
</body>
</html>
