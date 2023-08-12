const client = new XMLHttpRequest();

client.addEventListener("load", function() { OnClientReceiveResponse(); } );
client.open('GET', '127.0.0.1:2999/rooms');
client.send();

function OnClientReceiveResponse()
{
    
}