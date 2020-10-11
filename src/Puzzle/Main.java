package Puzzle;

import Analysis.JSONAnalysis;
import Analysis.PostAnswer;
import ImagUtil.BaseImages;
import ImagUtil.ImageToMat;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        int cnt=0;
        JSONAnalysis js=new JSONAnalysis();
        ImageToMat im=new ImageToMat();
        for(int i=0;i<1;i++){
            cnt++;
            js.jsonAnalysis();//获得题目
            int[] mat=im.findAnsImage();//得到问题矩阵
            new PlayPuzzle().playPuzzle(mat);//获得答案
            PostAnswer postans=new PostAnswer();
            if(postans.postAnswer().getString("score")=="false"){
                System.out.println("Fail Game:"+cnt);
                break;
            }
            Thread.sleep(500);
        }
    }
}