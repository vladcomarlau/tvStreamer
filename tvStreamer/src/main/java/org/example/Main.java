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
        window.checkFFMPEG();
        new arduinoSerial();
    }
}

