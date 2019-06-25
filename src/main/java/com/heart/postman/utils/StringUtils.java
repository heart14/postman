package com.heart.postman.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Description:字符串工具类
 * @Author: Heart
 * @Date: 2019/3/20 17:29
 */
public class StringUtils {

    private static final Logger logger = LoggerFactory.getLogger(StringUtils.class);

    /**
     * 判断字符串是否为null或者为空字符串
     *
     * @param string
     * @return
     */
    public static boolean isEmpty(String string) {
        if (string == null || string.trim().length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * HTTP GET方法时将参数组装到URL
     *
     * @param param
     * @return
     */
    public static String buildGetURL(String uri, String param) {

        StringBuilder url = new StringBuilder(uri);
        if (isEmpty(param)) {
            return url.toString();
        }
        JSONObject jsonObject = JSONObject.parseObject(param);
        for (Map.Entry<String, Object> stringObjectEntry : jsonObject.entrySet()) {
            String key = stringObjectEntry.getKey();
            String value = (String) stringObjectEntry.getValue();
            url.append("&");
            url.append(key);
            url.append("=");
            url.append(value);
        }
        return url.toString().replace("&", "?");
    }
}
