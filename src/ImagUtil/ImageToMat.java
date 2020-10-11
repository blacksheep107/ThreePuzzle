package ImagUtil;

import Analysis.Base64Util;
import Analysis.JSONAnalysis;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageToMat extends Component {
    BufferedImage[][] problem_images=new BufferedImage[3][3];//问题九宫格
    Base64Util base=new Base64Util();
    CutImage cut1=new CutImage();
    /**
     * 找目标图片
     */
    public int[] findAnsImage() throws IOException {
        base.save_problemimage();//保存问题图片

        /*cut1.cutSetting("BlackandWhite.jpg");
        Image blackImage= cut1.images[0][1];
        Image whiteImage= cut1.images[0][0];*/
        cut1.cutSetting("problem.jpg");
        problem_images = cut1.images;//分割图片
        BufferedImage one_of_problem = problem_images[0][0];//取分割后图片的第一张小图
        /**
         * 处理黑白块
         */
        if(CmpImage.cmpimg(BaseImages.BLACKANDWHITE[0], one_of_problem)
                ||CmpImage.cmpimg(BaseImages.BLACKANDWHITE[1], one_of_problem)){
            one_of_problem=problem_images[0][1];
            if(CmpImage.cmpimg(BaseImages.BLACKANDWHITE[1], one_of_problem)
                    ||CmpImage.cmpimg(BaseImages.BLACKANDWHITE[0], one_of_problem)){
                one_of_problem=problem_images[0][2];
            }//可能出现前两张一张纯黑一张纯白
        }//出现黑块或白块，换成下一张小图

        /*if(cmpi.compare(cmpi.getData(itb.toBufferedImage(one_of_problem)), cmpi.getData(itb.toBufferedImage(blackImage)))>=98
                ||cmpi.compare(cmpi.getData(itb.toBufferedImage(one_of_problem)), cmpi.getData(itb.toBufferedImage(whiteImage)))>=98){
            one_of_problem=cut1.images[0][1];
        }
        if(cmpi.compare(cmpi.getData(itb.toBufferedImage(one_of_problem)), cmpi.getData(itb.toBufferedImage(whiteImage)))>=98||
                cmpi.compare(cmpi.getData(itb.toBufferedImage(one_of_problem)), cmpi.getData(itb.toBufferedImage(blackImage)))>=98){
            one_of_problem=cut1.images[0][2];
        }*/
        //找目标图片
        for(int i=0;i<36;i++){
            for(int j=0;j<3;j++){
                for(int k=0;k<3;k++){
                    if(CmpImage.cmpimg(one_of_problem, BaseImages.BASEIMAGE[i][j][k])){
                        System.out.println("NO:"+(i+1));
                        return toMat(BaseImages.BASEIMAGE[i]);
                    }
                }
            }
        }
        /*File file = new File("Imag");        //获取其file对象
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
                }
            }
        }*/
        System.out.println("No Found!");
        return null;
    }

    /**
     * 返回问题图片矩阵
     */
    public int[] toMat(BufferedImage[][] images) {//传入原图九宫格
        int[] mat = new int[10];
        //CmpImage cmpi = new CmpImage();
        //ImageToBufferedImage itb = new ImageToBufferedImage();
        // 9张问题图找原图编号
        int j = 1;
        for (BufferedImage[] images1 : problem_images) {//问题图
            for (BufferedImage image1 : images1) {
                int i = 1;//原图九宫格位置
                for (BufferedImage[] images2 : images) {//原图
                    for (BufferedImage image2 : images2) {
                        if(CmpImage.cmpimg(ImageToBufferedImage.toBufferedImage(image1),
                                ImageToBufferedImage.toBufferedImage(image2))){
                            mat[j]=i;
                        }
                        i++;
                    }
                }
                j++;
            }
        }
        return mat;
    }
}
