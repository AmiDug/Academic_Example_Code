package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {
    
    public TCPClient() {
    }

    public byte[] askServer(String hostname, int port, byte [] toServerBytes) throws IOException {
        int bufferRead;
        Socket cSocket = new Socket(hostname, port);
        int fromUserLength = toServerBytes.length;
        cSocket.getOutputStream().write(toServerBytes, 0, fromUserLength);
        ByteArrayOutputStream dynArray = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        while(-1 != (bufferRead = cSocket.getInputStream().read(buff)))
        {
          dynArray.write(buff, 0, bufferRead);
        }
        byte res[] = dynArray.toByteArray();
        cSocket.close();
        return res;
    }
    public byte[] askServer(String hostname, int port) throws IOException {
        int bufferRead;
        Socket cSocket = new Socket(hostname, port);
        ByteArrayOutputStream dynArray = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        while(-1 != (bufferRead = cSocket.getInputStream().read(buff)))
        {
          dynArray.write(buff, 0, bufferRead);
        }
        byte res[] = dynArray.toByteArray();
        cSocket.close();
        return res;
    }

}
