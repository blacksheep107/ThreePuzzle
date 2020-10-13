import Analysis.JSONAnalysis;
import Analysis.PostAnswer;
import ImagUtil.BaseImages;
import ImagUtil.ImageToMat;
import Puzzle.PlayPuzzle;
import org.junit.Assert;

import java.io.IOException;

import static org.junit.Assert.*;

public class MainTest {
    @org.junit.Test
    public void requestTest() throws IOException {
        new BaseImages();
        JSONAnalysis js=new JSONAnalysis();
        ImageToMat im=new ImageToMat();
        PlayPuzzle pl=new PlayPuzzle();
        js.jsonAnalysis();//获得题目
        pl.playPuzzle(im.findAnsImage());//获得答案
        Assert.assertEquals("true",new PostAnswer().postAnswer().getString("score"));

    }
}