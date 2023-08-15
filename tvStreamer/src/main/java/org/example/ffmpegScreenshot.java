package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.io.FileUtils.copyFile;
import static org.example.ffmpegStream.channelFiles;
import static org.example.ffmpegStream.streamFilesLocation;

public class ffmpegScreenshot extends Thread{
    public String channel;
    public ffmpegScreenshot(String channelX){
        channel = channelX;
    }
    public void run(){
        /*try {
            sleep(15000);
            File source=new File(streamFilesLocation+"/out.jpg");
            File destination=new File(channelFiles + channel.substring(0,channel.length()-1)+".jpeg");
            copyFile(source,destination);
            System.out.println("Copied:\n" + source + "\nto:\n" + destination);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }*/
        /*var command = "cmd.exe /c start  " +
                "ffmpeg -y -i http://"+credentials.getUserName()+":"+credentials.getPassWord()+"@192.168.0.148:8080/video/out.m3u8 " +
                "-filter:v \"crop=262:23:840:177\" " +
                "-max_reload 2 " +
                "-m3u8_hold_counters 2 " +
                "-vframes 1 " +
                channelFiles + channel.substring(0,channel.length()-1)+".jpeg";
        System.out.println(command);
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        String[] cmd = {"ffmpeg", "-y", "-i", streamFilesLocation+"/out.m3u8 ",
                        "-vframes", "1", "-filter:v", "\"crop=262:23:840:177\"",
                        channelFiles + channel.substring(0,channel.length()-1)+".jpeg"};
        System.out.println(Arrays.toString(cmd));
        try {
            sleep(18000);
            ProcessBuilder processBuilder1 = new ProcessBuilder(cmd);
            Process p = processBuilder1.start();
            long pid = p.pid();
            System.out.println("Process id:"+ pid +" started!");

            sleep(9000);
            Runtime.getRuntime().exec("taskkill /F /PID " + pid);
            System.out.println("Terminated PID " + pid);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
