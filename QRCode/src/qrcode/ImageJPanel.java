/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qrcode;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author dorota
 */
public class ImageJPanel extends JPanel{
    
    BufferedImage image;
    public ImageJPanel(BufferedImage image) {
        this.image = image;
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 0, 0, this);
    }

}
