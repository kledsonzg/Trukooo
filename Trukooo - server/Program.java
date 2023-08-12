import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.trukooo.util.Utility;

public class Program
{
    public static void main(String[] args)
    {
        // Criação do servidor HTTP - (Site)
        PageThread pageThread = new PageThread();
        pageThread.start();

        Utility.print("O programa iniciou!");
        readInput();

        pageThread.interrupt();
    }

    private static void readInput()
    {
        InputStreamReader reader = new InputStreamReader(System.in, StandardCharsets.UTF_8);

        while(true)
        {
            String text = "";         
            try
            {
                int caractere, lastCaractere = -1;
                
                caractere = reader.read();
                while( !(caractere == -1 || caractere == 10 && lastCaractere == 13) )
                {
                    if(caractere != 13)
                        text += (char) caractere;

                    lastCaractere = caractere;
                    caractere = reader.read();
                }
                if(text.toLowerCase().equals("exit") )
                    break;
                else
                {
                    System.out.println("input length: " + text.length() + " | exit length: " + ("exit").length() );
                    continue;
                }                    
            }
            catch(Exception e)
            {
                Utility.print("Exceção gerada: " + e.getMessage() );
            }
            
        }
    }
}