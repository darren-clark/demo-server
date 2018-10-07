package demo.server;

import java.io.IOException;
import java.io.InputStream;

public interface HttpRequestParser {
    HttpServerRequest parse(InputStream input) throws IOException;
}
