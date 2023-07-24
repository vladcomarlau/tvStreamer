package org.example;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class ffmpegExecuter extends Thread{
    public ffmpegExecuter() throws IOException, InterruptedException {
        FileUtils.deleteDirectory(new File(Path.of(new File(".").getAbsolutePath()
                +"/tvStreamer/src/main/java/org/example/stream/video/").toUri()));
        new File(String.valueOf(new File(Path.of(new File(".").getAbsolutePath()
                +"/tvStreamer/src/main/java/org/example/stream/video/").toUri()).mkdirs()));

        var streamFilesLocation = Path.of(new File(".").getAbsolutePath()+
                "/tvStreamer/src/main/java/org/example/stream/video").toAbsolutePath();
        var command = " cmd.exe /c start " +
                "ffmpeg -f dshow -framerate 30 -pixel_format yuyv422 -i video=\"" +
                "USB Video\":audio=\"Digital Audio Interface (2- USB Digital Audio)\" -b:v 1.2M -b:a 96k " +
                "-vf scale=1280x720 -hls_list_size 1 -hls_flags delete_segments -sc_threshold 0 -g 5 " +
                "-force_key_frames \"expr:gte(t, n_forced)\" -segment_time 0  -hls_time 1 " +
                streamFilesLocation+"/out.m3u8";
        System.out.println(command);
        Runtime.getRuntime().exec("taskkill /F /IM ffmpeg.exe");
        TimeUnit.SECONDS.sleep(3);
        Runtime.getRuntime().exec(command);
    }
}
