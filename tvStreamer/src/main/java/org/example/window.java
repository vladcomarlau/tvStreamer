package org.example;

import org.apache.commons.io.IOUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;
import java.util.Date;

public class window {
    public static final JTextArea textArea1 = new JTextArea();
    public static Vector<String> videoDevices = new Vector<String>();
    public static Vector<String> audioDevices = new Vector<String>();
    public static String now(){
        return "["+String.valueOf(new Date()) + "] ";
    }
    public static void getDevices() throws IOException {
        String[] cmd = {"ffmpeg", "-hide_banner","-list_devices","true","-f","dshow","-i","dummy"};
        ProcessBuilder processBuilder1 = new ProcessBuilder(cmd);

        String output = IOUtils.toString(processBuilder1.start().getErrorStream(), "UTF-8");
        String devices[] = output.split("\\r?\\n");

        window.textArea1.append("\n\n" + window.now() + "Capture devices found:");
        for(int i = 0; i < devices.length-1; i++){
            if (i%2==0){
                window.textArea1.append("\n"+devices[i]);
                if(devices[i].contains("(video)")){
                    devices[i] = devices[i].substring(devices[i].indexOf("\"") + 1);
                    devices[i] = devices[i].substring(0, devices[i].indexOf("\""));
                    videoDevices.add(devices[i]);
                }
                else if(devices[i].contains("(audio)")){
                    devices[i] = devices[i].substring(devices[i].indexOf("\"") + 1);
                    devices[i] = devices[i].substring(0, devices[i].indexOf("\""));
                    audioDevices.add(devices[i]);
                }
            }
        }

    }
    public window() throws IOException {
        var frame = new JFrame("tvStreamer");

        Border blackBorder = BorderFactory.createLineBorder(new Color(121, 137, 152));

        var usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(10,40,90,20);
        frame.add(usernameLabel);
        var usernameInputField = new JTextArea();
        usernameInputField.setBounds(100,40,200,20);
        usernameInputField.setBorder(BorderFactory.createCompoundBorder(blackBorder,
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
        frame.add(usernameInputField);

        var passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(310,40,90,20);
        frame.add(passwordLabel);
        var passwordInputField = new JTextArea();
        passwordInputField.setBounds(400,40,200,20);
        passwordInputField.setBorder(BorderFactory.createCompoundBorder(blackBorder,
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
        frame.add(passwordInputField);

        textArea1.setBounds(10,70,745, 652);
        textArea1.setEditable(false);
        textArea1.setCaretColor(Color.WHITE);
        textArea1.setBackground(new Color(236, 236, 236));
        JScrollPane scrollPane = new JScrollPane(textArea1,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(10,70,745, 652);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(blackBorder,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        DefaultCaret caret = (DefaultCaret)textArea1.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        frame.getContentPane().add(scrollPane);

        var videoLabel = new JLabel("Video devices:");
        videoLabel.setBounds(10,10,90,20);
        frame.add(videoLabel);
        var audioLabel = new JLabel("Audio devices:");
        audioLabel.setBounds(310,10,90,20);
        frame.add(audioLabel);

        var comboBoxVideo = new JComboBox<String>(videoDevices);
        comboBoxVideo.setBounds(100,10,200,20);
        frame.add(comboBoxVideo);
        var comboBoxAudio = new JComboBox<String>(audioDevices);
        comboBoxAudio.setBounds(400,10,200,20);
        frame.add(comboBoxAudio);

        var startButton = new JButton("Start stream");
        startButton.setBounds(615,10,140, 20);
        startButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    server.startServer(usernameInputField.getText(),passwordInputField.getText());
                } catch (UnknownHostException ex) {
                    throw new RuntimeException(ex);
                }
                window.textArea1.append("\n\n" + window.now() + "Starting stream...");
                try {
                    new ffmpegStream((String) comboBoxVideo.getSelectedItem(),
                            (String) comboBoxAudio.getSelectedItem());
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        frame.add(startButton);

        final int windowW = 782;
        final int windowH = 782;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = screenSize.width/2 - windowW/2;
        int centerY = screenSize.height/2 - windowH/2;
        frame.setLocation(centerX, centerY);

        frame.setSize(windowW,windowH);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if(server.server != null){
                    server.server.stop(0);
                }
            }
        });
    }
}
