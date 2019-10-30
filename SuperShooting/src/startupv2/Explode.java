package startupv2;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Explode extends JPanel implements ActionListener {
    
    private static ImageIcon tome;
    private int x, y;
    private Balloon b;
    private Timer t;
    
    public Explode(Balloon b) {
        this.b = b;
        tome = new ImageIcon(this.getClass().getResource("/Image/explode.png"));
        x = b.getXB();
        y = b.getY1B();
        t = new Timer(250, this);
        t.start();
    }
    
    public void paint(Graphics g) {
        super.paintComponent(g);
        g.drawImage(tome.getImage(), x, y, 50, 50, this);
    }
    
    @Override
    public void actionPerformed(ActionEvent a) {
        try{ for(Explode i : Game.ex) Game.ex.remove(i);
                } catch ( Exception e){
            }
        t.stop();
    }
    
}
