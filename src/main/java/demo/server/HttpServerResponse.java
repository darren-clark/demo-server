package demo.server;

import java.util.HashMap;

public class HttpServerResponse {

    private int statusCode;
    private HashMap<String, String> additionalHeaders;
    private HttpResponseBody body;

    public HttpServerResponse(int statusCode)
    {
        this.statusCode = statusCode;
    }

    public int getStatusCode(){return statusCode;}
    public void setStatusCode(int statusCode) { this.statusCode = statusCode;}

    public HashMap<String,String> getAdditionalHeaders(){
        if (additionalHeaders == null)
            additionalHeaders = new HashMap<String,String>();

        return  additionalHeaders;
    }

    public void setAdditionalHeaders(HashMap<String,String> additionalHeaders){ this.additionalHeaders = additionalHeaders;}

    public HttpResponseBody getBody() {  return body; }
    public void setBody(HttpResponseBody body) { this.body = body;}
}
