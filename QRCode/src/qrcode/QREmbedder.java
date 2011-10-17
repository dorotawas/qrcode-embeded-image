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
}
