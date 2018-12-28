package pl.cybertech.bumcyk.ear.diagrams;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import pl.cybertech.bumcyk.ear.SoundData;
import pl.cybertech.bumcyk.ear.SoundFFTData;

public class PatternFftFrameDiagram extends Diagram {

	int offset = 0;
	
	@Override
	public Diagrams getName() {
		return Diagrams.PATTERN_FFT_FRAME;
	}

	@Override
	public void print(Graphics g, JComponent component, SoundData soundData) {
        Graphics2D g2d = (Graphics2D) g;
        
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2d.setStroke(new BasicStroke(1f));
//        g2d.setPaint(Color.GREEN);

        SoundFFTData soundFFTData = (SoundFFTData)soundData;
        
        double scaleY = 2;
//        double scaleX = 0.1;
        int middleY = component.getHeight() / 2;

        if (soundFFTData != null) {
            double data[] = soundFFTData.getData();
            if (data != null) {
                int printSizeX = component.getWidth();
                if (printSizeX >= data.length) {
                    printSizeX = data.length - 1;
                }
                for (int x = 0; x < printSizeX; x++) {
                	int value = (int)((data[x]));
                	if ( value>255 ) {
                		value = 255;
                	}
                    Color color = new Color(255,255-value,255);
                    g2d.setPaint(color);
                	
                    g2d.drawLine(offset, x, offset, x);
                    
                }
            }
        }
        offset++;
        if ( offset>component.getWidth()) 
        	offset = 0;

		
	}

}
