package ImagUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 装入图片
 */
public class BaseImages {
    public static BufferedImage[] BLACKANDWHITE;//0是白，1是黑
    public static BufferedImage[] [][]BASEIMAGE;
    static{
        BASEIMAGE= new BufferedImage[36][3][3];
        BLACKANDWHITE= new BufferedImage[2];
        int cnt=0;
        File file = new File("Imag");        //获取其file对象
        File[] fs = file.listFiles();    //遍历path下的文件和目录，放在File数组中
        String img;
        CutImage cut = new CutImage();
        /**
         * 加载所有图片
         */
        for (File f : fs) {
            img = f.toString();
            cut.cutSetting(img);//分割图库中的图
            BASEIMAGE[cnt++]=cut.images;
        }
        /**
         * 加载黑白
         */
        cut.cutSetting("BlackandWhite.jpg");
        BLACKANDWHITE[0]=cut.images[0][0];
        BLACKANDWHITE[1]=cut.images[0][1];
    }
}
