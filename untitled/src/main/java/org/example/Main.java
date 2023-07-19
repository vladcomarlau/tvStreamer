package org.example;
import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.SimpleFileServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        var credentials = new credentials();
        var addr = new InetSocketAddress("192.168.0.148", 8080);
        var server = SimpleFileServer.createFileServer(
                addr,
                Path.of(new File(".").getAbsolutePath()
                        +"/untitled/src/main/java/org/example/stream/"),
                SimpleFileServer.OutputLevel.NONE); //verbose or info

        server.createContext("/login", new MyHandler()).
                setAuthenticator(new BasicAuthenticator("test") {
            @Override
            public boolean checkCredentials(String user, String pwd) {
                return user.equals(credentials.getUserName())
                        && pwd.equals(credentials.getPassWord());
            }
        });
        server.setExecutor(null);
        server.start();

        //var ffmpeg = new ffmpegExecuter();
    }
}
class MyHandler implements HttpHandler {
    public String fileToString(Path path) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String FileContent;
        var file = new File(path.toUri());
        BufferedReader input = new BufferedReader(new FileReader(file));
        while((FileContent = input.readLine())!=null){
            stringBuilder.append(FileContent);
        }
        input.close();
        return String.valueOf(stringBuilder);
    }
    public void handle(HttpExchange t) throws IOException {
        InputStream is = t.getRequestBody();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        StringBuilder content = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) { // br.readLine is always null!!!
            content.append(line);
            content.append("\n");
        }

        System.out.println("Content: " + content.toString());

        var httpFilePath =
                Path.of(new File(".").getAbsolutePath()+
                        "/untitled/src/main/resources/home.html");
        var response = fileToString(httpFilePath);
        t.sendResponseHeaders(200, response.length());

        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}