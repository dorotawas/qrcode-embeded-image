/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qrcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 *
 * @author dorota
 */
public class ProcessRequest extends Thread{
    String imgPath;
    String URL;
    BufferedImage image;
    int contrast;
    int opacity;
    public ProcessRequest(String imgPath, String URL, int contrast, int opacity) throws IOException {
        this.imgPath = imgPath;
        this.URL = URL;
        this.contrast = contrast;
        this.opacity = opacity;
        image = ImageIO.read(new File(imgPath));        
        System.out.println(contrast + " " + opacity);
    }
    @Override
    public void run() {
        ShowResultJFrame jFrame = new ShowResultJFrame();        
        jFrame.setImage(QREmbedder.embed(URL, image, contrast, opacity));
        jFrame.setVisible(true);
    }

}
