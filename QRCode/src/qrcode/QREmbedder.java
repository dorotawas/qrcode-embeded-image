package qrcode;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.image.BufferedImage;
import com.google.zxing.qrcode.encoder.*;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.Random;

public class QREmbedder {
    public static BufferedImage generate(String url, int w, int h){
        QRCode code = new QRCode();
        try {
            Encoder.encode(url, ErrorCorrectionLevel.H, code);
            return new ImageByteMatrix(code.getMatrix()).toImage(w, h);
        } catch (Exception ex){ 
            ex.printStackTrace();
            return null;
        }
    }

    public static boolean check(BufferedImage image, String url){
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        try{
            Result res = new QRCodeReader().decode(bitmap);
            return res.getText().equals(url);
        } catch (Exception ex){ return false; }
    }
    
    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
    
    public static BufferedImage tryEmbed(BufferedImage source, BufferedImage logo, int x, int y) {
        BufferedImage copy = deepCopy(source);
        //return logoMin;
        return new MyImage(copy).drawImage(logo, x, y).toImage();
    }
    
    public static BufferedImage embed(String url, BufferedImage logo){
        BufferedImage QR = new MyImage(new BufferedImage(Config.getWIDTH(), Config.getHEIGHT(), BufferedImage.TYPE_INT_RGB)).
                                drawImage(generate(url, Config.getWIDTH(), Config.getHEIGHT()), 0, 0).toImage();
        Gaussian gw = new Gaussian(0, Config.getWIDTH()/Config.getDEVDIV());
        Gaussian gh = new Gaussian(0, Config.getHEIGHT()/Config.getDEVDIV());
        int w = QR.getWidth();
        int h = QR.getHeight();
        for (int j = 3; j < 10; j++){
            BufferedImage logoMin = new MyImage(logo).contrast(2).fitInto(2*w/j, 2*h/j).toImage();
            gw.setE(w/2 - w/j);
            gh.setE(h/2 - h/j);
            for (int i = 0; i < Config.getMAX_TRIES(); i++){                
                BufferedImage e = tryEmbed(QR, logoMin, gw.getNext(), gh.getNext());
                System.out.println(i);
                if (check(e, url)) return e;             
            }
        }
        return QR;
    }
}
