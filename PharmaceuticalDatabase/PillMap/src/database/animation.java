package database;

import java.awt.image.BufferedImage;

public class animation {
    int frame,max,width,height;
    double current,speed;
    BufferedImage[] img;
    public animation(BufferedImage img,int max){
        current=0;
        this.img = new BufferedImage[max];
        this.max = max;
        speed = 0.2;
        width = img.getWidth()/max;
        height = img.getHeight();
        for(int index = 0;index<max;index++)
            this.img[index] = img.getSubimage(index*width, 0, width, height);
    }
    public void setSpeed(double input){
        speed = input;
    }
    public BufferedImage getImg(){
        current+=speed;
        return img[(int)current%max];
    }
    public BufferedImage getImgMax(){
        current = current<max-1? current+=speed:max-1;
        return img[(int)current%max];
    }
    public BufferedImage getImgMin(){
        current = current<0? 0:current-speed;
        return img[(int)current%max];
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public void reset(){
        current = 0;
    }
    public void set(int in){
        current = in;
    }
    public int getCurrent(){
        return (int)current;
    }
    public int getMax(){
        return max;
    }
}
