# tvStreamer
Java TV set-top box web interface (remote control and viewer)
<br>
<img width="571" alt="1" src="https://github.com/vladcomarlau/tvStreamer/assets/102293760/4de1306c-dc4d-45a2-8588-c89c1ef891d4">

Features:
  - Real time HD TV cable streaming with ~10 seconds delay (accessible from Desktop, iOS / Android)
  - Channel grid menu:
      - automatically updates channel name by taking a screenshot when changing channels
        (This way it stays updated evem if the TV operator adds/removes TV channels from the channel grid)
      - automatically sends corresponding number keys in order to jump between channels as fast as possible
  - Basic username and password authentication using built-in Simple File Server BasicAuthenticator
  - Mobile repsonsive, translucent UI
  - GUI window:
      - Capture device selection
      - Username and password input fields
      - Logs text area
  - Other controls: power receiver on/off, channel information, channel +/-, back, exit, fullscreen, mute, play/pause

![diagram](https://github.com/vladcomarlau/tvStreamer/assets/102293760/8606d1b3-d257-4a48-9fa6-0a072d77f88c)

Hardware used:
  - TV receiver brand KAON, model NA1410HD
  - PLUSIVO Micro ESP8266 with added IR transmitter
  - HDMI USB capture card
    
Technologies used:
  - FFMPEG for HLS protocol live stream enconding (M3U8 / x-mpegURL manifest file with TS video fragments)
  - JAVA Simple File Server with multithreading for screenshot synchronization / waiting
  - jSerialComm for serial port direct USB communication with the ESP8266 microcontroller
  - Maven for dependency management
  - Mostly vanilla Javascript with few parts in JQuerry
  - Java Swing for GUI
  - Vanilla CSS and HTML5
    
Requirements:
- FFMPEG installed and added to PATH variable
- OS: Windows (program is dependent on executing CMD commands)

Download release:
<a href="https://github.com/vladcomarlau/tvStreamer/releases/tag/v1">tvStreamer_v1.zip</a>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img width="500" alt="Screenshot 2023-08-16 at 01 13 25" src="https://github.com/vladcomarlau/tvStreamer/assets/102293760/d7605e7d-4c16-4ea0-b330-e6165dcc2e49">

<img width="579" alt="Screenshot 2023-07-30 at 09 53 18" src="https://github.com/vladcomarlau/tvStreamer/assets/102293760/5e88f055-ae74-4eea-9061-3fb084f7dfa2">




