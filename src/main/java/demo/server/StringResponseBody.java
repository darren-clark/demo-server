package demo.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringResponseBody implements  HttpResponseBody {

    private byte[] bytes;
    private String contentType;
    private Charset encoding;

    public StringResponseBody(String response, String contentType, Charset encoding){
        this.bytes = response.getBytes(encoding);
        this.contentType = contentType;
        this.encoding = encoding;
    }

    public StringResponseBody(String response){
        this(response, "text/plain",StandardCharsets.UTF_8);
    }

    @Override
    public String getContentType() {
        return contentType+"; charset="+encoding;
    }

    @Override
    public InputStream getStream() throws IOException {
        return new ByteArrayInputStream(bytes);
    }

    @Override
    public long getContentLength() {
        return bytes.length;
    }
}
