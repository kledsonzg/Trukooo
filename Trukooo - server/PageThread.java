import java.net.InetSocketAddress;
import com.sun.net.httpserver.*;

import com.trukooo.server.PageHandler;
import com.trukooo.util.Utility;

public class PageThread extends Thread
{
    @Override
    public void run()
    {
        try{
            HttpServer pageServer = HttpServer.create(new InetSocketAddress("127.0.0.1", 40019), 0);
            pageServer.createContext("/", new PageHandler() );
            pageServer.start();      
        }
        catch(Exception e)
        {
            Utility.print("Uma exceção foi gerada no programa e ele será encerrado");
            Utility.print("Exceção: " + e.getMessage() );
        }
    }
}