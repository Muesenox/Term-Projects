package bw_imagemultithread;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class Result extends JPanel {
    
    private int[][] image;
    
    public Result(int[][] image) {
        this.image = image;
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        for(int i = 0; i < image.length; i++) {
            for(int j = 0; j < image[i].length; j++) {
                if(image[i][j] == 0) g2d.drawRect(j, i, 0, 0);
            }
        }
    }
}
