package org.example;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
public class server {
    public static HttpServer server;
    public static boolean checkStreamFolder(){
        if (Files.exists(Path.of(new File(".").getAbsolutePath()
                +"/stream/"))){
            window.textArea1.append("\n" + window.now() + "'stream' folder found");
            return true;
        }else{
            window.textArea1.append("\n" + window.now() + "Server cannot be started: 'stream' folder NOT FOUND near tvStreamer.jar !");
            return false;
        }
    }
    public static void startServer(String username, String password) throws UnknownHostException {
        if (server != null){
            window.textArea1.append("\n\n" + window.now() + "Stopping server...");
            server.stop(0);
            window.textArea1.append("\n" + window.now() + "Server stopped");
        }
        if(username != null && password != null && username.length()!=0 && password.length()!=0
        && checkStreamFolder()){
            window.textArea1.append("\n\n" + window.now() + "Starting server...");
            var addr = new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), 8080);
            server = SimpleFileServer.createFileServer(
                    addr,
                    Path.of(new File(".").getAbsolutePath()
                            +"/stream/"),
                    SimpleFileServer.OutputLevel.INFO); //VERBOSE or INFO or NONE
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
            window.textArea1.append("\n" + window.now() + "Server started at address: " + addr);
            window.textArea1.append("\n" + window.now() + "Username: " + username);
            window.textArea1.append("\n" + window.now() + "Password: " + password);
            window.textArea1.append("\n" + window.now() + "Server root folder: "+Path.of(new File(".").getAbsolutePath()
                    +"/stream/"));
        }
        else{
            window.textArea1.append("\n\n" + window.now() + "Server cannot be started: Username or password have not been inserted!");
        }
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
        while ((line = br.readLine()) != null) {
            content.append(line);
            content.append("\n");
        }
        arduinoSerial.decodeQueryString(content.toString());
        try {
            arduinoSerial.processCommands(String.valueOf(arduinoSerial.parametersReceived.get("tvRemoteButton")));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        var httpFilePath =
                Path.of(new File(".").getAbsolutePath()+
                        "/stream/home.html");
        var response = fileToString(httpFilePath);
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}