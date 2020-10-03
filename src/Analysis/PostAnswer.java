package Analysis;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import Puzzle.PlayPuzzle;
import com.alibaba.fastjson.JSONObject;

/**
 * POST答案
 */

public class PostAnswer {
    public static JSONObject postAnswer() throws IOException {
        String operations=PlayPuzzle.getop;
        String ans="{\"uuid\":\"";
        ans+=JSONAnalysis.uuid;
        ans+="\",\"answer\":{\"operations\": \"";
        ans+=operations;
        ans+="\",\"swap\": [";
        if(PlayPuzzle.best==null){
            ans+="0,0]}}";
        }else{
            PlayPuzzle.best[0]++;
            PlayPuzzle.best[1]++;
            ans+=PlayPuzzle.best[0];
            ans+=",";
            ans+=PlayPuzzle.best[1];
            ans+="]}}";
        }
        System.out.println();
        System.out.println(ans);
        //发送 POST 请求
        String sr = sendRequestManager("http://47.102.118.1:8089/api/answer",
                ans);
        JSONObject js=JSONObject.parseObject(sr);
        return js;
    }
    public static String sendRequestManager(String url, String body) throws IOException {

        StringBuilder sb = new StringBuilder();
        String result = "";
        BufferedWriter writer = null;
        BufferedReader bd = null;

        URL u = new URL(url);
        HttpURLConnection hc = (HttpURLConnection) u.openConnection();
        hc.setRequestMethod("POST");
        hc.setConnectTimeout(5000);
        hc.setReadTimeout(5000);

        hc.setRequestProperty("Content-Type", "application/Json; charset=UTF-8");

        hc.setDoInput(true);
        hc.setDoOutput(true);

        try {

            writer = new BufferedWriter(new OutputStreamWriter(hc.getOutputStream(), "UTF-8"));
            writer.write(body);
            writer.close();

            if (hc.getResponseCode() == 200){
                bd = new BufferedReader(new InputStreamReader(hc.getInputStream(), "utf-8"));
                String s = null;
                while ((s = bd.readLine()) != null) {
                    sb.append(s);
                }
                bd.close();
            }else if (hc.getResponseCode() == 301 || hc.getResponseCode() == 302) {
                // 得到重定向的地址
                String location = hc.getHeaderField("Location");
                URL u1 = new URL(location);
                HttpURLConnection hc1 = (HttpURLConnection) u1.openConnection();
                hc1.setRequestMethod("POST");
                hc1.setConnectTimeout(5000);
                hc1.setReadTimeout(5000);
                if (hc1.getResponseCode() == 200) {
                    bd = new BufferedReader(new InputStreamReader(hc1.getInputStream(), "utf-8"));
                    String s = null;
                    while ((s = bd.readLine()) != null) {
                        sb.append(s);
                    }
                    bd.close();
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (bd != null) {
                bd.close();
            }
            if (writer != null) {

                writer.close();
            }
        }

        result = sb.toString();
        System.out.println(result);
        return result;
    }

    /**
     * 不能用
     * @param url
     * @param param
     * @return
     * @throws IOException
     */
    /*public static String sendPost(String url, String param) throws IOException {
        URL realUrl = new URL(url);
        // 打开和URL之间的连接
        URLConnection conn = realUrl.openConnection();
        // 发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        //conn.setDoInput(true);
        //conn.setRequestProperty("connection", "Keep-Alive");
        //conn.setRequestProperty("charsert", "utf-8");
        conn.setRequestProperty("Content-Type", "application/json");
        //conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        BufferedReader in = null;
        String result = "";

        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
        out.write(param);
        // flush输出流的缓冲
        out.flush();
        // 定义BufferedReader输入流来读取URL的响应
        in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        return result;
    }*/
}