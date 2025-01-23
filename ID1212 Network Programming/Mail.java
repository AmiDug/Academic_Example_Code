import java.io.*;
import java.util.Base64;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.net.Socket;

class MailingApp {
  public static void main(String[] args) {
    writeMail();
    readMail();
  }

  private static void writeMail(){
    try {
      Socket socket = new Socket("smtp.kth.se", 587);
      PrintWriter writer = new PrintWriter(socket.getOutputStream());
      BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      writer.println("STARTTLS");
      writer.flush();
      String line = reader.readLine();
      while (!(line.contains("220 2.0.0 Ready to start TLS"))) {
        line = reader.readLine();
      }
      SSLSocketFactory factory = (SSLSocketFactory)SSLSocketFactory.getDefault();
      SSLSocket sslsoc = (SSLSocket)factory.createSocket(socket, socket.getInetAddress().getHostAddress(), socket.getPort(), true);
      writer = new PrintWriter(sslsoc.getOutputStream(), true);
      BufferedReader secReader = new BufferedReader(new InputStreamReader(sslsoc.getInputStream()));
      writer.println("EHLO " + "smtp.kth.se");
      Thread.sleep(1000);
      writer.println("AUTH LOGIN");
      System.out.println(secReader.readLine());
      writer.println(Base64.getEncoder().encodeToString("amiran".getBytes()));
      System.out.println(secReader.readLine());
      writer.println(Base64.getEncoder().encodeToString("Korven123".getBytes()));
      System.out.println(secReader.readLine());
      writer.println("MAIL FROM:<amiran@kth.se>");
      System.out.println(secReader.readLine());
      Thread.sleep(1000);
      writer.println("RCPT TO:<amiran@kth.se>");
      System.out.println(secReader.readLine());
      Thread.sleep(1000);
      writer.println("DATA");
      System.out.println(secReader.readLine());
      Thread.sleep(1000);
      writer.println("Meddelandet till email");
      Thread.sleep(1000);
      writer.println(".");
      line = secReader.readLine();
      while (!(line.contains("250 2.0.0 Ok: queued as"))) {
        line = secReader.readLine();
      }
    } 
    catch(Exception e) 
    {
      System.out.println(e);
    }
  }
  
  private static void readMail() {
    try{  
        SSLSocketFactory factory = (SSLSocketFactory)SSLSocketFactory.getDefault();
        HttpsURLConnection.setDefaultSSLSocketFactory(factory);
        SSLSocket sslsoc = (SSLSocket)factory.createSocket("webmail.kth.se", 993);
        PrintWriter writer = new PrintWriter(sslsoc.getOutputStream(), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(sslsoc.getInputStream()));
        writer.println("a001 login " + "amiran" + " " + "Korven123");
        writer.println("a002 select inbox");
        writer.println("a003 fetch 1 (FLAGS BODY[HEADER.FIELDS (DATE FROM TO)])");
        writer.println("a004 logout");
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
  }
}