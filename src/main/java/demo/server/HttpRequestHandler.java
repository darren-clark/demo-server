package demo.server;

import java.io.IOException;

public interface HttpRequestHandler {
    boolean HandleRequest(HttpServerRequest httpServerRequest, HttpServerResponse httpServerResponse) throws IOException;
}
