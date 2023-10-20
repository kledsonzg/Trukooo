import com.trukooo.game.GameManager;
import com.trukooo.spring.BCrypt;
import com.trukooo.util.Utility;

public class Program
{
    public static void main(String[] args) throws Exception
    {
        // Iniciando as instâncias das Salas de Jogos.
        GameManager.start();
        
        // Criação do servidor HTTP - (Site)
        PageThread pageThread = new PageThread();
        pageThread.start();
        Utility.print("Aplicacao WEB iniciada!");

        // Criação do servidor HTTP - (Informante de Status das Salas)
        RoomsRefresherThread refresherThread = new RoomsRefresherThread();
        refresherThread.start();
        Utility.print("Servidor de status iniciado!");

        readInput();

        GameManager.stop();
        pageThread.interrupt();
        refresherThread.interrupt();
    }

    private static void readInput()
    {
        while(true)
        {
            String text = "";         
            try
            {
                text = Utility.getLine();                    
            }
            catch(Exception e)
            {
                Utility.print("Exceção gerada: " + e.getMessage() );
            }
            
            if(text.equals("exit") )
                break;
        }
    }
}