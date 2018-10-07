package demo.server;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;

public class HttpServerResponseWriter implements  HttpResponseWriter {
    public void write(OutputStream stream, HttpServerRequest request, HttpServerResponse response) throws IOException {
        var writer = new OutputStreamWriter(stream, StandardCharsets.UTF_8);
        writeStatus(writer, request, response);
        writeCommonHeaders(writer, request, response);
        writeAdditionalHeaders(writer, request, response);
        writer.write("\r\n");
        writer.flush();

        if (response.getBody() != null)
            response.getBody().getStream().transferTo(stream);
    }

    private void writeStatus(OutputStreamWriter writer, HttpServerRequest request, HttpServerResponse response) throws IOException {
        writer.write(request.getHttpVersion()+" "+response.getStatusCode()+" "+HttpServerStatusMessages.getStatusMessage(response.getStatusCode())+"\r\n");
    }

    private void writeCommonHeaders(OutputStreamWriter writer, HttpServerRequest request, HttpServerResponse response) throws IOException {
        writeHeader(writer,"X-Server-Name", HttpServerConfig.SERVER_NAME);
        writeHeader(writer, "Date", LocalDate.now());
        writeHeader(writer, "Connection", "Close");
        if (response.getBody() != null) {
            writeHeader(writer,"Content-Type", response.getBody().getContentType());
            writeHeader(writer, "Content-Length", response.getBody().getContentLength());
        }else {
            writeHeader(writer, "Content-Length", 0);
        }
    }

    private void writeAdditionalHeaders(OutputStreamWriter writer, HttpServerRequest request, HttpServerResponse response) throws IOException {
        HashMap<String,String> headers = response.getAdditionalHeaders();
        if (headers != null){
            for(var header: headers.entrySet()){
                writeHeader(writer,header.getKey(), header.getValue());
            }
        }
    }

    private void writeHeader(OutputStreamWriter writer, String headerName, Object headerValue) throws IOException {
        writer.write(headerName+": "+headerValue+"\r\n");
    }
}
