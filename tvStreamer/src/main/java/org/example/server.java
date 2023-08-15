package org.example;

import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.SimpleFileServer;

import java.io.File;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;

public class server {
    public static HttpServer server;
    public static void startServer(String username, String password) throws UnknownHostException {
        if (server != null){
            window.textArea1.append("\n\nStopping server...");
            server.stop(0);
            window.textArea1.append("\nServer stopped");
        }
        if(username != null && password != null && username.length()!=0 && password.length()!=0){
            window.textArea1.append("\n\nStarting server...");
            var addr = new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), 8080);
            server = SimpleFileServer.createFileServer(
                    addr,
                    Path.of(new File(".").getAbsolutePath()
                            +"/tvStreamer/src/main/java/org/example/stream/"),
                    SimpleFileServer.OutputLevel.NONE); //VERBOSE or INFO or NONE
            server.createContext("/home", new MyHandler()).
                    setAuthenticator(new BasicAuthenticator("test") {
                        @Override
                        public boolean checkCredentials(String user, String pwd) {
                            return user.equals(username)
                                    && pwd.equals(password);
                        }
                    });
            server.setExecutor(null);
            server.start();
            window.textArea1.append("\nServer started at address: " + addr);
            window.textArea1.append("\nUsername: " + username);
            window.textArea1.append("\nPassword: " + password);
            window.textArea1.append("\nServer root folder: "+Path.of(new File(".").getAbsolutePath()
                    +"/tvStreamer/src/main/java/org/example/stream/"));
        }
        else{
            window.textArea1.append("\n\nServer cannot be started: Username or password have not been inserted!");
        }

    }
}
