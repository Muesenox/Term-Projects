package startupv2;

import java.awt.event.KeyEvent;

public class OnePlayer {
    private int x = 5;
    private int speedx = 0;
    private int y = 594;
    private boolean keyInputLeft, keyInputRight;
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) keyInputLeft = true;
	if (e.getKeyCode() == KeyEvent.VK_D) keyInputRight = true;
    }
    
    public void keyReleased(KeyEvent e) {
		keyInputLeft = keyInputRight = false;
    }
    
    public void move() {
        if(keyInputLeft) {
            speedx = -4;
            if(x + speedx >= 0 && x + speedx <= 440) x+=speedx;
        }
        if(keyInputRight) {
            speedx = 4;
            if(x + speedx >= 0 && x + speedx <= 440) x+=speedx;
        }
        else {
            speedx = 0;
            if(x + speedx >= 0 && y + speedx <= 440) x += speedx;
        }
    }
    
    public int getY() {
        return y;
    }
    
    public int getX() {
        return x;
    }
    
}
