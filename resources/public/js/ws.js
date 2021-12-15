var ws = new WebSocket("ws://127.0.0.1:8811/ws")
    
ws.onopen = function (event) {
   ws.send(JSON.stringify({"ok": true}));
};

ws.onmessage = function (event) {
    var data =JSON.parse(event.data);
    $("#" + data.topic.replace(/\//g ,"_")).val(parseInt(data.payload));
}
