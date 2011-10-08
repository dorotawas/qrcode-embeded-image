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
    public ProcessRequest(String imgPath, String URL) throws IOException {
        this.imgPath = imgPath;
        this.URL = URL;
        image = ImageIO.read(new File(imgPath));
        
    }
    @Override
    public void run() {
        ShowResultJFrame jFrame = new ShowResultJFrame();
        jFrame.setImage(image);
        jFrame.setVisible(true);
    }

}
