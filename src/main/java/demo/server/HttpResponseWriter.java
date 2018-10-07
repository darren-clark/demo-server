package demo.server;

import java.io.IOException;
import java.io.OutputStream;

public interface HttpResponseWriter {
    void write(OutputStream stream, HttpServerResponse response) throws IOException;
}
