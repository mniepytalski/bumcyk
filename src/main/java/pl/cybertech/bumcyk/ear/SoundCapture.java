package pl.cybertech.bumcyk.ear;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.cybertech.bumcyk.ear.signal.CalculateFFT;

@Component
public class SoundCapture {

    private static final Logger LOG = LoggerFactory.getLogger(SoundCapture.class);

    @Autowired
    private SoundRawData soundRawData;

    @Autowired
    private SoundFFTData soundFFTData;

    boolean stopped;

    boolean running;
    
    public SoundCapture() {
        stopped = false;
    }

    AudioFormat getFormat() {
//        float sampleRate = 8000;
//        int sampleSizeInBits = 16;
//        int channels = 1;
//        boolean signed = true;
//        boolean bigEndian = true;
//        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
//        return format;
        
        
        AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
        float rate = 8000.0f;
        int channels = 1;
        int sampleSize = 8;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8) * channels, rate, bigEndian);
        return format;
    }

    public void capture() {

        try {

            final AudioFormat format = getFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            final TargetDataLine line = (TargetDataLine)AudioSystem.getLine(info);
            line.open(format);
            line.start();

            
            
            
            Runnable runner = new Runnable() {
                
                public void run() {
                    
                    boolean stopped = false;
                    int count = 0;

                    int numBytesRead;
                    int bufferSize = (int) format.getSampleRate() * format.getFrameSize();
                    byte[] data = new byte[bufferSize];

                    while (!stopped) {
                        
                        numBytesRead = line.read(data, 0, data.length);
                        if (numBytesRead < 0) {
                            LOG.error("numBytesRead < 0");
                            break;
                        }
                        soundRawData.setData(data);
                        CalculateFFT fft = new CalculateFFT();
                        soundFFTData.setData(fft.calculateFFT(data));
                        if (++count > 1000) {
                            break;
                        }
                    }
                }
            };
            
            // launch
//
            runner.run();
//            Thread captureThread = new Thread(runner);
//            captureThread.start();
          
        } catch (LineUnavailableException ex) {
            LOG.error(ex.getMessage());
        }

    }

}
