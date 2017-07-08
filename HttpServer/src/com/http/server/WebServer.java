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
        
        //서버 소켓을 생성함. 웹 서버는 기본적으로 8080을 사용
        try
        {
            ServerSocket listenSocket = new ServerSocket(port);

            Socket connection;
            
            //클라이언트가 연결이 될 때까지 대기한다.
            while( (connection = listenSocket.accept()) != null)
            {
                System.out.println("Remote IP Address : " + connection.getInetAddress());
                System.out.println("Remote Port " + connection.getPort());
                
                //input, output 스트림 생성
                InputStream inputStream = connection.getInputStream();
                OutputStream outputStream = connection.getOutputStream();
                
                //전달할 DataOutputStream 생성
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                byte[] body = "Hello World!!".getBytes();
                
                //Header 부분 작성
                dataOutputStream.writeBytes("HTTP/1.1 200 OK \r\n");
                dataOutputStream.writeBytes("Content-Type : text/html;charset=utf-8\r\n");
                dataOutputStream.writeBytes("Content-Length : " + body.length + "\r\n");
                dataOutputStream.writeBytes("\r\n");
                
                //Body 부분 작성
                dataOutputStream.write(body,0,body.length);
                dataOutputStream.writeBytes("\r\n");
                dataOutputStream.flush(); //버퍼에 있는 내용을 처리하라는 함수
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
