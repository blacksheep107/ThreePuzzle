package Puzzle;
import Analysis.JSONAnalysis;
import Analysis.PostAnswer;
import Analysis.URLAnalysis;
import ImagUtil.CmpImage;
import ImagUtil.ImageToMat;
import com.alibaba.fastjson.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LearnSth {
    public static void main(String[] args) throws IOException {
        int[] mat={0,9,6,7,2,5,4,0,1,3};
        JSONAnalysis.step=20;
        JSONAnalysis.swap=new ArrayList<>();
        JSONAnalysis.swap.add(1);
        JSONAnalysis.swap.add(2);
        new PlayPuzzle().playPuzzle(mat);
    }
}