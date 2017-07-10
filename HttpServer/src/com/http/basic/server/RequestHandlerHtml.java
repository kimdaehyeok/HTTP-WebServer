package com.http.basic.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class RequestHandlerHtml extends Thread
{
    private Socket connection;
    
    public RequestHandlerHtml(Socket connection)
    {
        this.connection = connection;
    }
    
    public void run() 
    {
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String[] tokens = bufferedReader.readLine().split(" ");
            
            while(!bufferedReader.readLine().equals(""))
            {
                System.out.println(bufferedReader.readLine());
            }
            
            System.out.println("The URL is " + tokens[1]);
            
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
