package startupv2;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Player {
    private int y;
    private int x = 20;
    private int speedy = 0;
    private boolean keyInputUp, keyInputDown;
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) keyInputUp = true;
	if (e.getKeyCode() == KeyEvent.VK_DOWN) keyInputDown = true;
    }
    
    public void keyReleased(KeyEvent e) {
		keyInputUp = keyInputDown = false;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    //player move
    public void move() {
        if(keyInputUp) {
            speedy = -5;
            if(y + speedy >= 0 && y + speedy <= 570) y += speedy;
        }
        if(keyInputDown) {
            speedy = +5;
            if(y + speedy >= 0 && y + speedy <= 570) y += speedy;
        }
        else {
            speedy = 0;
            if(y + speedy >= 0 && y + speedy <= 570) y += speedy;
        }
    }
    
     public Rectangle getBounds(){
       return new Rectangle(x,y,80,103);
    }
}
