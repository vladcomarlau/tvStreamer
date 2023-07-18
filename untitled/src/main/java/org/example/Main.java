package org.example;
import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.SimpleFileServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        var addr = new InetSocketAddress("192.168.0.148", 8080);
        var server = SimpleFileServer.createFileServer(
                addr,
                Path.of(new File(".").getAbsolutePath()+"/untitled/src/main/java/org/example/stream/"),
                SimpleFileServer.OutputLevel.VERBOSE);

        server.createContext("/login", new MyHandler()).
                setAuthenticator(new BasicAuthenticator("test") {
            @Override
            public boolean checkCredentials(String user, String pwd) {
                return user.equals("vlad") && pwd.equals("4444%Streamer");
            }
        });
        server.setExecutor(null);
        server.start();

        var ffmpeg = new ffmpegExecuter();
    }
}
class MyHandler implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        StringBuilder htmlFile = new StringBuilder();
        String FileContent;
        var file = new File("C:\\Users\\Anonymous\\Downloads\\test\\untitled\\src\\main\\resources\\home.html");
        BufferedReader input = new BufferedReader(new FileReader(file));

        while((FileContent = input.readLine())!=null){
            htmlFile.append(FileContent);
        }

        input.close();
        System.out.println(htmlFile);
        String response = String.valueOf(htmlFile);
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}