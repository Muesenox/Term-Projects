package bw_imagemultithread;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class BW_ImageMultiThread extends JFrame {
    
    private static final String BEF = "/Users/knight12738/Downloads/BW_Image/src/bw_image/Pieapple_Pen.png";
    static BufferedImage bef;
    static int[][] pixelData;
    static int[][] outputImage;
    
    public static void main(String[] args) throws InterruptedException  {
        //long start = System.nanoTime();
        System.out.println("Image was received.");
        int[] RGB = new int[3];
        int bitGr;
        try {
            bef = ImageIO.read(new File(BEF));
            pixelData = new int[bef.getHeight()][bef.getWidth()];
        } catch(IOException e) {System.out.println("Image Error!");}
        for(int i = 0; i < bef.getHeight(); i++) {
            for(int j = 0; j < bef.getWidth(); j++) {
                RGB = getPixelData(bef, j, i);
                bitGr = (RGB[0] + RGB[1] + RGB[2])/3;
                pixelData[i][j] = bitGr;
            } 
        }
        outputImage = new int[bef.getHeight()][bef.getWidth()];
        int numOfThread = Runtime.getRuntime().availableProcessors();
        int use = numOfThread - Thread.activeCount();
        System.out.println("Creating a B&W Image.");
        long start = System.nanoTime();
        Process[] T = new Process[use]; Thread[] P = new Thread[use];
        for(int t = 0; t < use; t++) {
            T[t] = new Process(); P[t] = new Thread(T[t]);
        }
        for(int row = 0; row < bef.getHeight(); row += use) {
            for(int t = 0; t < use; t++) if(row + t < bef.getHeight()) {
                T[t].Identify(row, t);
                P[t].run();
                P[t].join(2);
            }
        }
        long estimatedTime = System.nanoTime() - start;
        System.out.println("total time : " + (estimatedTime  / 1000) + " milliseconds.");
        System.out.println((use + Thread.activeCount()) + " Thread Activates.");
        JFrame screen2 = new JFrame("B&W Image");
        screen2.setSize(bef.getWidth(), bef.getHeight());
        screen2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Result BWI = new Result(outputImage);
        screen2.add(BWI);
        screen2.setVisible(true);
        screen2.setLocationRelativeTo(null);
        System.out.println("Success.");
        
    }
    
    private static int[] getPixelData(BufferedImage img, int x, int y) {
        int argb = img.getRGB(x, y);

        int rgb[] = new int[] {
        (argb >> 16) & 0xff, //red
        (argb >>  8) & 0xff, //green
        (argb      ) & 0xff  //blue
        };
        return rgb;
    }
    
}
