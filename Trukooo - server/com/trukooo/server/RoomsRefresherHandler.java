package com.trukooo.server;

import java.io.FileReader;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import com.sun.net.httpserver.*;
import com.trukooo.game.GameManager;
import com.trukooo.game.Game;
import com.trukooo.util.Utility;

public class RoomsRefresherHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange)
    {
        try
        {
            String uriString = exchange.getRequestURI().toString();

            System.out.println("Refresher URI: " + uriString);

            if(uriString.equals("/frame") )
            {
                String readerTexString = getStringFromFile("/pages/rooms/refresher.html");
            
                byte[] bytesResult = readerTexString.getBytes();
                
                sendResponse(exchange, bytesResult, "text/html");
                return;
            }
            else if(uriString.indexOf("/scripts") == 0)
            {
                String readerTexString = getStringFromFile(uriString);

                byte[] bytesResult = readerTexString.getBytes();

                sendResponse(exchange, bytesResult, Files.probeContentType(Path.of(Utility.getFileByRequestURI(new URI(uriString) ) ) ) );
                return;
            }
            else if(uriString.equals("/rooms") == false || GameManager.isRunning() == false)
            {
                exchange.sendResponseHeaders(400, 0);
                OutputStream stream = exchange.getResponseBody();
                stream.flush();
                stream.close();

                return;
            }
           
            byte[] bytesResult= getResultXMLFormat().getBytes(Charset.forName("UTF-8") );    

            sendResponse(exchange, bytesResult, "text/xml");
        }
        catch(Exception e)
        {
            Utility.print("Uma exceção foi gerada no Thread que retorna os status das salas de jogos.");
            Utility.print("Exceção: " + e.getMessage() );
        }      
    }

    private static String getStringFromFile(String filePath)
    {
        String readerTexString = "";
        try
        {
            FileReader reader = new FileReader(Utility.getFileByRequestURI(new URI(filePath) ), Charset.forName("UTF-8") ); 
        
            int caractere = 0;

            while((caractere = reader.read() ) != -1)
                readerTexString += (char) caractere;
            reader.close();
        }
        catch(Exception e)
        {
            Utility.print("Uma exceção foi gerada no Thread que retorna os status das salas de jogos ao ler um arquivo.");
            Utility.print("Exceção: " + e.getMessage() );
        }
        
        return readerTexString;
    }

    private static String getResultXMLFormat()
    {
        String result = "<type>update</type>\n";
        result += "<games>\n\t";

        for(int i = 0; i < 10; i++)
        {
            Game game = GameManager.getRunningGame(i);
            int count = game.getPlayersCount();

            result += "<game id=\"" + i + "\" >" + count + "</game>";

            if(i != 9)
                result += "\n\t";
            else result += "\n";
        }

        result += "</games>";
        return result;
    }

    private static void sendResponse(HttpExchange exchange, byte[] bytesResult, String fileTypeString) throws Exception
    {
        exchange.getResponseHeaders().set("Content-Type", fileTypeString);
        exchange.sendResponseHeaders(200, bytesResult.length);

        OutputStream stream = exchange.getResponseBody();
        stream.write(bytesResult);
        stream.flush();
        stream.close();
    }
}
