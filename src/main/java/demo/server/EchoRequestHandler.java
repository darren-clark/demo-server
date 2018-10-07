package demo.server;

public class EchoRequestHandler implements HttpRequestHandler {

    @Override
    public boolean HandleRequest(HttpServerRequest request, HttpServerResponse response) {
        if (!request.getPath().equals("echo"))
            return false;

        if(request.getParameter("status") != null) {
            response.setStatusCode(Integer.parseInt((request.getParameter("status"))));
        } else{
            response.setStatusCode(200);
        }

        if (request.getParameter("body") != null) {
            response.setBody(new StringResponseBody(request.getParameter("body")));
        }

        if (request.getParameter("location") != null) {
            response.getAdditionalHeaders().put("Location", request.getParameter("location"));
        }

        return true;
    }
}
