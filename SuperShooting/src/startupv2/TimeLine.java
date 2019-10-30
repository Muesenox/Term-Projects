package startupv2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TimeLine extends JPanel implements ActionListener {
    
    private int x = 502;
    private int y = 0;
    private int speedy = 1;
    private Timer t;
    private int loop;
    
    public TimeLine() {
        t = new Timer(10, this);
        t.start();
    }
    
    public void move() {
        if(loop % 60 == 0 && y <= 700) y += speedy;
        loop+=t.getDelay();
    }
    
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if(y < 175) {
            g2d.setColor(Color.GREEN);
            g2d.fillRect(x, y, 11, 700);    
        }
        else if(y >= 175 && y < 350) {
            g2d.setColor(Color.YELLOW);
            g2d.fillRect(x, y, 11, 700);
        }
        else if(y >= 350 && y < 525) {
            g2d.setColor(Color.ORANGE);
            g2d.fillRect(x, y, 11, 700);
        }
        else if(y >= 525) {
            g2d.setColor(Color.RED);
            g2d.fillRect(x, y, 11, 700);
        }
        g2d.setColor(Color.WHITE);
        g2d.fillRect(465, 0, 80, 30);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(469, 2, 70, 25);
        g.setFont(new Font("Impact", Font.PLAIN, 25));
        g.setColor(Color.BLACK);
        g.drawString("Timer", 475, 24);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    
    public int getY() {
        return y;
    }
    
    public void stopTimeLine() {
        speedy = 0;
    }
    
    public void startTimeLine() {
        speedy = 1;
    }
    
    public void startAgain() {
        y = 0;
    }
    
}
