package demo.server;

import java.io.*;
import java.nio.file.Files;

public class FileResponseBody implements HttpResponseBody  {
    private File file;
    private String contentType;

    public FileResponseBody(File file, String contentType){
        this.file = file;
        this.contentType = contentType;
    }

    public FileResponseBody(File file) throws IOException {
        this(file, Files.probeContentType(file.toPath()));
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
