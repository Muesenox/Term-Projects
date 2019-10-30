package startupv2;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Explode1 extends JPanel implements ActionListener {
    
    private static ImageIcon tome;
    private int x, y;
    private OneBalloon b;
    private Timer t;
    
    public Explode1(OneBalloon b) {
        this.b = b;
        tome = new ImageIcon(this.getClass().getResource("/Image/explode.png"));
        x = b.getX();
        y = b.getY();
        t = new Timer(250, this);
        t.start();
    }
    
    public void paint(Graphics g) {
        super.paintComponent(g);
        g.drawImage(tome.getImage(), x, y, 40, 40, this);
    }
    
    @Override
    public void actionPerformed(ActionEvent a) {
        try{ for(Explode1 i : Game.ex1) Game.ex1.remove(i);
                } catch ( Exception e){
            }
        t.stop();
    }
    
}