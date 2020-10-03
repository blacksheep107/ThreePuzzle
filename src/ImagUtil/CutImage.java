package ImagUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;

public class CutImage extends JFrame {
    //private static final long serialVersionUID = 1140239462766935667L;
    //private MediaTracker mediaTracker;
    public Image[][] images;//分割后的图像
    /**
     * 设置切割图片的参数
     */
    public void cutSetting(String filename){
        //获取源图像
        Image image = Toolkit.getDefaultToolkit().getImage(filename);
        new ImageIcon(image).getImage();
        // 分割图像
        images = CutImage.cutImage(image, 3, 3, 0, 0,
                image.getWidth(null)/3,
                image.getHeight(null)/3,
                image.getWidth(null)/3,
                image.getHeight(null)/3, this);


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
    /**
     * 分割图像
     *
     * @param image
     *            传入的图片对象
     * @param rows
     *            垂直方向上需要裁剪出的图片数量 - 行
     * @param cols
     *            水平方向上需要裁剪出的图片数量 - 列
     * @param x
     *            开始裁剪位置的X坐标
     * @param y
     *            开始裁剪位置的Y坐标
     * @param width
     *            每次裁剪的图片宽度
     * @param height
     *            每次裁剪的图片高度
     * @param changeX
     *            每次需要改变的X坐标数量
     * @param changeY
     *            每次需要改变的Y坐标数量
     * @param component
     *            容器对象，目的是用来创建裁剪后的每个图片对象
     * @return 裁剪完并加载到内存后的二维图片数组
     */
    public static Image[][] cutImage(Image image, int rows, int cols, int x,
                                     int y, int width, int height, int changeX, int changeY,
                                     Component component) {
        Image[][] images = new Image[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ImageFilter filter = new CropImageFilter(x + j * changeX, y + i
                        * changeY, width, height);
                images[i][j] = component.createImage(new FilteredImageSource(
                        image.getSource(), filter));
            }
        }

        return images;
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
