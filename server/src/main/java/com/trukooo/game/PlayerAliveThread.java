package com.trukooo.game;

import java.net.InetSocketAddress;
import com.sun.net.httpserver.*;

public class PlayerAliveThread extends Thread
{
    private QueuePlayer playerInQueue;
    private PlayerAliveThread(){}
    
    public PlayerAliveThread(QueuePlayer playerInQueueRef)
    {
        playerInQueue = playerInQueueRef;
    }

    @Override
    public void run()
    {
        try
        {
            HttpServer playerAliveServer = HttpServer.create(new InetSocketAddress("127.0.0.1", playerInQueue.getConnectionPort() ), 0);
            playerAliveServer.createContext("/alive", new PlayerAlive(playerInQueue) );
            playerAliveServer.start();
        }
        catch(Exception e)
        {
            System.out.println("Uma exceção foi gerada no Thread que tentou criar o servidor do verificador 'player alive': " + playerInQueue);
        }
    }
}
