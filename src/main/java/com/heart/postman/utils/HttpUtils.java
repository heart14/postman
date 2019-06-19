package com.heart.postman.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
     *
     * @param apiUrl
     * @return
     */
    public static String doGet(String apiUrl) {

        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        //返回结果字符串
        String result = null;
        try {
            // 创建远程url连接对象
            URL url = new URL(apiUrl);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                // 封装输入流is，并指定字符集
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // 存放数据
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // 关闭远程连接
            connection.disconnect();
        }
        return result;
    }

    /**
     * Http post请求
     * param: key=value&key=value&...
     * Content: application/x-www-form-urlencoded
     *
     * @param apiUrl
     * @param params
     * @return
     */
    public static String doPost(String apiUrl, String params) {

        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        String result = null;
        try {
            URL url = new URL(apiUrl);
            // 通过远程url连接对象打开连接
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接请求方式
            connection.setRequestMethod("POST");
            // 设置连接主机服务器超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取主机服务器返回数据超时时间：60000毫秒
            connection.setReadTimeout(60000);

            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
            connection.setDoOutput(true);
            // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
            connection.setDoInput(true);
            // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
            connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 通过连接对象获取一个输出流
            os = connection.getOutputStream();
            // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
            os.write(params.getBytes());
            // 通过连接对象获取一个输入流，向远程读取
            if (connection.getResponseCode() == 200) {

                is = connection.getInputStream();
                // 对输入流对象进行包装:charset根据工作项目组的要求来设置
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                StringBuffer sbf = new StringBuffer();
                String temp = null;
                // 循环遍历一行一行读取数据
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 断开与远程地址url的连接
            connection.disconnect();
        }
        return result;
    }

    /**
     * Http post请求
     * param: {"key":"value",...}
     * Content-Type: application/json
     *
     * @param apiUrl
     * @param params
     * @return
     */
    public static String doPostJSON(String apiUrl, String params) {

        logger.info("HTTP POST URL :{}", apiUrl);
        logger.info("HTTP POST PARAM :{}", params);

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
            //连接超时10秒
            httpURLConnection.setConnectTimeout(10 * 1000);
            //读取超时30秒
            httpURLConnection.setReadTimeout(30 * 1000);
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
            logger.error("HTTP POST ERROR! {}", e.getMessage());
            return "{\"error\":\"HTTP POST ERROR!请求目标服务器失败：" + e.getMessage() + "\"}";
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
        logger.info("HTTP POST RESPONSE :{}", respContent);
        return respContent.toString();
    }
}
