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
<img width="500" src="https://github.com/vladcomarlau/tvStreamer/assets/102293760/563673b4-dcb4-41f8-9ae0-dcea4ce65645">

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

<img width="563" alt="260854263-d7605e7d-4c16-4ea0-b330-e6165dcc2e49" src="https://github.com/vladcomarlau/tvStreamer/assets/102293760/dd74ae8c-b57b-461a-be8a-cdbbacad1e67">
<img width="579" alt="257036880-5e88f055-ae74-4eea-9061-3fb084f7dfa2" src="https://github.com/vladcomarlau/tvStreamer/assets/102293760/3632a588-ab56-4602-ae3a-ce0948c73fbc">


