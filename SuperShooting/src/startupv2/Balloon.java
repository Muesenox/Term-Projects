package startupv2;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Balloon extends JPanel implements ActionListener {
    
    private static ImageIcon ball1, ball2, ball3, ball4;
    private Random rand = new Random();
    private int y1 = 0;
    private int x = 1000;
    private int colour;
    private Player p;
    private Game g;
    private Bow b;
    
    Balloon(Bow b, Player p, Game g){
        y1 = rand.nextInt(500) + 20;
        colour = rand.nextInt(3);
        ball1 = new ImageIcon(this.getClass().getResource("/Image/rb.png"));
        ball2 = new ImageIcon(this.getClass().getResource("/Image/bb.png"));
        ball3 = new ImageIcon(this.getClass().getResource("/Image/gb.png"));
        this.p = p;
        this.g = g;
        this.b = b;
    }
    
    public void paint(Graphics g){
        super.paintComponent(g);
        if(colour == 0) g.drawImage(ball1.getImage(),x, y1, 50, 50, this);
        if(colour == 1) g.drawImage(ball2.getImage(),x, y1, 50, 50, this);
        if(colour == 2) g.drawImage(ball3.getImage(),x, y1, 100, 50, this);
    }
    
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    
    public void move() {
        int high = Integer.parseInt(Game.sc.get(Game.sc.size()-1));
        if(high <= 10) x-=1;
        else if(high > 10 && high <= 30) x-=2;
        else if(high > 30 && high <= 50) x-=3;
        else if(high > 50 && high <= 70) x-=4;
        else if(high > 70 && high <= 90) x-=5;
        else if(high > 90 && high <= 110) x-=6;
        else if(high > 110 && high <= 130) x-=7;
        else if(high > 130 && high <= 150) x-=8;
        else if(high > 150 && high <= 170) x-=9;
        else if(high > 170 && high <= 190) x-=10;
        else if(high > 190) x-=20;
        if(checkGameOver()) g.doGameOver();
        if(checkCollision()) g.doGameOver();
    }
    
    public Rectangle getBoundsY1(){ 
        return new Rectangle(x, y1, 50, 50);
    }
    
    public boolean checkGameOver(){
        boolean hint = false;
        if(g.getHorizon().intersects(getBoundsY1())) hint = true;
        return hint;
    }
    
    public boolean checkCollision() {
        boolean hint = false;
        for(int i = 0; i < Game.ar.size(); i++) {
            if(Game.ar.get(i).getBounds().intersects(getBoundsY1())) hint = true;
        }
        return hint;
    }
    
    public int getXB() {
        return x;
    }
    
    public int getY1B() {
        return y1;
    }
    
    public int getColour() {
        return colour;
    }
    
}
