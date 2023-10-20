package com.trukooo.game;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.trukooo.util.Utility;

public class PlayerAlive implements HttpHandler
{
    private QueuePlayer playerInQueue = null;

    private PlayerAlive() {}
    public PlayerAlive(QueuePlayer playerInQueueRef)
    {
        playerInQueue = playerInQueueRef;
    }

    @Override 
    public void handle(HttpExchange exchange)
    {
        try
        {   
            if(playerInQueue == null)
            {
                sendResponse(exchange, null, 401);
                return;
            }
        }
        catch(Exception e)
        {
            System.out.println("Uma exceção foi gerada no handler do verificador de requests de jogador conectado: " + exchange.getRequestURI().getRawQuery() );
            System.out.println(e.getMessage() );
        }
    }

    private void sendResponse(HttpExchange exchange, byte[] responseBytes, int code) throws IOException
    {
        int responseLength = 0;
        if(responseBytes != null)
            responseLength = responseBytes.length;
        
        exchange.sendResponseHeaders(code, responseLength);

        OutputStream stream = exchange.getResponseBody();
        if(responseLength != 0)
        {
            stream.write(responseBytes);
        }
        
        stream.flush();
        stream.close();
    }
}
