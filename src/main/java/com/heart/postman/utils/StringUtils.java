package com.heart.postman.utils;

import com.alibaba.fastjson.JSONObject;
import com.heart.postman.common.Constants;
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
     * 将json格式参数组装成key=value&key=value...形式
     *
     * @param param
     * @return
     */
    public static String buildParam(String param) throws Exception {

        StringBuilder buildParam = new StringBuilder();
        if (StringUtils.isEmpty(param)) {
            return Constants.EMPTY_STRING;
        }

        try {
            JSONObject jsonObject = JSONObject.parseObject(param);
            for (Map.Entry<String, Object> stringObjectEntry : jsonObject.entrySet()) {
                String key = stringObjectEntry.getKey();
                Object value = stringObjectEntry.getValue();
                buildParam.append("&");
                buildParam.append(key);
                buildParam.append("=");
                buildParam.append(value == null ? "" : value.toString());
            }
        } catch (Exception e) {
            logger.error("解析参数异常 :{}", e.getMessage());
            throw new Exception("JSON解析异常 :参数非JSON格式!");
        }
        return buildParam.toString().replaceFirst("&", "");
    }
}
