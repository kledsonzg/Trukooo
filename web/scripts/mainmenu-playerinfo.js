/* Este script fica responsável pela interação do usuário com 
   o painel de informações do jogador. */

var panel = document.getElementById('topUserInfoPanel');
var settingsIcon = document.getElementById('topUserEditIcon');
var nameNode = document.getElementById('topUserName');

panel.addEventListener('mouseenter', function() { OnPanelHover(panel); } );
panel.addEventListener('mouseleave', function() { OnPanelExit(panel); } );

settingsIcon.addEventListener('mouseenter', function() { OnSettingsIconHover(); } );
settingsIcon.addEventListener('mouseleave', function() { OnSettingsIconExit(); } );
settingsIcon.addEventListener('click', function() { OnSettingsIconClick(); } );

OnPageLoad();

function OnPageLoad()
{
    if(document.cookie.length < 1)
        return OnSettingsIconClick();

    UpdateName();
}

function OnPanelHover()
{
    panel.style.setProperty('border-width', '1px');
    panel.style.setProperty('background-color', '#FFFFFF80');
    settingsIcon.style.setProperty('display', 'block');
}

function OnPanelExit()
{
    panel.style.setProperty('border-width', '0px');
    panel.style.setProperty('background-color', 'transparent');
    settingsIcon.style.setProperty('display', 'none');
}

function OnSettingsIconHover()
{
    settingsIcon.style.backgroundColor = '#A6D0DD';
}

function OnSettingsIconExit()
{
    settingsIcon.style.backgroundColor = 'transparent';
}

function OnSettingsIconClick()
{
    var bodyDoc = document.body;

    // Se a janela já estiver vísivel na página, a função não precisa prosseguir.
    for(let i = 0; i < bodyDoc.children.length; i++)
    {
        if(bodyDoc.children[i].classList.contains('settingsWindow') )
        {
            bodyDoc.children[i].remove();
            return;
        }
    }

    var settingsWindow = document.createElement('div');
    var frame = document.createElement('iframe');

    settingsWindow.className = 'settingsWindow';
    frame.src = 'pages/playersettings/settings.html';

    settingsWindow.appendChild(frame);

    bodyDoc.appendChild(settingsWindow);
}

function UpdateName()
{
    if(document.cookie.length < 1)
        return OnSettingsIconClick();

    var xmlContent = (new DOMParser).parseFromString(document.cookie.replace('tkd=', ''), 'text/xml');

    nameNode.innerHTML = xmlContent.getElementsByTagName('playername')[0].innerHTML;
}