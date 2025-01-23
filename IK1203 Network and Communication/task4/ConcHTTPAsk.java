import java.net.*;
import java.io.*;

public class ConcHTTPAsk 
{
    public static void main(String[] args) throws IOException {
        try
        {
            int p = Integer.parseInt(args[0]);
            ServerSocket s = new ServerSocket(p);
            while(true)
            {
                Socket cS = s.accept();
                MyRunnable r = new MyRunnable(cS);
                Thread t = new Thread(r);
                t.start();
            }

        } 
        catch (Exception e)
        {
            System.err.println(e);
        }
    }
}