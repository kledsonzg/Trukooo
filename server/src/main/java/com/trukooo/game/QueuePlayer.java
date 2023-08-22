package com.trukooo.game;

import com.trukooo.util.Utility;

public class QueuePlayer 
{
    private Game gameToEnter = null;
    private String player_info_hash = null;
    private String name = null;
    private String ip = null;
    private int connectionPort = 0;
    
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

    public Game getTargetGame() { return gameToEnter; }
    public String getPlayerIP() { return ip; }
}
