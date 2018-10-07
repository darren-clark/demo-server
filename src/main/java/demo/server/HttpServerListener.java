package demo.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class HttpServerListener {
    private boolean running;
    private List<HttpRequestHandler> requestHandlers;
    private HttpRequestParser requestParser;
    private HttpResponseWriter responseWriter;

    public HttpServerListener(List<HttpRequestHandler> requestHandlers, HttpRequestParser requestParser, HttpResponseWriter responseWriter){
        this.requestHandlers = requestHandlers;
        this.requestParser  = requestParser;
        this.responseWriter = responseWriter;
    }

    public void start(int port){
        int actualPort;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            actualPort = serverSocket.getLocalPort();
        }
        catch(IOException e){
            System.err.println("Error starting listener");
            e.printStackTrace();
            return;
        }
        running = true;
        System.out.printf("Starting server on port %d", actualPort);
        while(running) {
            try {
                Socket clientSocket = serverSocket.accept();
                new HttpConnectionHandler(clientSocket, requestHandlers, requestParser, responseWriter).start();
            } catch (IOException e) {
                System.err.println("Error starting connection handler");
                e.printStackTrace();
            }
        }
    }

    public void stop(){
        System.out.println("Stopping server");
        running = false;
    }
}
