package pl.cybertech.bumcyk.ear.diagrams;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import pl.cybertech.bumcyk.ear.SoundData;
import pl.cybertech.bumcyk.ear.SoundRawData;

public class LinearTimeFrameDiagram extends Diagram {

	@Override
	public Diagrams getName() {
		return Diagrams.LINEAR_TIME_FRAME;
	}
	
	@Override
	public void print(Graphics g, JComponent component, SoundData soundData) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(1f));
        g2d.setPaint(Color.GREEN);

        SoundRawData soundRawData = (SoundRawData)soundData;
        int middleY = component.getHeight() / 2;
        if (soundRawData != null) {
            byte data[] = soundRawData.getData();
            if (data != null) {
                int printSizeX = component.getWidth();
                if (printSizeX >= data.length) {
                    printSizeX = data.length - 1;
                }
                for (int x = 0; x < printSizeX; x++) {
                    g2d.drawLine(x, middleY - conv2int(data, x) / 3, x + 1, middleY - conv2int(data, x+1) / 3);
                }
            }
        }
		
	}
	
    private int conv2int(byte[] data, int index) {
    	int ret = data[index];
    	return ret;
  }

}
