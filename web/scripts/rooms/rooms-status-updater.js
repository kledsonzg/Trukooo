const client = new XMLHttpRequest();

client.addEventListener("loadend", function() { OnClientReceiveResponse(); } );
client.open('GET', 'rooms');
client.send();

function OnClientReceiveResponse()
{
    if(client.responseText == null)
        return;
    console.log(client.responseText);
}