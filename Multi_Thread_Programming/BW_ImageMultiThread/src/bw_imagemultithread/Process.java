package bw_imagemultithread;
public class Process extends BW_ImageMultiThread implements Runnable {
    
    private int row;
    private int t;
    
    public void Identify(int row, int t) {
        this.row = row; this.t = t;
    }

    @Override
    public void run() {
        int err;
        int xrow = row + t;
        for(int j = 0; j < bef.getWidth(); j++) {
            if(pixelData[xrow][j] < 128) outputImage[xrow][j] = 0;
            else if(pixelData[xrow][j] >= 128) outputImage[xrow][j] = 1;
            err = pixelData[xrow][j] - (255 * outputImage[xrow][j]);
            if(j != (bef.getWidth() - 1)) pixelData[xrow][j+1] += (err * 7) / 16;
            if(j != 0 && xrow != (bef.getHeight() - 1)) pixelData[xrow+1][j-1] += (err * 3) / 16;
            if(xrow != bef.getHeight() - 1) pixelData[xrow+1][j] += (err * 5) / 16;
            if(j != (bef.getWidth() - 1) && xrow != (bef.getHeight() - 1))pixelData[xrow+1][j+1] += err / 16;
        }
    }
    
}
