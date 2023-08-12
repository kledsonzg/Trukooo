// Este script fica responsável por criar os símbolos e aplicar as animações.

// Contagem de símbolos na tela.
const symbolsCount = 20;

const symbolNames = ['animSymbols-diamond', 'animSymbols-heart',
    'animSymbols-club', 'animSymbols-spade'];
const animPropertys = ['--anim_left_start', '--anim_left_end',
    '--anim_top_start', '--anim_top_end'];

for(var i = 0; i < symbolsCount; i++)
{
    var symbol = document.createElement('div');

    // Randomizando o símbolo que irá aparecer na tela.
    var symbolChoice = parseInt(Math.random() * 4);

    // Randomizando as posições que serão aplicadas depois.
    var offset = [4];
    for(var j = 0; j < 4; j++)
    {
        offset[j] = parseInt(Math.random() * 98) + '%';
    }   

    //Setando a classe que possui a propriedade de animação no elemento criado.
    symbol.setAttribute('class', symbolNames[symbolChoice] );

    // Setando as propriedades de posição para as animações.
    for(var j = 0; j < 4; j++)
    {
        symbol.style.setProperty(animPropertys[j], offset[j] );
    }

    document.body.appendChild(symbol );
}