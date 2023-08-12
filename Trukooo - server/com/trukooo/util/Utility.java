package com.trukooo.util;

public class Utility 
{
    public static void print(String text)
    {
        System.out.println(text);
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
}
