package com.trukooo.server;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.sun.net.httpserver.*;
import com.trukooo.game.GameManager;
import com.trukooo.util.Utility;

public class PageHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        if(exchange.getRequestMethod().equalsIgnoreCase("post") )
        {          
            handlePostResquest(exchange);
            return;
        }
        
        String responseText = "";
        String fileRequested = Utility.getFileByRequestURI(exchange.getRequestURI() );

        Utility.print("File: " + fileRequested);

        FileReader reader = new FileReader(fileRequested, StandardCharsets.UTF_8 );
        
        int readerIndex = -1;
        while((readerIndex = reader.read() ) != -1)
        {
            responseText += (char) readerIndex;
        }
        reader.close();

        byte[] responseBytes = responseText.getBytes(StandardCharsets.UTF_8);
        
        String type = Files.probeContentType(Path.of(fileRequested) );
        exchange.getResponseHeaders().set("Content-Type",  type);

        exchange.sendResponseHeaders(200, responseBytes.length );
        OutputStream outStream = exchange.getResponseBody();
        outStream.write(responseBytes);
        outStream.flush();
        outStream.close();
    }

    private void handlePostResquest(HttpExchange exchange) throws IOException
    {
        InputStream bodyStream = exchange.getRequestBody();
        String bodyString = readStream(bodyStream);
        String uriString = exchange.getRequestURI().toString();
        String clientIP = exchange.getRemoteAddress().getHostName();

        if(uriString.equals("/connect") == false)
        {
            sendResponse(exchange, null, 404);
            return;
        }
        int beginIndex = bodyString.indexOf("playername: "), endIndex = bodyString.indexOf('\n');
        if(beginIndex == -1 || endIndex == -1)
        {
            sendResponse(exchange, null, 400);
            return;
        }
        String playerName = bodyString.substring(beginIndex,  endIndex);

        //Em 'room' obtemos o número da porta da sala.
        beginIndex = bodyString.indexOf("room: "); endIndex = bodyString.length() - 1;
        if(beginIndex == -1 || endIndex < beginIndex)
        {
            sendResponse(exchange, null, 400);
            return;
        }
        int port = 0;
        try 
        {
            port = Integer.parseInt(bodyString.substring(beginIndex, endIndex).replace("room: ", "") );
        } 
        catch (Exception e) {
            Utility.print("Erro ao obter a sala de jogo na leitura de um corpo de uma request.");
            Utility.print(e.getMessage() );

            sendResponse(exchange, null, 404);
            return;
        }

        if(GameManager.isNicknameInsideAGame(playerName) == true || GameManager.getQueuePlayersCountInRoom(port) >= 4) //|| GameManager.getRunningGame(GameManager.getGameIndexByPort(port)).getPlayersCount() == 4)
        {
            sendResponse(exchange, null, 409);
            return;
        }
        String result = Utility.hashString(clientIP + ":" + playerName + ":" + port);
        GameManager.queuePlayer(clientIP, playerName, result, port);  
        
        sendResponse(exchange, ("playerkey: " + result).getBytes(), 200);
    }

    private void sendResponse(HttpExchange exchange, byte[] responseBytes, int code) throws IOException
    {
        int responseLength = 0;
        if(responseBytes != null)
            responseLength = responseBytes.length;
        
        exchange.sendResponseHeaders(code, responseLength);

        OutputStream stream = exchange.getResponseBody();
        if(responseLength != 0)
            stream.write(responseBytes);
        
        stream.flush();
        stream.close();
    }

    private String readStream(InputStream stream)
    {
        String result = "";
        int i = 0;
        try
        {
            while( (i = stream.read() ) != -1)
            {
                result += (char) i;
            }

            stream.close();
        }
        catch(Exception e)
        {
            Utility.print("Uma exceção foi gerada ao ler o 'body' de uma requisição.");
            Utility.print(e.getMessage() );
        }

        return result;
    }
}
