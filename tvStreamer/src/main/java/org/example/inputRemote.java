package org.example;
import java.io.*;
import java.net.*;
import java.util.*;
public class inputRemote {
    public static Map parameters;
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
            parameters = params;
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e); // Cannot happen with UTF-8 encoding.
        }
    }
    public static void sendCommand(String key) throws IOException {
        URL url = new URL("http://127.0.0.1:9989/?key="+key);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println(response);
        } else {
            System.out.println("GET request did not work.");
        }
    }
    public static void processCommands(String key) throws IOException {
        if(key.contains("OK")){
            sendCommand(key);
        }else if(Character.isDigit(key.charAt(0))){
            for(int i = 0; i<key.length();i++){
                sendCommand(String.valueOf(key.charAt(i)));
            }
            sendCommand("OK");
        }
    }
}
