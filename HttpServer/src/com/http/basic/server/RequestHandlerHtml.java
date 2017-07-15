package com.http.basic.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;

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
            InputStream inputStream = connection.getInputStream();
            OutputStream outputStream = connection.getOutputStream();
            
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = bufferedReader.readLine();
            
            String requestURL = getURL(line);
            String[] params = getParams(line);
            
            int contentLength = 0;
            
            line = bufferedReader.readLine();
            
            if (line.contains("Content-Length"))
            {
                contentLength = getContentLength(line);
            }
            
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            File readFile = new File("./webapp" + requestURL);
            
            byte[] body = Files.readAllBytes(readFile.toPath());
            
            response200Header(dataOutputStream, body.length);
            response200Body(dataOutputStream, body);
            
            for (int i = 0; i < params.length; i++)
            {
                String[] seperateParam = params[i].split("=");
                
                System.out.print("Parameter Name: " + seperateParam[0] + " / ");
                System.out.println("Parameter Value : " + seperateParam[1]);
            }
            
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public String getURL(String line)
    {
        String tokens[] = line.split(" ");
        String requestURL = tokens[1].split("\\?")[0];
        
        return requestURL;
    }
    
    public String[] getParams(String line)
    {
        String tokens[] = line.split(" ");
        String[] params = tokens[1].split("\\?")[1].split("&");
        
        return params;
        
    }
    
    public int getContentLength(String line)
    {
        String[] headerToken = line.split(":");
        System.out.println(headerToken[0] + headerToken[1]);
        return Integer.parseInt(headerToken[1].trim());
    }
    
    public void response200Header(DataOutputStream dataOutputStream, int lengthOfBodyContent) throws IOException
    {
        dataOutputStream.writeBytes("HTTP/1.1 200 OK \r\n");
        dataOutputStream.writeBytes("Connection: Keep-Alive\r\n");
        dataOutputStream.writeBytes("Content-Type: text/html; charset=utf-8\r\n");
        dataOutputStream.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
        dataOutputStream.writeBytes("Keep-Alive: timeout=5000, max=10000\r\n");
        dataOutputStream.writeBytes("\r\n");
    }
    
    public void response200Body(DataOutputStream dataOutputStream, byte[] body) throws IOException
    {
        dataOutputStream.write(body, 0, body.length);
        dataOutputStream.writeBytes("\r\n");
        dataOutputStream.flush();
    }
    
}
