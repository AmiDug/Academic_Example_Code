package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {

    boolean shut;
    Integer time;
    Integer lim;

    public TCPClient(boolean shutdown, Integer timeout, Integer limit) {
        shut = shutdown;
        time = timeout;
        lim = limit;
    }

    public byte[] askServer(String hostname, int port, byte [] toServerBytes) throws IOException, SocketTimeoutException{
        int bufferRead;
        Socket cSocket = new Socket(hostname, port);
        Integer max = 2147483647;
        if(this.lim == null){lim = max;}
        ByteArrayOutputStream dynArray  = new ByteArrayOutputStream();
        try{
        if(shut == true)
        {
           cSocket.shutdownOutput();
           return toServerBytes;
        }
        int fromUserLength = toServerBytes.length;
        cSocket.getOutputStream().write(toServerBytes, 0, fromUserLength);
        if(time != null){cSocket.setSoTimeout(time);}
        byte[] buff = new byte[1024];
        while(-1 != (bufferRead = cSocket.getInputStream().read(buff)))
        {
           if(lim < bufferRead)
           {
              dynArray.write(buff, 0, lim);
              byte res[] = dynArray.toByteArray();
              cSocket.close();
              return res;
           }
           dynArray.write(buff, 0, bufferRead);
        }
        byte res[] = dynArray.toByteArray();
        cSocket.close();
        return res;
        }
        catch(SocketTimeoutException e)
        {
          byte res[] = dynArray.toByteArray();
          cSocket.close();
          return res;
        }
    }
}
