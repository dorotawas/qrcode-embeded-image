package qrcode;
 
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class MyImage {
    private BufferedImage image;

    public MyImage(int width, int height, int imageType){
        this.image = new BufferedImage(width, height, imageType);
    }

    public MyImage(BufferedImage image){
        this.image = image;
    }

    public MyImage load(File file) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException ex) { 
        }
        return this;
    }
    
    public MyImage embedWithTransparency(BufferedImage logo, int x, int y, int originalWeight, int logoWeight) {
        int maxColor = (1<<8)-1;
        for(int w=0; w<logo.getWidth(); w++)
            for(int h=0; h<logo.getHeight(); h++) {
                int RGB = image.getRGB(x+w, y+h);
                int logoRGB = logo.getRGB(w, h);
                int[] colors = new int[3];
                for(int i=0; i<3; i++) {
                    colors[i] = (RGB & maxColor) * originalWeight + (logoRGB & maxColor) * logoWeight;
                    colors[i] = (int) ((double) colors[i] / (originalWeight + logoWeight));
                    RGB = RGB >> 8;
                    logoRGB = logoRGB >> 8;
                }
                int result = 0;
                for(int i=2; i>=0; i++) {
                    result = result << 8;
                    result += colors[i];
                }
                image.setRGB(x+w, y+h, result);
            }
        return this;
    }
    
    public MyImage contrast(double power) {

        int W = image.getWidth();
        int H = image.getHeight();
        int[][] smallerTab = new int[W][H];
        int[][] smallerTabNew = new int[W][H];
        int[][][] colors = new int[W][H][3];
        int maxColor = (1<<8)-1;
        //for each pixel, check whether we should make it lighter (smaller > 0) or darker
        for(int x=0; x<W; x++) {
            for(int y=0; y<H; y++) {
                int mid = maxColor/2;
                int smaller = 0;
                int RGB = image.getRGB(x, y);
                for(int i=0; i<3; i++) {
                    colors[x][y][i] = RGB & maxColor;
                    if(colors[x][y][i] < mid)
                        smaller++;
                    else
                        smaller--;
                    RGB = RGB >> 8;
                }
                smallerTab[x][y] = smaller;
            }
        }
        // for each pixel, update smallerTable, depending on neighbours
        for(int x=0; x<W; x++)
            for(int y=0; y<H; y++) {
                smallerTabNew[x][y] = 0;
                for(int dx=-1; dx<=1; dx++)
                    for(int dy=-1; dy<=1; dy++)
                        if(x+dx >= 0 && x+dx<W && y+dy >= 0 && y+dy <H)
                            smallerTabNew[x][y] += smallerTab[x+dx][y+dy];
            }
        smallerTab = smallerTabNew;
        // for each pixel, make it darker or lighter
        for(int x=0; x<W; x++) {
            for(int y=0; y<H; y++) {
                if(smallerTab[x][y] > 0) {
                    for(int i=0; i<3; i++)
                        colors[x][y][i] = (int)((double) colors[x][y][i] / power);
                }
                else {
                    for(int i=0; i<3; i++) {
                        int dif = maxColor - colors[x][y][i];
                        dif = (int) ((double) dif / power);
                        colors[x][y][i] = maxColor - dif;
                    }
                }
                image.setRGB(x, y, (colors[x][y][0]) | (colors[x][y][1]<<8) | (colors[x][y][2]<<16));
            }
        }
        return this;
    }
    public MyImage fitInto(int width, int height) {
        double divWidth = (double) width / image.getWidth();
        double divHeight = (double) height / image.getHeight();
        double min = Math.min(divHeight, divWidth);
        min = Math.min(min, 1);
        int newWidth = (int) Math.floor(image.getWidth()*min);
        int newHeight = (int) Math.floor(image.getHeight()*min);
        return this.resizeTo(newWidth, newHeight);
    }

    public MyImage resizeTo(int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(this.image, 0, 0, width, height, null);
        g.dispose();
        image = resizedImage;
        return this;
    }

    public MyImage grayscale(){
        BufferedImage gi = new BufferedImage(this.image.getWidth(), this.image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        gi.getGraphics().drawImage(this.image, 0, 0, null);
        image = gi;
        return this;
    }

    public MyImage drawImage(Image image, int x, int y){
        this.image.getGraphics().drawImage(image, x, y, null);
        return this;
    }

    public void save(File out){
        try {
            ImageIO.write(this.image, null, out);
        } catch (IOException ex) {}
    }

    public BufferedImage toImage(){
        return this.image;
    }
}
