package com.trukooo.game;

import com.trukooo.util.Utility;

/* TO DO:
    Criar um thread com um timer assim que uma instância dessa classe for criada, para verificar se 'pageLoaded' é true em até 30 segundos.
    Se não for true, significa que a fila foi abandonada, e a instância pode ser removida da lista do GameManager.
*/
public class QueuePlayer 
{
    private Game gameToEnter = null;
    private String player_info_hash = null;
    private String name = null;
    private String ip = null;
    private int connectionPort = 0;

    //'pageLoaded' representa se o jogador já está com o navegador na página da sala.
    private boolean pageLoaded = false;
    //'ready' representa se o jogador já está preparado para iniciar o jogo.
    private boolean ready = false;
    
    public QueuePlayer(Game game, String nickname, String playerIP, int port, String hash)
    {
        gameToEnter = game;
        name = nickname;
        connectionPort = port;
        player_info_hash = hash;
        ip = playerIP;
    }

    public boolean isThisPlayer(String nickname, int port)
    {
        return Utility.validateHash(player_info_hash, ip + ":" + nickname + ":" + port);
    }

    public void setPlayerPageAsLoaded()
    {
        //Podemos criar um thread para checar se o client deste player está enviando requests periodicamente.
        //Se o client parar de enviar as requests, significa que o player deixou a sala ou perdeu a conexão com o mesmo.
        if(!pageLoaded)
        {
            pageLoaded = true;

            (new PlayerAliveThread(this) ).start();
        }
    }

    public void setReadyBoolean(boolean isReady) { ready = isReady; }

    public Game getTargetGame() { return gameToEnter; }
    public int getConnectionPort() { return connectionPort ;}
    public String getPlayerIP() { return ip; }
    public String getPlayerName() { return name; }
}
