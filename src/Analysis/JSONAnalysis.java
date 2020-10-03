package Analysis;

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
        Map<String,Object> map = JSONObject.parseObject(jsonString, Map.class);
        img=map.get("img").toString();
        uuid=map.get("uuid").toString();
        step=Integer.parseInt(map.get("step").toString());
        String jsonStr = JSONObject.toJSONString(map.get("swap"));
        swap=JSONObject.parseArray(jsonStr, Integer.class);
        System.out.println("forceStep:"+step);
        System.out.println("forceSwap:"+swap.get(0)+" "+swap.get(1));
        System.out.println("uuid:"+uuid);
    }
}
