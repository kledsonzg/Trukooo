import java.net.InetSocketAddress;

import com.sun.net.httpserver.*;
import com.trukooo.server.RoomsRefresherHandler;
import com.trukooo.util.Utility;

public class RoomsRefresherThread extends Thread
{
    @Override
    public void run()
    {
        try{           
            HttpServer refresherServer = HttpServer.create(new InetSocketAddress("127.0.0.1", 40020), 0);
            refresherServer.createContext("/rooms", new RoomsRefresherHandler() );
            refresherServer.createContext("/frame", new RoomsRefresherHandler() );
            refresherServer.createContext("/scripts", new RoomsRefresherHandler() );
            refresherServer.start();    
        }
        catch(Exception e)
        {
            Utility.print("Uma exceção foi gerada no programa e ele será encerrado");
            Utility.print("Exceção: " + e.getMessage() );
        }
    }
}
