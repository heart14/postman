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

    @RequestMapping(value = "/index")
    public ModelAndView postManPage() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/postMan",method = RequestMethod.POST)
    @ResponseBody
    public String postMan(String uri, String param) {
        String result = HttpUtils.doPostJSON(uri, param);
        logger.info("原始响应报文 :{}", result);
        String data = result.replace("\\", "{");
        data = data.replace("\"{", "{");
        data = data.replace("}\"", "}");
        data = data.replace("\"[", "[");
        data = data.replace("]\"", "]");
        logger.info("转义后报文 :{}", data);
        return data;
    }
}
