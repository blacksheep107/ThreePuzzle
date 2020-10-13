package Analysis;

import Puzzle.Competition;
import Puzzle.Main;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JSONAnalysis {
    public static String img;
    public static String uuid;
    public static int step;
    public static List<Integer> swap;

    public void jsonAnalysis() throws IOException {
        String jsonString=new URLAnalysis().urlAnalysis();//调用URLAnalysis，获得json字符串
        JSONObject map=JSONObject.parseObject(jsonString);
        img=map.getString("img");
        uuid=map.getString("uuid");
        step=Integer.parseInt(map.getString("step"));
        swap=JSONObject.parseArray(map.getString("swap"), Integer.class);
        System.out.println("forceStep:"+step);
        System.out.println("forceSwap:"+swap.get(0)+" "+swap.get(1));
    }
    public void jsonAnalysisInCompetiton() throws IOException {
        String jsonString= Competition.po.requestPuzzle();
        JSONObject s=JSONObject.parseObject(jsonString);
        JSONObject map=JSONObject.parseObject(String.valueOf(s.get("data")));
        img=map.getString("img");
        uuid=s.getString("uuid");
        step=map.getInteger("step");
        swap=JSONObject.parseArray(map.getString("swap"), Integer.class);
        System.out.println("forceStep:"+step);
        System.out.println("forceSwap:"+swap.get(0)+" "+swap.get(1));
    }
}
