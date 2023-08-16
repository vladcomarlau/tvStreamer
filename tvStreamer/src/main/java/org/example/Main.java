package org.example;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        new Thread(() -> {
            try {
                new window();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
        window.textArea1.append("tvStreamer\nVlad Comarlau - August 2023");
        new arduinoSerial();
        window.getDevices();
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
