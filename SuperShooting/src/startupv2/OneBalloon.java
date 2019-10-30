package startupv2;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class OneBalloon extends JPanel implements ActionListener {
    
    private static ImageIcon ball1, ball2, ball3;
    private Random rand = new Random();
    private int y = 0;
    private int x = 0;
    private int colour;
    private OnePlayer p1;
    private Game g;
    private OneBow b1;
    
    public OneBalloon(OneBow b1, OnePlayer p1, Game g) {
        x = rand.nextInt(410) + 10;
        colour = rand.nextInt(3);
        ball1 = new ImageIcon(this.getClass().getResource("/Image/rb.png"));
        ball2 = new ImageIcon(this.getClass().getResource("/Image/bb.png"));
        ball3 = new ImageIcon(this.getClass().getResource("/Image/gb.png"));
        this.b1 = b1;
        this.p1 = p1;
        this.g = g;
    }
    
    public void paint(Graphics g){
        super.paintComponent(g);
        if(colour == 0) g.drawImage(ball1.getImage(),x, y, 40, 40, this);
        if(colour == 1) g.drawImage(ball2.getImage(),x, y, 40, 40, this);
        if(colour == 2) g.drawImage(ball3.getImage(),x, y, 80, 40, this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    
    public void move() {
        int high = Integer.parseInt(Game.sc1.get(Game.sc1.size()-1));
        if(high <= 10) y+=1;
        else if(high > 10 && high <= 30) y+=2;
        else if(high > 30 && high <= 50) y+=3;
        else if(high > 50 && high <= 70) y+=4;
        else if(high > 70 && high <= 90) y+=5;
        else if(high > 90 && high <= 110) y+=6;
        else if(high > 110 && high <= 130) y+=7;
        else if(high > 130 && high <= 150) y+=8;
        else if(high > 150 && high <= 170) y+=9;
        else if(high > 170 && high <= 190) y+=10;
        else if(high > 190) y+=20;
        if(checkGameOver()) g.doGameOver();
        if(checkCollision()) g.doGameOver();
    }
    
    public Rectangle getBounds(){ 
        return new Rectangle(x, y, 40, 40);
    }
    
    public boolean checkGameOver(){
        boolean hint = false;
        if(g.getHorizon1().intersects(getBounds())) hint = true;
        return hint;
    }
    
    public boolean checkCollision() {
        boolean hint = false;
        for(int i = 0; i < Game.ob.size(); i++) {
            if(Game.ob.get(i).getBounds().intersects(getBounds())) hint = true;
        }
        return hint;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
}
