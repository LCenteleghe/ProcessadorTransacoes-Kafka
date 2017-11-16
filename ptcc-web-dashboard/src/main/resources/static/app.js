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
            showTransacao(message.body);
        });
        
        stompClient.subscribe('/dashboard/valor-transacoes-por-tipo-estabelecimento', function (message) {
            showTotal(message.body);
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

function showTotal(message) {
    message = JSON.parse(message);
    var key = Object.keys(message)[0];
    key = key.toLowerCase();
    var valor = Object.values(message)[0];
    if ($("#span-" + key).length > 0) {
        $("#span-" + key).text(key + ": " + valor);
    } else {
        $("#totais-div").append("<span id=\"span-" + key + "\" class=\"label label-primary text-uppercase\">" + key + ": " + valor + "</span>");
    } 
}

function showTransacao(message) {
    message = JSON.parse(message);
    var data = new Date(message.data);
    var html = "<tr>";
    html += "<td>" + message.codigo + "</td>";
    html += "<td>" + message.numeroCartao + "</td>";
    html += "<td>" + message.valor + "</td>";
    html += "<td>" + data.getDate() + "/" + data.getMonth() + "/" + data.getFullYear() + " " + data.getHours() + ":" + data.getMinutes() + "</td>";
    html += "<td>" + message.estabelecimento.nome + " - " + message.estabelecimento.endereco.cidade + " (" + message.estabelecimento.tipoEstabelecimento + ")" + "</td>";
    html += "</tr>";

    console.log(html);
    $("#transacoes-tbody").append(html);
    countMessages++;
    if(countMessages > 10){
        $("#transacoes-tbody tr:first").remove();
    }
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
});
