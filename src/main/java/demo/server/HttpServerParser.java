package demo.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class HttpServerParser implements  HttpRequestParser{

    public HttpServerRequest request;
    public InputStream input;

    public HttpServerRequest parse(InputStream input) throws IOException {
        request = new HttpServerRequest();
        this.input = input;
        parseRequestLine();
        parseHeaderLines();
        request.setBody(input);
        return request;
    }

    public void parseRequestLine() throws IOException{
        try {
            String[] s = HttpUtils.readNextLine(input).split("[ ]");
            request.setHttpMethod(s[0]);
            if(!s[1].isEmpty()) {
                request.setURL(s[1].substring(1));
            } else {
                request.setURL("");
            }
            request.setHttpVersion(s[2]);
            parseParameters(parsePath());
        } catch(IndexOutOfBoundsException e){
            System.out.println("Index out of bounds on parseRequestLine in HttpServerParser.");
        } catch(NullPointerException e){
            System.out.println("Null pointer exception on parseRequestLine in HttpServerParser.");
        }
    }

    public String parsePath() {
        String uri[] = request.getURL().split("[?]");
        request.setPath(uri[0]);
        if(uri.length > 1) {
            return uri[1];
        } else{
            return "";
        }
    }

    public void parseParameters(String paramString) {
        if(!paramString.isEmpty()) {
            String queryParams[] = paramString.split("&");
            for (String param : queryParams) {
                int delimiterPos = param.indexOf("=");
                try {
                    request.setParameter(param.substring(0, delimiterPos), URLDecoder.decode(param.substring(delimiterPos + 1), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    System.out.println("Error parsing parameters");
                }
            }
        }
    }

    public void parseHeaderLines() throws IOException {
        String line = HttpUtils.readNextLine(input);
        while(!line.isEmpty()) {
            int colonPos = line.indexOf(":");
            request.setHeader(line.substring(0, colonPos), line.substring(colonPos+1));
            line = HttpUtils.readNextLine(input);
        }
    }
}
