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
        
        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) 
        {
            // 클라이언트가 연결될때까지 대기한다.
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
