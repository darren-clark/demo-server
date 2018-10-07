package demo.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class StaticFileRequestHandler implements HttpRequestHandler {

    @Override
    public boolean HandleRequest(HttpServerRequest request, HttpServerResponse response) {

        String resourceName;
        if(request.getPath().isEmpty()){
            resourceName = HttpServerConfig.DEFAULT_FILE;
        } else{
            resourceName = request.getPath();
        }

        File file = new File(resourceName);
        if (!file.exists())
            return false;

        if (!request.getHttpMethod().equalsIgnoreCase("GET")){
            response.setStatusCode(405);
        }

        //TODO: Set Mime type
        response.setStatusCode(200);
        response.setBody(new FileResponseBody(file));
        return true;
    }
}
