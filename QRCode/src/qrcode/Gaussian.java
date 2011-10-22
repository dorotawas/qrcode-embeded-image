package qrcode;

import java.util.Random;

public class Gaussian {
    
    private Random R = new Random();
    private int e,d;
    
    public Gaussian(int exp, int dev){
        e = exp; d = dev;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public int getE() {
        return e;
    }

    public void setE(int e) {
        this.e = e;
    }
    
    public int getNext(){
        return (int)(R.nextGaussian()*d + e);
    }
}
