package Puzzle;

import Analysis.JSONAnalysis;
import Analysis.PostAnswer;
import ImagUtil.BaseImages;
import ImagUtil.ImageToMat;

import java.io.IOException;

public class Competition {
    public static PostAnswer po=new PostAnswer();
    public static void main(String[] args) throws IOException {
        new BaseImages();
        JSONAnalysis js=new JSONAnalysis();
        ImageToMat im=new ImageToMat();
        PlayPuzzle pl=new PlayPuzzle();


        js.jsonAnalysisInCompetiton();//获得题目
        pl.playPuzzle(im.findAnsImage());//获得答案
        po.postAnswerInCompetion();

    }
}
