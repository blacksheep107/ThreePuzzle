package Analysis;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLAnalysis {
    private String stuid="http://47.102.118.1:8089/api/problem?stuid=041803101";
    /**
     * 解析url，转为json字符串
     * @return
     * @throws IOException
     */
    public String urlAnalysis() throws IOException {
        String result="";
        URL url=new URL(stuid);
        HttpURLConnection httpConn=(HttpURLConnection) url.openConnection();
        httpConn.setConnectTimeout(3000);
        httpConn.setDoInput(true);
        httpConn.setRequestMethod("GET");
        int code = httpConn.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = httpConn.getInputStream();
            // 创建字节数组输出流，用来输出读取到的内容
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            // 创建缓存大小
            byte[] buffer = new byte[1024];
            int len = -1;// 设置读取的长度
            while ((len = inputStream.read(buffer)) != -1) {// 当输入流中没有数据时，返回-1
                outputStream.write(buffer, 0, len);
            }
            // 将数组转成字符串
            result = outputStream.toString();
            //System.out.println(result);
            inputStream.close();
            outputStream.close();
        }
        return result;
    }
}
