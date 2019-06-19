package com.heart.postman.controller;

import com.heart.postman.utils.HttpUtils;
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
    public String postMan(String uri, String param) {
        String result = HttpUtils.doPostJSON(uri, param);
        String data = result.replace("\\", "{");
        data = data.replace("\"{", "{");
        data = data.replace("}\"", "}");
        data = data.replace("\"[", "[");
        data = data.replace("]\"", "]");
        logger.info("RESPONSE :{}", data);
        return data;
    }
}
