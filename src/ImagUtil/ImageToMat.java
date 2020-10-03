package ImagUtil;

import Analysis.Base64Util;
import Analysis.JSONAnalysis;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageToMat extends Component {
    Image[][] problem_images;//问题九宫格
    /**
     * 找目标图片
     */
    public int[] findAnsImage() throws IOException {
        Base64Util base=new Base64Util();
        base.save_problemimage();//保存问题图片

        String problem_image = "problem.jpg";
        CutImage cut1 = new CutImage();
        cut1.cutSetting("BlackandWhite.jpg");
        Image blackImage= cut1.images[0][1];
        Image whiteImage= cut1.images[0][0];

        cut1.cutSetting(problem_image);
        problem_images = cut1.images;//分割图片

        Image one_of_problem = cut1.images[0][0];//取分割后图片的第一张小图

        /**
         * 处理黑白块
         */

        if(CmpImage.cmpimg(ImageToBufferedImage.toBufferedImage(blackImage),ImageToBufferedImage.toBufferedImage(one_of_problem))
                ||CmpImage.cmpimg(ImageToBufferedImage.toBufferedImage(whiteImage),ImageToBufferedImage.toBufferedImage(one_of_problem))){
            one_of_problem=cut1.images[0][1];
        }//出现黑块或白块，换成下一张小图
        if(CmpImage.cmpimg(ImageToBufferedImage.toBufferedImage(whiteImage),ImageToBufferedImage.toBufferedImage(one_of_problem))
                ||CmpImage.cmpimg(ImageToBufferedImage.toBufferedImage(blackImage),ImageToBufferedImage.toBufferedImage(one_of_problem))){
            one_of_problem=cut1.images[0][2];
        }//可能出现前两张一张纯黑一张纯白
        /*if(cmpi.compare(cmpi.getData(itb.toBufferedImage(one_of_problem)), cmpi.getData(itb.toBufferedImage(blackImage)))>=98
                ||cmpi.compare(cmpi.getData(itb.toBufferedImage(one_of_problem)), cmpi.getData(itb.toBufferedImage(whiteImage)))>=98){
            one_of_problem=cut1.images[0][1];
        }
        if(cmpi.compare(cmpi.getData(itb.toBufferedImage(one_of_problem)), cmpi.getData(itb.toBufferedImage(whiteImage)))>=98||
                cmpi.compare(cmpi.getData(itb.toBufferedImage(one_of_problem)), cmpi.getData(itb.toBufferedImage(blackImage)))>=98){
            one_of_problem=cut1.images[0][2];
        }*/
        //找目标图片
        String path = "C:\\Users\\chenman\\Desktop\\Puzzle\\Imag";
        File file = new File(path);        //获取其file对象
        File[] fs = file.listFiles();    //遍历path下的文件和目录，放在File数组中
        String img;
        CutImage cut2 = new CutImage();

        for (File f : fs) {                    //遍历File[]数组
            img = f.toString();
            cut2.cutSetting(img);//分割图库中的图

            // 遍历所有分割得到的图像
            for (Image[] images2 : cut2.images) {
                for (Image image2 : images2) {
                    if(CmpImage.cmpimg(ImageToBufferedImage.toBufferedImage(one_of_problem),ImageToBufferedImage.toBufferedImage(image2))){
                        System.out.println(f);//问题图片的一张小图和图库中的一张小图对比，若一样就是找到
                        return toMat(cut2.images);
                    }
                    /*if (cmpi.compare(cmpi.getData(itb.toBufferedImage(one_of_problem)), cmpi.getData(itb.toBufferedImage(image2))) == 100) {
                        //找到
                        System.out.println(f);
                        return toMat(cut2.images);
                    }*/
                }
            }
        }
        return null;
    }

    /**
     * 返回问题图片矩阵
     */
    public int[] toMat(Image[][] images) {//传入原图九宫格
        int[] mat = new int[10];
        //CmpImage cmpi = new CmpImage();
        //ImageToBufferedImage itb = new ImageToBufferedImage();
        // 9张问题图找原图编号
        int j = 1;
        for (Image[] images1 : problem_images) {//问题图
            for (Image image1 : images1) {
                int i = 1;//原图九宫格位置
                for (Image[] images2 : images) {//原图
                    for (Image image2 : images2) {
                        if(CmpImage.cmpimg(ImageToBufferedImage.toBufferedImage(image1),ImageToBufferedImage.toBufferedImage(image2))){
                            mat[j]=i;
                        }
                        /*if (cmpi.compare(cmpi.getData(itb.toBufferedImage(image1)), cmpi.getData(itb.toBufferedImage(image2))) ==100) {
                            mat[j] = i;//找到对应相同块
                            //System.out.println(i+" "+j);
                        }*/
                        i++;
                    }
                }
                j++;
            }
        }
        return mat;
    }
}
