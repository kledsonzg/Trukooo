var parentPage = parent.document;
var playerForm = document.getElementsByName('playerForm')[0];
document.getElementById('playerNameInput').value = GetPlayerName();

parentPage.body.addEventListener('click', function(){
    parentPage.getElementsByClassName('settingsWindow')[0].remove();
    parentPage.body.removeEventListener('click');
} );

function OnApply()
{ 
    var formChilds = playerForm.children;
    var playerName = '';

    for(var i = 0; i < formChilds.length; i++)
    {
        if(!(formChilds[i].id == 'playerNameInput') )
            continue;

        playerName = formChilds[i].value;
        break;
    }
    if(playerName.length > 30 || playerName.trim().length < 3)
    {
        window.alert('O nome de usuário deve conter no mínimo 3 caracteres e no máximo 30 caracteres!');
        return;
    }
    if(playerName == GetPlayerName() )
    {
        CloseSettingsWindow();
        return;
    }
    SetPlayerName(playerName);
}

function SetPlayerName(playerName)
{
    if(playerName.length < 1)
        return;
  
    var xmlContent;
    if(parentPage.cookie.length < 1)
    {
        xmlContent = new Document();
        xmlContent.appendChild(xmlContent.createElement('data') );
        var nameNode = xmlContent.createElement('playername');
        nameNode.innerHTML = playerName;
        xmlContent.firstChild.appendChild(nameNode);
    }
    else
    {
        xmlContent = (new DOMParser).parseFromString(parentPage.cookie.replace('tkd=', ''), 'text/xml');
        xmlContent.getElementsByTagName('playername')[0].innerHTML = playerName;
    }    

    parentPage.cookie = 'tkd=' + (new XMLSerializer()).serializeToString(xmlContent.firstChild);
    CloseSettingsWindow();
    parentPage.location.reload();
}

function GetPlayerName()
{
    if(parentPage.cookie.length < 1)
        return '';
    
    return parentPage.getElementById('topUserName').innerHTML;
}

function CloseSettingsWindow()
{
    parentPage.getElementsByClassName('settingsWindow')[0].remove();
}