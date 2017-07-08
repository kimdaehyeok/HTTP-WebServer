package com.http.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer
{
    private static final int DEFAULT_PORT = 8080;    
    
    public static void main(String args[])
    {
        int port = 0;
        
        if(args == null || args.length == 0)
        {
            port = DEFAULT_PORT;
        }
        else
        {
            port = Integer.parseInt(args[0]);
        }
        
        //���� ������ ������. �� ������ �⺻������ 8080�� ���
        try
        {
            ServerSocket listenSocket = new ServerSocket(port);

            Socket connection;
            
            //Ŭ���̾�Ʈ�� ������ �� ������ ����Ѵ�.
            while( (connection = listenSocket.accept()) != null)
            {
                System.out.println("Remote IP Address : " + connection.getInetAddress());
                System.out.println("Remote Port " + connection.getPort());
                
                //input, output ��Ʈ�� ����
                InputStream inputStream = connection.getInputStream();
                OutputStream outputStream = connection.getOutputStream();
                
                //������ DataOutputStream ����
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                byte[] body = "Hello World!!".getBytes();
                
                //Header �κ� �ۼ�
                dataOutputStream.writeBytes("HTTP/1.1 200 OK \r\n");
                dataOutputStream.writeBytes("Content-Type : text/html;charset=utf-8\r\n");
                dataOutputStream.writeBytes("Content-Length : " + body.length + "\r\n");
                dataOutputStream.writeBytes("\r\n");
                
                //Body �κ� �ۼ�
                dataOutputStream.write(body,0,body.length);
                dataOutputStream.writeBytes("\r\n");
                dataOutputStream.flush(); //���ۿ� �ִ� ������ ó���϶�� �Լ�
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
