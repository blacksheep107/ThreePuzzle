package ImagUtil;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CutImageTest extends JFrame {
    private static final long serialVersionUID = 1140239462766935667L;
    private MediaTracker mediaTracker;
    private Image[][] images;

    public CutImageTest() {
        BufferedImage image = ImageToBufferedImage.toBufferedImage(Toolkit.getDefaultToolkit().getImage("problem.jpg"));
        new ImageIcon(image).getImage();
        setSize(image.getWidth(null), image.getHeight(null));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // 创建媒体追踪器对象
        mediaTracker = new MediaTracker(this);
        // 获取源图像

        // 分割图像
        //images = CutImage.cutImage(image);
        int index = 0;
        // 将所有分割得到的图像添加到MediaTracker追踪列表中
        for (Image[] images2 : images) {
            for (Image image2 : images2) {
                mediaTracker.addImage(image2, index++);
            }
        }

        setVisible(true);
    }

    @Override
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
                    int x = col * (imgWidth + 10) + 15;
                    int y = row * (imgHeight + 15) + 40;
                    g.drawImage(img, x, y, null);
                }
            }
        }
    }

    public static void main(String[] args) {
        new CutImageTest();
    }
}