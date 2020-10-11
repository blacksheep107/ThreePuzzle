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
    /*public int[] getData(BufferedImage img) {
        //BufferedImage img = ImageIO.read(new File(name));
        BufferedImage slt = new BufferedImage(img.getWidth(null), img.getHeight(null),
                BufferedImage.TYPE_INT_RGB);
        slt.getGraphics().drawImage(img, 0, 0, img.getWidth(null), img.getHeight(null), null);
        int[] data = new int[256];
        for (int x = 0; x < slt.getWidth(); x++) {
            for (int y = 0; y < slt.getHeight(); y++) {
                int rgb = slt.getRGB(x, y);
                Color myColor = new Color(rgb);
                int r = myColor.getRed();
                int g = myColor.getGreen();
                int b = myColor.getBlue();
                data[(r + g + b) / 3]++;
            }
        }
        // data 就是所谓图形学当中的直方图的概念
        return data;
    }

    public float compare(int[] s, int[] t) {
        try {
            float result = 0F;
            for (int i = 0; i < 256; i++) {
                int abs = Math.abs(s[i] - t[i]);
                int max = Math.max(s[i], t[i]);
                result += (1 - ((float) abs / (max == 0 ? 1 : max)));
            }
            //System.out.println((result / 256) * 100);
            return (result / 256) * 100;
        } catch (Exception exception) {
            return 0;
        }
    }*/
}