package com.trukooo.util;

import java.io.IOException;
import java.net.URI;
import org.springframework.security.crypto.bcrypt.*;

public class Utility 
{
    public static void print(String text)
    {
        System.out.println(text);
    }

    public static String getLine() throws IOException
    {
        String response = "";
        int caractere = 0;
        while( (caractere = System.in.read() ) != 10 )
        {
            response += (char) caractere;
        }

        response = response.substring(0, response.length() - 1);
        return response;
    }

    public static boolean isValidIP(String ip)
    {
        for(int i = 0; i < ip.length(); i++)
        {
            boolean isNumber = false;
            char caractere = ip.charAt(i);
            
            if(caractere == '.')
                continue;
            
            for(int j = 48; j <= 57; j++)
            {
                if(caractere != (char) j)
                    continue;
                isNumber = true;
                break;
            }

            if(!isNumber)
                return false;
        }

        return true;
    }

    public static String getFileByRequestURI(URI requestURI)
    {
        String uri = requestURI.toString();
        String currentFilePath = "w:/html/Trukooo/web";

        if(uri.equals("/") || uri.isEmpty() )
            currentFilePath += "/index.html";
        else currentFilePath += uri;

        return currentFilePath;
    }

    public static String getFileExtension(String filePath)
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

    public static String hashString(String textToHash)
    {
        return BCrypt.hashpw(textToHash, BCrypt.gensalt() );
    }

    public static boolean validateHash(String hash, String key)
    {
        return BCrypt.checkpw(key, hash);
    }
}
