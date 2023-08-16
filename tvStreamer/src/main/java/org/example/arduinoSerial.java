package org.example;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import com.fazecast.jSerialComm.SerialPort;
public class arduinoSerial {
    public static SerialPort serialPort;
    public static HashMap<String, Integer> keyCodes;
    public static Map parametersReceived;
    public arduinoSerial(){
        keyCodes = new HashMap<>();
        keyCodes.put("power",       68157695);
        keyCodes.put("1",           68171975);
        keyCodes.put("2",           68204615);
        keyCodes.put("3",           68188295);
        keyCodes.put("4",           68158205);
        keyCodes.put("5",           68190845);
        keyCodes.put("6",           68174525);
        keyCodes.put("7",           68166365);
        keyCodes.put("8",           68199005);
        keyCodes.put("9",           68182685);
        keyCodes.put("0",           68215325);
        keyCodes.put("channelPlus", 68182175);
        keyCodes.put("channelMinus",68214815);
        keyCodes.put("volumePlus",  68174015);
        keyCodes.put("volumeMinus", 68206655);
        keyCodes.put("info",        68160245);
        keyCodes.put("left",        68167895);
        keyCodes.put("right",       68184215);
        keyCodes.put("up",          68176055);
        keyCodes.put("down",        68208695);
        keyCodes.put("ok",          68200535);
        keyCodes.put("exit",        68159735);
        keyCodes.put("opt",         68217365);
        keyCodes.put("back",        68169935);
        keyCodes.put("guide",       68202575);
        keyCodes.put("media",       68180645);
        keyCodes.put("menu",        68192375);
        serialPort = SerialPort.getCommPort(SerialPort.getCommPorts()[0].getSystemPortName());
        serialPort.setBaudRate(115200);
        if (serialPort.openPort()){
            window.textArea1.append("\n\n" + window.now() + "Arduino board\nPort: " +
                    SerialPort.getCommPorts()[0].getSystemPortName() +
                    " - opened \nBaud rate: " + serialPort.getBaudRate());
        }else{
            window.textArea1.append("\n\nArduino board found on port " +
                    SerialPort.getCommPorts()[0].getSystemPortName() +
                    " - closed \nBaud rate: " + serialPort.getBaudRate());
        }
    }
    public static void decodeQueryString(String query) {
        try {
            Map<String, String> params = new LinkedHashMap<>();
            for (String param : query.split("&")) {
                String[] keyValue = param.split("=", 2);
                String key = URLDecoder.decode(keyValue[0], "UTF-8");
                String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1], "UTF-8") : "";
                if (!key.isEmpty()) {
                    params.put(key, value);
                }
            }
            parametersReceived = params;
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e); // Cannot happen with UTF-8 encoding.
        }
    }
    public static void sendKey(String key) throws IOException {
        serialPort.getOutputStream().write((String.valueOf(keyCodes.get(key))+"\n").getBytes());
        serialPort.getOutputStream().flush();
        window.textArea1.append("\n" + window.now() + " Key pressed: " + key);
        window.textArea1.setCaretPosition(window.textArea1.getDocument().getLength());
    }
    public static void processCommands(String command) throws InterruptedException {
        if(!command.isEmpty() && !command.equals("null")){
            window.textArea1.append("\n");
            try{
                if (Character.isDigit(command.charAt(0))) {
                    if(command.length()<5) {
                        for (int i = 0; i < (command.length() - 1); i++) {
                            if (Character.isDigit(command.charAt(i))) {
                                sendKey(String.valueOf(command.charAt(i)));
                                Thread.sleep(300);
                            }
                        }
                        Thread.sleep(300);
                        sendKey("ok");
                        ffmpegScreenshot threadFfmpegScreenshot = new ffmpegScreenshot(command);
                        threadFfmpegScreenshot.start();
                    }
                }
                else{
                    sendKey(command.trim());
                    if(command.equals("back")){
                        sendKey("exit");
                    }
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}