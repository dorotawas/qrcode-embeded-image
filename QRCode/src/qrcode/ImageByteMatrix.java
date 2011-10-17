package qrcode;

import com.google.zxing.qrcode.encoder.ByteMatrix;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageByteMatrix{
    ByteMatrix matrix;

    public ImageByteMatrix(ByteMatrix matrix){
        this.matrix = matrix;
    }

    public BufferedImage toImage(){
        BufferedImage base = new BufferedImage(matrix.getWidth(), matrix.getHeight(), BufferedImage.TYPE_USHORT_GRAY);
        for(int i = 0; i < matrix.getHeight(); i++)
            for (int j = 0; j < matrix.getWidth(); j++)
                base.setRGB(i, j, matrix.get(i, j) > 0 ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
        return base;
    }

    public BufferedImage toImage(int w, int h){
        return new MyImage(this.toImage()).resizeTo(w, h);
    }
}
