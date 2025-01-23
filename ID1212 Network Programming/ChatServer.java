package com.mycompany.task_1;

import java.net.*;
import java.io.*;
import java.util.*;

public class ChatServer{
    
    public static void main() throws IOException {
        ServerSocket ss = new ServerSocket (8080);
        Listener listener = new Listener(ss);
        Thread t = new Thread(listener);
      
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
          @Override  
          public void run()
          {  t.start();
            System.out.println("Server shutting down");
          }
        });
        // BufferedReader br;
    }
}





class Listener implements Runnable{
    ServerSocket ss;
    Socket s;
    static Vector<ClientHandler> client_list = new Vector<>();
    public Listener(ServerSocket ss) throws IOException{
        this.ss = ss;
    }
    @Override
    public void run(){
        while(true){
            try{
                this.s = this.ss.accept();
                System.out.println("Host: " + s.getLocalAddress());
                System.out.println("Client Address: " + s.getInetAddress());
                System.out.println("Host port: " + s.getLocalPort());
                System.out.println("Client port: " + s.getPort());
                //System.out.println(s.getLocalSocketAddress());
                //System.out.println(s.getRemoteSocketAddress());
                BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintStream output = new PrintStream(s.getOutputStream());
                System.out.println("Handling request...");
                ClientHandler client = new ClientHandler(s, input, output);
                Thread t = new Thread(client);
                client_list.add(client);
                t.start();
            }
            catch(UnknownHostException e){
                System.out.println(e.getMessage());
            }
            catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
class ClientHandler implements Runnable{
    Socket s;
    BufferedReader input;
    PrintStream output;
    String message;
    boolean active;
    public ClientHandler(Socket s, BufferedReader input, PrintStream output){
        this.s = s;
        this.input = input;
        this.output = output;
        this.active = true;
    }
    @Override
    public void run(){
            try
            {
                while((message = input.readLine()) != null && !(s.isClosed())) 
                {
                    //System.out.println(received);
                    if(message.equals("Logout")){
                        this.active = false;
                        break;
                    }

                    for(ClientHandler ch: Listener.client_list){
                        if(ch.active == true && this.s.getPort() != ch.s.getPort()){
                            ch.output.println(message);
                        }
                    }
                } 
            }
            
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        
        try{
            // closing resources
            this.s.close();
            this.input.close();
            this.output.close();
			
	}
        catch(IOException e){
            System.out.println(e.getMessage());
	}
    }
}