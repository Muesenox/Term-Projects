package startupv2;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Bow extends JPanel {
    private Player p;
    private int x = -20;
    private int y = 0;
    private static ImageIcon drop;
    
    public Bow(Player p) {
        this.p = p;
        drop = new ImageIcon(this.getClass().getResource("/Image/bullet.png"));
        y = p.getY();
        x = p.getX() + 10;
    }
    
    public void paint(Graphics g){
        super.paintComponent(g);
        g.drawImage(drop.getImage(), x + 80, y + 35, 30, 10, this);
    }
    
    public Rectangle getBounds(){ 
        return new Rectangle(x + 80, y + 50, 30, 30);
    }
    
    public void move() {
        x += 5;
    }
    
    public int getXb() {
        return x;
    }
    
    public int getYb() {
        return y;
    }
    
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    
}

