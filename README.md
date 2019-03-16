### netty-chat

Netty结合webSocket做简单的聊天案例

案例效果图:

窗口A给窗口B发送消息:
![5791552706434_.pic.jpg](https://upload-images.jianshu.io/upload_images/15181329-435eab3ddc05f7ed.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

窗口B回复窗口A的消息
![5801552706540_.pic.jpg](https://upload-images.jianshu.io/upload_images/15181329-8a302f144acab26e.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

这里只列出前端代码:

```html
<body>
<input type="text" id="message">
<input type="button" value="发送消息" onclick="sendMsg()">
<br/>
接收到消息:
<p id="server_message" style="background-color: #AAAAAA"></p>

<script>
    var websocket = null;
    //判断当前浏览器是否支持 webSocket
    if (window.WebSocket) {
        websocket = new WebSocket("ws://127.0.0.1:9001/ws");
        websocket.onopen = function (ev) {
            console.log("建立连接");
        }
        websocket.onclose = function (ev) {
            console.log("断开连接");
        }
        websocket.onmessage = function (ev) {
            console.log("接收到服务器的消息" + ev.data);
            var server_message = document.getElementById("server_message");
            server_message.innerHTML += ev.data + "<br/>";
        }
    } else {
        alert("当前浏览器不支持 webSocket")
    }

    function sendMsg() {
        var message = document.getElementById("message");
        websocket.send(message.value)
    }
</script>
</body>
```
后端代码有详细的注释,具体看案例中的代码;

