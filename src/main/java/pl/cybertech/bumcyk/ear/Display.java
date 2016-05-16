package pl.cybertech.bumcyk.ear;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Display {

    private static int speed = 5;

    double offsetX = 0;

    @Autowired
    private SoundRawData soundRawData;
    
    @Autowired
    private SoundFFTData soundFFTData;

    public Display() {
    }
    
    public void show() {

        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());

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
                paintFftFrameDiagram(g, this);
                paintTimeFrameDiagram(g, this);
            }
        });

        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    void paintTimeFrameDiagram(Graphics g, JComponent component) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(1f));
        g2d.setPaint(Color.GREEN);

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

    void paintFftFrameDiagram(Graphics g, JComponent component) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(1f));
        g2d.setPaint(Color.RED);

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
                    g2d.drawLine(x, middleY + (int)((data[x]) * scaleY), x + 1, middleY + (int)((data[x+1]) * scaleY));
                }
            }
        }
    }
    
    int conv2int(byte[] data, int index) {
//        int ret = data[index<<1 + 1]&0xFF + (data[index<<1])<<8;
      int ret = data[index];
        return ret;
    }
    
}
