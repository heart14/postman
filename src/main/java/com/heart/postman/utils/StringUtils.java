package com.heart.postman.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

}
