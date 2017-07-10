package com.http.basic.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RequestHandler extends Thread
{
    private Socket connection;
    
    public RequestHandler(Socket connection)
    {
        this.connection = connection;
    }
    
    public void run()
    {
        System.out.println("Remote IP : " + connection.getInetAddress());
        System.out.println("Remote Port : " + connection.getPort());
        
        try
        {
            InputStream inputStream = connection.getInputStream();
            OutputStream outputStream = connection.getOutputStream();
            
            DataOutputStream dateOutputStream = new DataOutputStream(outputStream);
            
            byte[] body = "hello world~~".getBytes();
            
            // Header 
            dateOutputStream.writeBytes("HTTP/1.1 200 OK \r\n");
            dateOutputStream.writeBytes("Content-Type:text/html;charset=utf-8\r\n");
            dateOutputStream.writeBytes("Content-Length:" + body.length + "\r\n");
            dateOutputStream.writeBytes("\r\n");
            
            //Body
            dateOutputStream.write(body, 0, body.length);
            dateOutputStream.flush();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}   
