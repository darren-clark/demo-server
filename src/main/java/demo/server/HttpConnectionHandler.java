package demo.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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
        HttpServerRequest request = null;
        HttpServerResponse response;
        try {
            request = parser.parse(socket.getInputStream());
            response = new HttpServerResponse(404, request.getHttpVersion());
            for (HttpRequestHandler handler: requestHandlers) {
                if (handler.HandleRequest(request, response))
                    break;
            }
        }
        catch(IOException e) {
            StringWriter stack = new StringWriter();
            PrintWriter writer = new PrintWriter(stack);
            e.printStackTrace(writer);
            String httpVersion = request != null ? request.getHttpVersion() : "HTTP/1.1";
            response = new HttpServerResponse(500, httpVersion);
            response.setBody(new StringResponseBody(stack.toString()));
        }
        try{
            writer.write(socket.getOutputStream(), response);
        }catch(IOException e){
            System.err.println("Error writing response");
            e.printStackTrace();
        }
    }
}
