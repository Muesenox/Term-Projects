package startupv2;

import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class SuperShooting extends JFrame {
    Game g ;
    
     SuperShooting() throws InterruptedException{
        JFrame j = new JFrame();
        g = new Game();;
        
        j.add(g);
        j.setTitle("SUPER SHOOTING GAME");
        j.setSize(1000,700);
        j.setResizable(false);
        j.setDefaultCloseOperation(EXIT_ON_CLOSE);
        j.setVisible(true);
        j.setLocationRelativeTo(null);
        while (true) {                       
            g.getP().move();//move player
            g.move();//move bomb
            g.repaint();
            g.add();
            Thread.sleep(10);
            g.getB().move();
            }
    }
    public static void main(String[] args) throws InterruptedException {
        new SuperShooting();
    }
    
}
