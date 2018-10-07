package demo.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileResponseBody implements HttpResponseBody  {
    private File file;
    private String contentType;

    public FileResponseBody(File file, String contentType){
        this.file = file;
        this.contentType = contentType;
    }

    public FileResponseBody(File file){
        this(file, "text/plain");
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public InputStream getStream() throws FileNotFoundException {
        return new FileInputStream(file);
    }

    @Override
    public long getContentLength() {
        return file.length();
    }
}
