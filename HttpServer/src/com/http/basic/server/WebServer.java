package com.http.basic.server;

import java.net.ServerSocket;
import java.net.Socket;


public class WebServer {
    private static final int DEFAULT_PORT = 8080;

    public static void main(String args[]) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }
        String name = "A=B=C";
        name.split("=");
        
        // ���������� �����Ѵ�. �������� �⺻������ 8080�� ��Ʈ�� ����Ѵ�.
        try (ServerSocket listenSocket = new ServerSocket(port)) 
        {
            // Ŭ���̾�Ʈ�� ����ɶ����� ����Ѵ�.
            Socket connection;
            
            while ((connection = listenSocket.accept()) != null) {
//                RequestHandler requestHandler = new RequestHandler(connection);
//                requestHandler.start();
                RequestHandlerHtml tt = new RequestHandlerHtml(connection);
                tt.start();
            }
        }
    }
}
