package demo.server;

import java.io.IOException;
import java.io.InputStream;

public interface HttpResponseBody {

    String getContentType();
    InputStream getStream() throws IOException;
    long getContentLength();
}

