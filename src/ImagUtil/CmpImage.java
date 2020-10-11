package ImagUtil;


import java.awt.image.*;
import java.awt.*;

public class CmpImage {
    /**
     * 对比两张图片是否相同
     * @param img
     * @param img2
     * @return boolean
     */
    public static boolean cmpimg(BufferedImage img,BufferedImage img2){
        BufferedImage slt = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
        BufferedImage slt2 = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
        slt.getGraphics().drawImage(img, 0, 0, 300, 300, null);
        slt2.getGraphics().drawImage(img2, 0, 0, 300, 300, null);
        for (int x = 0; x < 300; x++) {
            for (int y = 0; y < 300; y++) {
                if(slt.getRGB(x,y)!=slt2.getRGB(x,y))   return false;//有一个像素不同就返回
            }
        }
        return true;
    }
}