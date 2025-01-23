package com.mycompany.task_1;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient{
    public static void main(String args[]) throws IOException, UnknownHostException
    {

        Scanner scan = new Scanner(System.in);
        InetAddress ip = InetAddress.getByName("localhost");
        Socket s = new Socket(ip, 8080);
        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintStream output = new PrintStream(s.getOutputStream());
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run(){
                System.out.println("Client shutting down");
                try{
                    output.println("Logout");
                    input.close();
                    output.close();
                    s.close();
                }
                catch (IOException e) 
                 {
                        System.out.println(e.getMessage());
                 }
            }
        });
        
        Thread send = new Thread(new Runnable() 
        {
            @Override
            public void run() 
            {
                while (true) 
                {
                    String msg = scan.nextLine();
                    try 
                    {
                        output.println(msg);
                        if(msg.equals("Logout"))
                        {
                            System.out.println("Logging client out...");
                            input.close();
                            output.close();
                            s.close();
                            break;
                        }
                    } 
                    catch (IOException e) 
                    {
                        System.out.println(e.getMessage());
                    }
                }
            }
        });

        Thread read = new Thread(new Runnable() 
        {
            @Override
            public void run() 
            {
                 try 
                {
                    String msg;
                    while ((msg = input.readLine()) != null && !(s.isClosed())) 
                    {
                        System.out.println(msg);
                    } 
                }
                    catch (IOException e) 
                    {
                        System.out.println("Server crashed");
                        System.out.println(e.getMessage());
                    }
            }
        });
        send.start();
        read.start();

    }
}