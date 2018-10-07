package demo.server;

import java.io.IOException;
import java.util.Arrays;

public class Main {


    public static void main(String[] args) throws IOException {
        new HttpServerConfig();
        int port = 0; // Could get from args if not lazy.
        var listener = new HttpServerListener(
                Arrays.asList(new StaticFileRequestHandler()),
                new HttpServerParser(),
                new HttpServerResponseWriter()
        );
        listener.start(port);
        System.in.read();
        listener.stop();
        // Or this if we want to do more on main.
        //new Thread(() -> listener.start(port));
    }
}
