package com.heart.postman.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.heart.postman.utils.HttpUtils;
import com.heart.postman.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName:PostManController
 * @Description:
 * @Author: Heart
 * @Date: 2019/6/18 16:48
 */
@Controller
@RequestMapping
public class PostManController {

    public static final Logger logger = LoggerFactory.getLogger(PostManController.class);

    /**
     * POSTMAN首页
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView postManPage() {
        return new ModelAndView("index");
    }

    /**
     * 发送HTTP POST application/json请求
     *
     * @param uri
     * @param param
     * @return
     */
    @RequestMapping(value = "/postMan", method = RequestMethod.POST)
    @ResponseBody
    public String postMan(String uri, String param, String method) {
        logger.info("HTTP REQUEST URI:{}", uri);
        logger.info("HTTP REQUEST PARAM:{}", param);
        logger.info("HTTP REQUEST METHOD:{}", method);
        JSONObject jsonObject = new JSONObject(1);
        if (StringUtils.isEmpty(uri) || StringUtils.isEmpty(method)) {
            jsonObject.put("error", "请正确输入参数！");
            return jsonObject.toJSONString();
        }
//        else {
//            if ("POST".equals(method)&&StringUtils.isEmpty(param)) {
//                JSONObject jsonObject = new JSONObject(1);
//                jsonObject.put("error", "请正确输入参数！");
//                return jsonObject.toJSONString();
//            }
//        }
        String data;
        try {
            String result = "";
            switch (method) {
                case "GET":
                    String url = StringUtils.buildGetURL(uri, param);
                    result = HttpUtils.doGet(url);
                    break;
                case "POST":
                    logger.info("POST.");
                    break;
                case "POST(json)":
                    result = HttpUtils.doPostJSON(uri, param);
                    break;
                default:
                    break;
            }
            if (!StringUtils.isEmpty(result)) {
                try {
                    JSONObject.parse(result);
                    JSONArray.parse(result);
                    data = result.replace("\\", "{").replace("\"{", "{").replace("}\"", "}").replace("\"[", "[").replace("]\"", "]");
                } catch (Exception e) {
                    logger.info("POSTMAN :响应数据非JSON格式!");
                    data = result;
                }
                logger.info("RESPONSE :{}", data);
            } else {
                jsonObject.put("error", "Nothing to show.");
                data = jsonObject.toJSONString();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            jsonObject.put("error", e.getMessage());
            data = jsonObject.toJSONString();
        }
        return data;
    }
}
