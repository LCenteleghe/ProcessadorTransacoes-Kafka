var stompClient = null;
var countMessages = 0;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#stream").html("");
}

function connect() {
    var socket = new SockJS('/dashboard-websocket-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/dashboard/novas-transacoes', function (message) {
            showMessage(message.body);
        });
        
        stompClient.subscribe('/dashboard/valor-transacoes-por-tipo-estabelecimento', function (message) {
            showMessage(message.body);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function showMessage(message) {
    countMessages++;
    $("#stream").prepend("<tr><td><pre>" + JSON.stringify(JSON.parse(message), undefined, 2) + "</pre></td></tr>");
    if(countMessages > 10){
        // console.log(countMessages);
        $("#stream tr:first").remove();
    }
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
});
