package org.pcchen.uar.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.pcchen.uar.service.WXAddTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 公众号任务
 *
 * @author cpc
 * @create 2018-12-19 16:26
 **/
@Controller
@RequestMapping(value = "/wxAddTask")
public class WXAddTaskController {
    private Logger logger = Logger.getLogger(WXAddTaskController.class);

    @Autowired
    private WXAddTaskService wxAddTaskService;

    /**
     * 根据公众号搜索公众号记录，并且添加到公众号待执行任务表中
     *
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/fromSearchKeywordAndSavetaskbywxid")
    public Map<String, Object> fromSearchKeywordAndSavetaskbywxid(HttpServletRequest request, HttpServletResponse response) {
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
            return wxAddTaskService.fromSearchKeywordAndSavetaskbywxid(wxId);
        } catch (Exception e) {
            logger.error("根据公众号搜索公众号记录，并且添加到公众号待执行任务表接口调用失败-发生未知错误", e);
            resultMap.put("code", 0);
            resultMap.put("message", "接口调用失败，请重试！！");
            return resultMap;
        }
    }
}
