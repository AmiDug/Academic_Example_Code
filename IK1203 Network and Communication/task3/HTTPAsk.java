import java.net.*;
import java.io.*;
import tcpclient.TCPClient;

public class HTTPAsk {
    public static void main( String[] args) throws IOException 
    {
       String H200 = "HTTP/1.1 200 OK\r\n\r\n";
       String H404 = "HTTP/1.1 404 Not Found\r\n";
       String H400 = "HTTP/1.1 400 Bad Request\r\n";
       ServerSocket sS = new ServerSocket(Integer.parseInt(args[0]));
       while(true)
       {
          Socket cS = sS.accept();
          byte[] buff = new byte[1024];
          ByteArrayOutputStream array = new ByteArrayOutputStream();
          int length = cS.getInputStream().read(buff);
          while(true)
          {
             array.write(buff, 0, length);
             if(new String(buff).contains("\r\n")){break;}
          }
          String sOut = new String(array.toByteArray());
          String host = null;
          String toSer = "";
          Integer lim = null;
          Integer port = 0;
          Integer time = 0;
          boolean shut = false;
          boolean http = false;
          if(!sOut.contains("/favicon.ico")) {
                String[] info = sOut.split("[?&= ]");
                for (int i = 0; i < info.length; i++) {
                    if (info[i].equals("hostname")) {
                        host = info[++i];
                    } if (info[i].equals("port")) {
                        port = Integer.parseInt(info[++i]);
                    } if (info[i].equals("string")) {
                        toSer = info[++i];
                    } if (info[i].equals("limit")) {
                        lim = Integer.parseInt(info[++i]);
                    } if (info[i].equals("timeout")) {
                        time = Integer.parseInt(info[++i]);
                    } if (info[i].equals("shutdown")) {
                        shut = (info[++i]).equals("true");
                    } if (info[i].contains("HTTP/1.1")) {
                        http = true;
                    }
                }
           if(info[0].equals("GET") &&  http)
           {
           if(info[1].equals("/ask") && host != null && port != 0)
           {
            try 
            {
                TCPClient tC = new tcpclient.TCPClient(shut, time, lim);
                byte[] bytes = toSer.getBytes();
                byte[] sBytes  = tC.askServer(host, port, bytes);
                String show = new String(sBytes);
                cS.getOutputStream().write(H200.getBytes());         
cS.getOutputStream().write(show.getBytes());

            } catch(Exception ex) 
            {
           cS.getOutputStream().write(H404.getBytes());
            }
            }
            else
            {
           cS.getOutputStream().write(H404.getBytes());
            }
            }
            else
            {
           cS.getOutputStream().write(H400.getBytes());
            }
            cS.getOutputStream().flush();
            cS.close();
       }
      }
    }
}

