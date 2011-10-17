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
        g.drawImage(this.image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    public BufferedImage grayscale(){
        BufferedImage gi = new BufferedImage(this.image.getWidth(), this.image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        gi.getGraphics().drawImage(this.image, 0, 0, null);
        return gi;
    }

    public BufferedImage drawImage(Image image, int x, int y){
        this.image.getGraphics().drawImage(image, x, y, null);
        return this.image;
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
