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
    private static BufferedImage slt = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
    private static BufferedImage slt2 = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);

    public static boolean cmpimg(BufferedImage img,BufferedImage img2){
        slt.getGraphics().drawImage(img, 0, 0, 300, 300, null);
        slt2.getGraphics().drawImage(img2, 0, 0, 300, 300, null);
        for (int x = 0; x < 300; x+=5) {
            for (int y = 0; y < 300; y+=5) {
                if(slt.getRGB(x,y)!=slt2.getRGB(x,y))   return false;//有一个像素不同就返回
            }
        }
        return true;
    }
}