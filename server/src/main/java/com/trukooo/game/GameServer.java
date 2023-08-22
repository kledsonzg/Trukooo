package com.trukooo.game;

import java.io.OutputStream;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.trukooo.util.Utility;

import java.util.List;

public class GameServer implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange)
    {
        String url = exchange.getRequestURI().toString();
        try
        {
            if(url.length() > 1)
            {
                exchange.sendResponseHeaders(400, 0);

                OutputStream stream = exchange.getResponseBody();
                stream.flush();
                stream.close();
                return;
            }

            List<String> cookies = exchange.getRequestHeaders().get("cookie");
            if(cookies == null)
                throw new Exception("Foi obtido 'null' ao tentar obter os cookies.");

            for (String value : cookies) {
                Utility.print("cookie: " + value);
            }
            
            exchange.sendResponseHeaders(200, 0);

            OutputStream stream = exchange.getResponseBody();
            stream.flush();
            stream.close();
        }       
        catch(Exception e)
        {
            System.out.println("Uma exceção foi gerada no handler na sala de jogo: " + exchange.getRequestURI().getRawQuery() );
            System.out.println(e.getMessage() );
        }
    }
}
