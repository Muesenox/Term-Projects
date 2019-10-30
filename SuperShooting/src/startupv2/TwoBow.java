package startupv2;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class TwoBow  extends JPanel {
    
    private TwoPlayer p2;
    private int x;
    private int y;
    private static ImageIcon drop;
    
    public TwoBow(TwoPlayer p2) {
        this.p2 = p2;
        drop = new ImageIcon(this.getClass().getResource("/Image/bullet1.png"));
        x = p2.getX();
        y = p2.getY() + 5;
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
