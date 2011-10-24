package qrcode;

import java.util.Random;

public class Gaussian {
    
    private Random R = new Random();
    private int e,d;
    private int MAX = Config.getHEIGHT();
    
    public Gaussian(int exp, int dev, int max){
        e = exp; d = dev;
        MAX = max;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public int getMAX() {
        return MAX;
    }

    public void setMAX(int MAX) {
        this.MAX = MAX;
    }

    public int getE() {
        return e;
    }

    public void setE(int e) {
        this.e = e;
    }
    
    public int getNext(){
        return Math.min((int)Math.max(R.nextGaussian()*d + e, 0), MAX);
    }
}
