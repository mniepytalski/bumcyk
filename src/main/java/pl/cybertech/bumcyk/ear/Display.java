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
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Display {

    private static int speed = 5;

    double offsetX = 0;

    @Autowired
    private SoundRawData soundRawData;

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
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setStroke(new BasicStroke(1f));
                g2d.setPaint(Color.BLACK);

//                double scaleY = getHeight() / 2;
                double scaleY = 0.1;
                double scaleX = 0.1;
                int middleY = getHeight() / 2;

                if (soundRawData != null) {
                    byte data[] = soundRawData.getData();
                    if (data != null) {
                        for (int x = 0; x < 200; x++) {
                            g2d.drawLine(x, middleY + (int)((data[x]) * scaleY), x + 1,
                                middleY + (int)((data[x+1]) * scaleY));
                        }
                    }
                } else {

                    for (int x = 0; x < 200; x++) {
                        g2d.drawLine(x, middleY + (int) (Math.sin(x * scaleX + offsetX) * scaleY), x + 1,
                            middleY + (int) (Math.sin((x + 1) * scaleX + offsetX) * scaleY));
                    }
                    offsetX += 0.1;
                }

            }
        });
        frame.add(new JSlider(JSlider.HORIZONTAL, 1, 10, speed) {
            {
                addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent e) {
                        speed = getValue();
                    }
                });
            }
        }, BorderLayout.SOUTH);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
