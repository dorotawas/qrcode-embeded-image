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

    public MyImage contrast() {

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
                        colors[x][y][i] /=2;
                }
                else {
                    for(int i=0; i<3; i++) {
                        int dif = maxColor - colors[x][y][i];
                        dif /= 2;
                        colors[x][y][i] = maxColor - dif;
                    }
                }
                image.setRGB(x, y, (colors[x][y][0]) | (colors[x][y][1]<<8) | (colors[x][y][2]<<16));
            }
        }
        return this;
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
