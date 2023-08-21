console.log('the connector script works!');

const scriptDOM = document.getElementById('connector');
const port = parseInt(scriptDOM.getAttribute('connector_param') );

scriptDOM.removeAttribute('connector_param');

const httpClient = new XMLHttpRequest();

httpClient.addEventListener('loadend', function() { OnConnectionResponse(); } );
httpClient.open('POST', 'connect');
httpClient.send(
    'playername: ' + GetPlayerName() +
    '\nroom: ' + port
);

function GetPlayerName()
{
    if(document.cookie.length < 1)
        return '';
    
    return document.getElementById('topUserName').innerHTML;
}

function OnConnectionResponse()
{
    if(httpClient.responseText == null || httpClient.status != 200)
        return;
    
}

