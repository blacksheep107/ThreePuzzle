package ImagUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;

public class CutImage {
    //private static final long serialVersionUID = 1140239462766935667L;
    //private MediaTracker mediaTracker;
    public BufferedImage[][] images=new BufferedImage[3][3];//分割后的图像
    /**
     * 设置切割图片的参数
     */
    public void cutSetting(String filename) {
        //获取源图像
        Image image = Toolkit.getDefaultToolkit().getImage(filename);
        //new ImageIcon(image).getImage();
        // 分割图像
        images=CutImage.cutImage(ImageToBufferedImage.toBufferedImage(image));


        /*setSize(image.getWidth(null), image.getHeight(null));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // 创建媒体追踪器对象
        mediaTracker = new MediaTracker(this);

        int index = 0;
        // 将所有分割得到的图像添加到MediaTracker追踪列表中
        for (Image[] images2 : images) {
            for (Image image2 : images2) {
                mediaTracker.addImage(image2, index++);
            }
        }

        setVisible(true);*/

    }

    public static BufferedImage[][] cutImage(BufferedImage image){
        BufferedImage[][] images=new BufferedImage[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                images[i][j]=image.getSubimage(j*300,i*300,300,300);
            }
        }
        return images;
        /*for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ImageFilter filter = new CropImageFilter(x + j * changeX, y + i
                        * changeY, width, height);
                images[i][j] = component.createImage(new FilteredImageSource(
                        image.getSource(), filter));
            }
        }*/

    }
    /*@Override
    public void paint(Graphics g) {

        try {
            // 加载所有图像
            mediaTracker.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (mediaTracker.checkAll()) { // 所有图像加载完毕
            // 循环将每张分割的图像绘制到窗体中
            for (int row = 0, len = images.length; row < len; row++) {
                for (int col = 0, length = images[row].length; col < length; col++) {
                    Image img = images[row][col];
                    int imgWidth = img.getWidth(null);
                    int imgHeight = img.getHeight(null);
                    int x = col * (imgWidth+10)+15 ;
                    int y = row * (imgHeight+10)+15 ;
                    g.drawImage(img, x, y, null);
                }
            }
        }
    }*/
}
