package Puzzle;
import Analysis.JSONAnalysis;
import Analysis.PostAnswer;
import ImagUtil.CmpImage;
import ImagUtil.ImageToMat;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Map;

public class LearnSth {
    public static void main(String[] args) throws IOException {
        PlayPuzzle p=new PlayPuzzle();
        int[] n={0,4,2,3,8,5,0,1,6,7};
        JSONAnalysis.step=9;

        p.playPuzzle(n);
    }
}