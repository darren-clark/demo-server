package demo.server;

public interface HttpRequestHandler {
    boolean HandleRequest(HttpServerRequest httpServerRequest, HttpServerResponse httpServerResponse);
}
