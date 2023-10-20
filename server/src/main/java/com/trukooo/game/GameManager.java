package com.trukooo.game;

import java.util.ArrayList;
import java.util.List;

import com.trukooo.util.Utility;

public class GameManager 
{
    private static int[] ports = null; 
    private static boolean running = false;
    private static Game[] games = {null, null, null, null, null, null, null, null, null, null};
    private static GameThread[] gameThreads = new GameThread[games.length];

    private static List<QueuePlayer> queuePlayers = new ArrayList<QueuePlayer>();

    public static void start() throws Exception
    {     
        ports = new int[games.length];
        for(int i = 0; i < games.length; i++)
        {
            games[i] = new Game();
            ports[i] = 40021 + i;

            gameThreads[i] = new GameThread("127.0.0.1", ports[i] );
            gameThreads[i].start();
        }

        running = true;
    }

    public static void stop()
    {
        for (GameThread gameThread : gameThreads) {
            gameThread.interrupt();
        }
    }

    public static Game getRunningGame(int index) 
    {
        Game resultGame = null;
        if(index < 0 || index >= games.length)
            return resultGame;
        
        resultGame = games[index];
        return resultGame;
    }

    public static boolean isRunning() { return running; }

    public static boolean isNicknameInsideAGame(String nickname)
    {
        if(running == false)
            return false;
        for(int i = 0; i < games.length; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                Player player = games[i].getPlayer(j);
                if(player == null)
                    continue;
                
                if(player.getName().equals(nickname) )
                    return true;
            }
        }
        return false;
    }

    public static boolean insertPlayer(String nickname, int port)
    {
        QueuePlayer playerFound = null;
        boolean result = false;

        for (QueuePlayer playerInQueue : queuePlayers) {
            if(playerInQueue.isThisPlayer(nickname, port) == false)
                continue;
            
            Player player = null;
            Game game = playerInQueue.getTargetGame();
            try
            {
                player = new Player(nickname, playerInQueue.getPlayerIP() );           
            }
            catch(Exception e)
            {
                Utility.print("Falha ao instanciar um jogador em uma partida.");
                Utility.print(e.getMessage() );
                return false;
            }

            result = game.addPlayer(player);
            playerFound = playerInQueue;
            break;
        }
        
        if(result)
            queuePlayers.remove(playerFound);
        
        return result;
    }

    public static int getGameIndexByPort(int port)
    {
        int portIndex = -1;
        for(int i = 0; i < ports.length; i++)
        {
            if(ports[i] == port)
            {
                portIndex = i;
                break;
            }
        }
        
        return portIndex;
    }

    public static int getQueuePlayersCountInRoom(int port)
    {
        int count = 0;
        for(QueuePlayer queuePlayer : queuePlayers)
        {
            if(queuePlayer.getConnectionPort() != port)
                continue;
            
            count++;
        }

        return count;
    }

    public static boolean isValidQueuePlayerConnection(String playerName, int port)
    {
        for(QueuePlayer playerInQueue : queuePlayers)
        {
            if(playerInQueue.isThisPlayer(playerName, port) )
                return true;
        }

        return false;
    }

    public static void removePlayerFromQueue(String playerName, String ip)
    {
        for(int i = 0; i < queuePlayers.size(); i++)
        {
            if(queuePlayers.isEmpty() || queuePlayers.size() <= i)
                break;
            
            QueuePlayer playerInQueue = queuePlayers.get(i);
            if(playerInQueue.getPlayerIP().equals(ip) == false)
                continue;
            if(playerInQueue.getPlayerName().equals(playerName) == false)
                continue;
            
            queuePlayers.remove(i);
            i--;
        }
    }

    public static boolean queuePlayer(String ip, String playerName, String hash, int port)
    {
        removePlayerFromQueue(playerName, ip);
        return queuePlayers.add(new QueuePlayer(getRunningGame(getGameIndexByPort(port) ), playerName, ip, port, hash) );
    }

    public static void setQueuePlayerAlive(String playerName, int port, boolean isAlive)
    {
        for(QueuePlayer playerInQueue : queuePlayers)
        {
            if(playerInQueue.isThisPlayer(playerName, port) )
            {
                playerInQueue.setPlayerPageAsLoaded();
                break;
            }
        }
    }
}
