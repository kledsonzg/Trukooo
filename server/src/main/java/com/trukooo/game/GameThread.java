package com.trukooo.game;

import java.net.InetSocketAddress;
import com.sun.net.httpserver.*;

import com.trukooo.util.Utility;

public class GameThread extends Thread
{
    private int port = -1;
    private String hostName = "";

    private GameThread() {}
    public GameThread(String _hostname, int listenToPort) throws Exception
    {
        if(listenToPort < 0)
            throw new Exception("A porta especificada não é válida: " + listenToPort);
        if(Utility.isValidIP(_hostname) == false)
            throw new Exception("O hostname especificado não é válido: " + _hostname);
        
        hostName = _hostname;
        port = listenToPort;
    }

    @Override
    public void run()
    {
        try
        {
            HttpServer gameServer = HttpServer.create(new InetSocketAddress(hostName, port), 0);
            gameServer.createContext("/", new GameServer() );
            gameServer.createContext("/game", new GameServer() );
            gameServer.start();
        }
        catch(Exception e)
        {
            System.out.println("Uma exceção foi gerada no Thread que tentou criar servidor da sala: " + hostName + ":" + port);
        }
    }
}

