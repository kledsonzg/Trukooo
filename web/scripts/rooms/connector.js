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

    let result = httpClient.responseText;
    if(result.includes("playerkey: ") == false)
        return;

    let playerkey = result.replace("playerkey: ", "");

    SetPlayerToken(playerkey);  
    loadTargetRoom(port);
}

function SetPlayerToken(token)
{
    let doc = new DOMParser().parseFromString(document.cookie.replace('tkd=', ''), "text/xml");
    let elements = doc.getElementsByTagName('playertoken');
    if(elements.length != 0)
    {
        Array.from(elements).forEach(element => element.remove() );
    }

    let tokenElement = doc.createElement('playertoken');
    tokenElement.innerHTML = token;
    doc.firstElementChild.appendChild(tokenElement);

    document.cookie = 'tkd=' + new XMLSerializer().serializeToString(doc.documentElement);
}

function loadTargetRoom(port)
{
    window.location.assign('http://' + getPageAddress() + ":" + port + "/game");
}

function getPageAddress()
{
    let address = window.location.hostname;
    
    return address;
}

function GetPlayerName()
{
    if(document.cookie.length < 1)
        return '';
    
    return document.getElementById('topUserName').innerHTML;
}
