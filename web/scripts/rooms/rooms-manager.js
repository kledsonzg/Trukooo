var parentPage = parent.document;
var enter_panels = document.getElementsByClassName('roomEnterBox');

for(let i = 0; i < enter_panels.length; i++)
{
    enter_panels[i].addEventListener('mouseenter', function() {
        OnMouseHover(enter_panels[i] );
    } );
    enter_panels[i].addEventListener('mouseleave', function() {
        OnMouseLeave(enter_panels[i] );
    } );

    enter_panels[i].addEventListener('click', function()  { initializeConnectionWithGameRoom(enter_panels[i] ); } );
}

Array.from(document.getElementsByClassName('divButton') ).forEach(element => {
    element.addEventListener('mouseenter', function() { OnRightPanelButtonHover(element); } );
    element.addEventListener('mouseleave', function() { OnRightPanelButtonLeave(element); } );
    element.addEventListener('click', function() { OnRightPanelButtonClick(element); } );
});

function initializeConnectionWithGameRoom(element)
{
    let port = 40021;
    let gameRoom = parseInt(element.parentElement.parentElement.getElementsByClassName('roomName')[0].innerHTML.replace('Sala ', '') ) - 1;

    port += gameRoom;
    console.log('opening room with port: ' + port);

    Array.from(enter_panels).forEach(element => element.remove() );

    let script = parentPage.createElement('script');
    script.setAttribute('language', 'JavaScript');
    script.setAttribute('connector_param', port);
    script.id = 'connector'
    script.src = '../../scripts/rooms/connector.js';
    
    parentPage.head.appendChild(script);
}

function OnMouseHover(element)
{
    element.classList.add('mousehover');
}

function OnMouseLeave(element)
{
    element.classList.remove('mousehover');
}

function OnRightPanelButtonHover(element)
{
    element.classList.add('mouseHover');
}

function OnRightPanelButtonLeave(element)
{
    element.classList.remove('mouseHover');
}

function OnRightPanelButtonClick(element)
{
    if(element.classList.contains('homeBtn') )
        return parentPage.location.reload();
    else if(element.classList.contains('refreshBtn') )
    {
        removeStatusReloaderFrame();
        addStatusReloaderFrame();
    }
}

function removeStatusReloaderFrame()
{
    reloaderFrame = document.getElementById('roomReloader');
    if(reloaderFrame == null)
        return;

    reloaderFrame.remove();
}

function addStatusReloaderFrame()
{
    /* Para conseguir fazer uma requisição HTTP do mesmo hostname mas com a porta diferente,
     resolvi usar o 'iframe'. Com o 'iframe' é impossível obter um documento(s) de um host diferente, sendo assim,
     nos possibilita fazer a comunicação com o servidor com uma porta diferente.

     A porta '40020' será a porta exclusiva para fazer requisições para atualizar os status de todas as
     salas de jogos.
    */
    var reloaderFrame = document.createElement('iframe');
    reloaderFrame.src = "http://" + getPageAddress() + ':40020/frame';
    reloaderFrame.id = 'roomReloader';

    document.body.appendChild(reloaderFrame);
}

function getPageAddress()
{
    let address = window.location.hostname;
    
    return address;
}
