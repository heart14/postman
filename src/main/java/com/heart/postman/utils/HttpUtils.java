package com.heart.postman.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Description:HTTP请求工具类
 * @Author: Heart
 * @Date: 2019/3/20 17:09
 */
public class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * Http get 请求
     * @param apiUrl
     * @return
     */
    public static String doGet(String apiUrl) {

        return null;
    }

    /**
     * Http post请求
     * param: key=value&key=value&...
     * @param apiUrl
     * @param params
     * @return
     */
    public static String doPost(String apiUrl, String params) {

        return null;
    }

    /**
     * Http post请求
     * Content-Type:application/json
     * @param apiUrl
     * @param params
     * @return
     */
    public static String doPostJSON(String apiUrl, String params) {

        StringBuffer respContent = new StringBuffer();
        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;

        try {
            //第一步：获取URL
            URL url = new URL(apiUrl);
            //第二步：使用URL获取URLConnection
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //第三步：设置相应属性
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            //httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(params.length()));

            //如果需要传输数据并接受返回数据，需要设置下面两项
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setConnectTimeout(10 * 1000);//连接超时10秒
            httpURLConnection.setReadTimeout(30 * 1000);//读取超时30秒
            //第四步：使用IO流将数据写出
            printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            printWriter.write(params);
            printWriter.flush();
            /*
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.writer(params.getBytes());
                outputStream.flush()
             */
            //第五步：获取响应报文
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                respContent.append(line);
            }
        } catch (Exception e) {
            logger.error("HTTP发送POST请求出现异常! {}", e.getMessage());
            String mmp = "{\"error\":\"请求目标服务器失败：" + e.getMessage() + "\"}";
            return mmp;
        } finally {
            //第六步：关闭IO流
            try {
                if (printWriter != null) {
                    printWriter.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                logger.error("关闭IO流异常 : {}", e.getMessage());
            }
        }
        logger.info("HTTP请求响应数据 : {}", respContent);
        return respContent.toString();
    }
}
