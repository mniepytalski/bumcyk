package pl.cybertech.bumcyk.ear;

import java.io.ByteArrayOutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SoundCapture {

    private static final Logger LOG = LoggerFactory.getLogger(SoundCapture.class);

    @Autowired
    private SoundRawData soundRawData;
    
    boolean stopped;
    
    public SoundCapture() {
        stopped = false;
    }
    
    AudioFormat getFormat() {
        float sampleRate = 8000;
        int sampleSizeInBits = 8;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);        
        return format;
    }
    
    public void capture() {
        
        AudioFormat format = getFormat();
        
        TargetDataLine line = null;
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format); // format is an AudioFormat object
        if (!AudioSystem.isLineSupported(info)) {
            // Handle the error ... 
            LOG.error("line isn't supported");
        }
        // Obtain and open the line.
        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
        } catch (LineUnavailableException ex) {
            LOG.error(ex.getMessage());
        } 
        
        // Assume that the TargetDataLine, line, has already
        // been obtained and opened.
//        ByteArrayOutputStream out  = new ByteArrayOutputStream();
         int numBytesRead;
         byte[] data = new byte[line.getBufferSize() / 5];

         // Begin audio capture.
         line.start();

         boolean stopped = false;

         int count = 0;
         // Here, stopped is a global boolean set by another thread.
         while (!stopped) {
             // Read the next chunk of data from the TargetDataLine.
             numBytesRead =  line.read(data, 0, data.length);
            // Save this chunk of data.
//            out.write(data, 0, numBytesRead);
  
             LOG.info(count + " data read: " + numBytesRead);
//             for ( int i=0; i<numBytesRead; i++) {
//                 System.out.print(data[i]+",");
//             }
             
             if (soundRawData != null) {
                 soundRawData.setData(data);
             }
             
             count++;
             if (count > 1000) {
                 break;
             }
            
         }
        
        
    }
    
    
}
