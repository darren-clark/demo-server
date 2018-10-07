package demo.server;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class HttpConnectionHandler extends Thread {
    private Socket socket;
    private List<HttpRequestHandler> requestHandlers;
    private HttpRequestParser parser;
    private HttpResponseWriter writer;

    HttpConnectionHandler(Socket socket, List<HttpRequestHandler> requestHandlers, HttpRequestParser parser, HttpResponseWriter writer){
        this.socket = socket;
        this.requestHandlers = requestHandlers;
        this.parser = parser;
        this.writer = writer;
    }

    @Override
    public void run() {
        try {
            HttpServerRequest request = parser.parse(socket.getInputStream());

            HttpServerResponse response = new HttpServerResponse(404);
            for(var handler: requestHandlers){
                if (handler.HandleRequest(request, response))
                    break;
            }

            writer.write(socket.getOutputStream(), request, response);
        }catch(IOException e){

        }
    }
}
