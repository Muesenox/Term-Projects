package startupv2;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class OneBow extends JPanel {
    
    private OnePlayer p1;
    private int x;
    private int y;
    private static ImageIcon drop;
    
    public OneBow(OnePlayer p1) {
        this.p1 = p1;
        drop = new ImageIcon(this.getClass().getResource("/Image/bullet1.png"));
        x = p1.getX();
        y = p1.getY() + 5;
    }
    
    public void paint(Graphics g) {
        super.paintComponent(g);
        g.drawImage(drop.getImage(), x + 26, y - 20, 5, 20, this);
    }
    
    public Rectangle getBounds(){ 
        return new Rectangle(x, y, 30, 30);
    }
    
    public void move() {
        y -= 5;
    }
    
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    
    public int getY() {
        return y;
    }
    
}
