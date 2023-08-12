package com.trukooo.server;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.sun.net.httpserver.*;
import com.trukooo.util.Utility;

public class PageHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        String responseText = "";
        String fileRequested = getFileByRequestURI(exchange.getRequestURI() );

        Utility.print("File: " + fileRequested);

        FileReader reader = new FileReader(fileRequested, StandardCharsets.UTF_8 );
        
        int readerIndex = -1;
        while((readerIndex = reader.read() ) != -1)
        {
            responseText += (char) readerIndex;
        }
        reader.close();

        byte[] responseBytes = responseText.getBytes(StandardCharsets.UTF_8);
        if(getFileExtension(fileRequested).equals(".png") )
            responseBytes = responseText.getBytes(StandardCharsets.US_ASCII);
        
        String type = Files.probeContentType(Path.of(fileRequested) );
        exchange.getResponseHeaders().set("Content-Type",  type);

        exchange.sendResponseHeaders(200, responseBytes.length );
        OutputStream outStream = exchange.getResponseBody();
        outStream.write(responseBytes);
        outStream.flush();
        outStream.close();
    }

    private String getFileByRequestURI(URI requestURI)
    {
        String uri = requestURI.toString();
        String currentFilePath = "w:/html/Trukooo/src";

        if(uri.equals("/") || uri.isEmpty() )
            currentFilePath += "/index.html";
        else currentFilePath += uri;

        return currentFilePath;
    }

    private String getFileExtension(String filePath)
    {
        int index = filePath.lastIndexOf('.', filePath.length() - 1);
        String extension = "";

        if(index != -1)
        {
            for(int i = index; i < filePath.length(); i++)
            {
                extension += filePath.charAt(i);
            }
        }
        else return extension;
        
        return extension.toLowerCase();
    }
}
