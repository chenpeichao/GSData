package org.pcchen.uar.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.pcchen.uar.service.WXNoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 公众号相关信息
 *
 * @author cpc
 * @create 2018-12-21 9:44
 **/
@Controller
@RequestMapping(value = "/wxNo")
public class WXNoController {
    private Logger logger = Logger.getLogger(WXNoController.class);


    @Autowired
    private WXNoService wxNoService;

    /**
     * 根据公众号获取清博中公众号相关信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/getGSDataWXNoInfoByWXId")
    public Map<String, Object> getGSDataWXNoInfoByWXId(HttpServletRequest request, HttpServletResponse response) {
        // 解决跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");

        Map<String, Object> resultMap = new HashMap<String, Object>();

        String wxId = request.getParameter("wxId");

        if (StringUtils.isBlank(wxId)) {
            resultMap.put("code", 0);
            resultMap.put("message", "微信公众号不能为空");
            return resultMap;
        }

        try {
            return wxNoService.getGSDataWXNoInfoByWXId(wxId);
        } catch (Exception e) {
            logger.error("根据公众号搜索公众号记录，并且添加到公众号待执行任务表接口调用失败-发生未知错误", e);
            resultMap.put("code", 0);
            resultMap.put("message", "接口调用失败，请重试！！");
            return resultMap;
        }
    }
}
