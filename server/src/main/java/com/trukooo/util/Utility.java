package com.trukooo.util;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import com.trukooo.spring.BCrypt;

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

    public static String readFile(String file)
    {
        String result = "";
        try
        {
            FileReader reader = new FileReader(file, StandardCharsets.UTF_8);
            int i = -1;
            while( (i = reader.read() ) != -1)
            {
                result += (char) i;
            }
            reader.close();
        }
        catch(Exception e)
        {
            print("Falha ao ler um arquivo. (Utility.readFile)");
            print(e.getMessage() );
        }

        return result;
    }

    public static String hashString(String textToHash)
    {
        print("Generating hash...");
        String result = BCrypt.hashpw(textToHash, BCrypt.gensalt() );
        print("result: " + result);
        return result;
    }

    public static boolean validateHash(String hash, String key)
    {
        return BCrypt.checkpw(key, hash);
    }
}
