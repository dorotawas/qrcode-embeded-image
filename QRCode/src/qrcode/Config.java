package qrcode;

public class Config {
    private static int MAX_TRIES = 1000; 
    private static int WIDTH = 400;
    private static int HEIGHT = 400;
    private static int DEVDIV = 12;
    
    private Config(){}

    public static int getDEVDIV() {
        return DEVDIV;
    }

    public static void setDEVDIV(int DEVDIV) {
        Config.DEVDIV = DEVDIV;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static void setHEIGHT(int HEIGHT) {
        Config.HEIGHT = HEIGHT;
    }

    public static int getMAX_TRIES() {
        return MAX_TRIES;
    }

    public static void setMAX_TRIES(int MAX_TRIES) {
        Config.MAX_TRIES = MAX_TRIES;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static void setWIDTH(int WIDTH) {
        Config.WIDTH = WIDTH;
    }
    
    
    
}
