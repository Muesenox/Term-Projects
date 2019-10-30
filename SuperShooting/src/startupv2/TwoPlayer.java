package startupv2;

import java.awt.event.KeyEvent;

public class TwoPlayer {
    private int x = 520;
    private int speedx = 0;
    private int y = 594;
    private boolean keyInputLeft, keyInputRight;
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) keyInputLeft = true;
	if (e.getKeyCode() == KeyEvent.VK_RIGHT) keyInputRight = true;
    }
    
    public void keyReleased(KeyEvent e) {
		keyInputLeft = keyInputRight = false;
    }
    
    public void move() {
        if(keyInputLeft) {
            speedx = -4;
            if(x + speedx >= 520 && x + speedx <= 940) x+=speedx;
        }
        if(keyInputRight) {
            speedx = 4;
            if(x + speedx >= 520 && x + speedx <= 940) x+=speedx;
        }
        else {
            speedx = 0;
            if(x + speedx >= 520 && y + speedx <= 940) x += speedx;
        }
    }
    
    public int getY() {
        return y;
    }
    
    public int getX() {
        return x;
    }
    
}
