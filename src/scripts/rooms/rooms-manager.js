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

    Array.from(document.getElementsByClassName('divButton') ).forEach(element => {
        element.addEventListener('mouseenter', function() { OnRightPanelButtonHover(element); } );
        element.addEventListener('mouseleave', function() { OnRightPanelButtonLeave(element); } );
        element.addEventListener('click', function() { OnRightPanelButtonClick(element); } );
    });
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
        httpClientThread.terminate();
        httpClientThread = GetRefreshingWorker();
    }
}

// HTTP Client
var httpClientThread = GetRefreshingWorker();

function GetRefreshingWorker() { return (new Worker('../../scripts/rooms/rooms-status-updater.js') ); };
