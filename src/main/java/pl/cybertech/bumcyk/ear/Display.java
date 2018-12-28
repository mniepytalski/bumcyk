package pl.cybertech.bumcyk.ear;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.cybertech.bumcyk.ear.diagrams.Diagram;
import pl.cybertech.bumcyk.ear.diagrams.Diagrams;
import pl.cybertech.bumcyk.ear.diagrams.LinearFftFrameDiagram;
import pl.cybertech.bumcyk.ear.diagrams.LinearTimeFrameDiagram;
import pl.cybertech.bumcyk.ear.diagrams.PatternFftFrameDiagram;

@Component
public class Display {

    private static int speed = 5;

    double offsetX = 0;

    @Autowired
    private SoundRawData soundRawData;
    
    @Autowired
    private SoundFFTData soundFFTData;
    
    Map<Diagrams, Diagram> diagrams = new HashMap<Diagrams, Diagram>();

    public Display() {
    	diagrams.put(Diagrams.LINEAR_TIME_FRAME, new LinearTimeFrameDiagram());
    	diagrams.put(Diagrams.LINEAR_FFT_FRAME, new LinearFftFrameDiagram());
    	diagrams.put(Diagrams.PATTERN_FFT_FRAME, new PatternFftFrameDiagram());
    }
    
    public void show() {

        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setTitle("Ucho");
        
        frame.add(new JComponent() {

            private static final long serialVersionUID = 1L;

            private int diff = 0;
            {
                final Timer timer = new Timer(1000 / (10 * speed), null);
                timer.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (diff < 20) {
                            diff++;
                        } else {
                            diff = 0;
                        }
                        repaint();
                        timer.setDelay(1000 / (10 * speed));
                    }
                });
                timer.start();
            }

            protected void paintComponent(Graphics g) {
            	super.paintComponent(g);
                diagrams.get(Diagrams.LINEAR_FFT_FRAME).print(g, this, soundFFTData);
//                diagrams.get(Diagrams.PATTERN_FFT_FRAME).print(g, this, soundFFTData);
                diagrams.get(Diagrams.LINEAR_TIME_FRAME).print(g, this, soundRawData);
                
            }
        });

        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }    
    
}
