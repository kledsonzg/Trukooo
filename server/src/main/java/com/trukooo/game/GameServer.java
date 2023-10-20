package com.trukooo.game;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.trukooo.subinterface.IPlayer;
import com.trukooo.util.Utility;

public class GameServer implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange)
    {
        String url = exchange.getRequestURI().toString();
        Utility.print("url (uri): " + url);
        try
        {
            String file = Utility.getFileByRequestURI(exchange.getRequestURI() );
            if(url.equals("/") || url.isEmpty() )
            {
                sendResponse(exchange, null, 200);
                return;
            }
            if(Files.exists(Path.of(Utility.getFileByRequestURI(exchange.getRequestURI() ) ) ) == true)
            {            
                String content = Utility.readFile(file);
                sendResponse(exchange, content.getBytes(), 200);
                return;
            }
            else if(url.equals("/game") )
            {
                String cookieString = exchange.getRequestHeaders().get("Cookie").get(0);
                IPlayer _player = getPlayerInfoByCookie(cookieString, exchange.getLocalAddress().getPort() );

                if(_player == null)
                {
                    sendResponse(exchange, null, 401);
                    return;
                }
                if(GameManager.isValidQueuePlayerConnection(_player.Name, _player.Port) == false)
                {
                    sendResponse(exchange, null, 401);
                    return;
                }
                
                URI roomFileURI = URI.create("/pages/rooms/waiting-room.html");
                String content = Utility.readFile(Utility.getFileByRequestURI(roomFileURI) );
                exchange.getResponseHeaders().set("Content-Type", Files.probeContentType(Path.of(Utility.getFileByRequestURI(roomFileURI) ) ) );

                //Utility.print("Headers keys: " + exchange.getRequestHeaders().keySet() );
                //Utility.print("Cookie keys: " + exchange.getRequestHeaders().get("Cookie").toString() );
                //Utility.print("Cookie keys: " + exchange.getRequestHeaders().get("Cookie").get(0) );

                sendResponse(exchange, content.getBytes(), 200);
                return;
            }
            sendResponse(exchange, null, 404);
            
        }       
        catch(Exception e)
        {
            System.out.println("Uma exceção foi gerada no handler na sala de jogo: " + exchange.getRequestURI().getRawQuery() );
            System.out.println(e.getMessage() );
        }
    }

    private IPlayer getPlayerInfoByCookie(String cookieString, int port)
    {
        int index = -1;
        index = cookieString.indexOf("tkd=");
        if(index == -1)
        {
            return null;
        }
        cookieString = cookieString.substring(index + "tkd=".length() );
        
        index = cookieString.indexOf("<playername>");
        if(index == -1)
        {
            return null;
        }
        String playerName = cookieString.substring(index + "<playername>".length(), cookieString.indexOf("</playername>") );

        index = cookieString.indexOf("<playertoken>");
        if(index == -1)
        {
            return null;
        }
        String playerHash = cookieString.substring(index + "<playertoken>".length(), cookieString.indexOf("</playertoken>") );

        return new IPlayer(playerName, playerHash, port);
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
