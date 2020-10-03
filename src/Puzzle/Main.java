package Puzzle;

import Analysis.JSONAnalysis;
import Analysis.PostAnswer;
import ImagUtil.ImageToMat;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        int cnt=0;
        for(int i=0;i<100;i++){
            cnt++;
            JSONAnalysis js=new JSONAnalysis();
            js.jsonAnalysis();//获得题目

            ImageToMat itm=new ImageToMat();
            int[] mat=itm.findAnsImage();//得到问题矩阵

            PlayPuzzle p=new PlayPuzzle();
            p.playPuzzle(mat);//获得答案

            PostAnswer postans=new PostAnswer();

            if(postans.postAnswer().getString("score")=="false"){
                System.out.println("Fail Game:"+cnt);
                break;
            }
        }
    }
}