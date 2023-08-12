// Este script fica responsável por receber as interações do usuário.

const colors = ['#000000D6', 'black', '#B04759', '#CD1818'];

var btnElements = document.getElementsByClassName('centerButton');

Array.from(btnElements).forEach(element => {
    element.setAttribute('onmouseover', 'OnButtonHover(this)');
    element.setAttribute('onmouseout', 'OnButtonExit(this)');
    element.addEventListener('click', function(){
        OnButtonClick(element);
    } );
});

function OnButtonClick(element)
{
    switch(element.innerHTML)
    {
        case 'JOGAR!':
        {
            ShowRooms();
            break;
        }
        default:
        {
            return;
        }
    }
}

function OnButtonHover(element)
{
    var parent = element.parentElement;

    if(!(element.id == 'exitButton') )
        parent.style.setProperty('background-color', colors[1] );
    else parent.style.setProperty('background-color', colors[3] );

    parent.style.cursor = 'pointer';

    element.style.bottom = '2px';
}

function OnButtonExit(element)
{
    var parent = element.parentElement;

    if(!(element.id == 'exitButton') )
        parent.style.setProperty('background-color', colors[0] );
    else parent.style.setProperty('background-color', colors[2] );

    parent.style.cursor = 'default';

    element.style.removeProperty('bottom');
}

function ShowRooms()
{
    var container = document.getElementById('centerContainer');
    container.remove();

    var frame = document.createElement('iframe');
    frame.src = 'pages/rooms/index.html';
    frame.className = 'roomsWindow';

    document.body.appendChild(frame);
}