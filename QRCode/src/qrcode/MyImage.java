package qrcode;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class MyImage extends BufferedImage {

    public MyImage(int width, int height, int imageType){
        super(width, height, imageType);
    }

    public static BufferedImage load(File file) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException ex) { 
        }
        return image;
    }

    public BufferedImage resizeTo(int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(this, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    public BufferedImage grayscale(){
        BufferedImage gi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        gi.getGraphics().drawImage(this, 0, 0, null);
        return gi;
    }

    public BufferedImage drawImage(Image image, int x, int y){
        this.getGraphics().drawImage(image, x, y, null);
        return this;
    }

    public void save(File out){
        try {
            ImageIO.write(this, null, out);
        } catch (IOException ex) {}
    }
}
