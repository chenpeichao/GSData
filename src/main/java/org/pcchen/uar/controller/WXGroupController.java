package org.pcchen.uar.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.pcchen.uar.service.WXGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 清博组相关操作
 *
 * @author cpc
 * @create 2018-12-20 10:16
 **/
@Controller
@RequestMapping(value = "/wxGroup")
public class WXGroupController {
    private Logger logger = Logger.getLogger(WXGroupController.class);

    @Autowired
    private WXGroupService wxGroupService;


    /**
     * 获取应用的所有分组信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @GetMapping("/getAllGroup")
    public Map<String, Object> getAllGroup(HttpServletRequest request, HttpServletResponse response) {
        // 解决跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {
            return wxGroupService.getAllGroup();
        } catch (Exception e) {
            logger.error("获取应用的所有分组信息接口调用失败-发生未知错误", e);
            resultMap.put("code", 0);
            resultMap.put("message", "接口调用失败，请重试！！");
            return resultMap;
        }
    }

    /**
     * 根据文章url将信公众账号添加到分组并返回公众号相关信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/addWXToGroupByurlAndReturnWXInfo")
    public Map<String, Object> addWXToGroupByurlAndReturnWXInfo(HttpServletRequest request, HttpServletResponse response) {
        // 解决跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");

        Map<String, Object> resultMap = new HashMap<String, Object>();

        String groupId = request.getParameter("groupId");
        String url = request.getParameter("url");

        if (StringUtils.isBlank(groupId) || StringUtils.isBlank(url)) {
            resultMap.put("code", 0);
            resultMap.put("message", "必填参数未传递");
            return resultMap;
        }

        try {
            return wxGroupService.addWXToGroupByurlAndReturnWXInfo(groupId, url);
        } catch (Exception e) {
            logger.error("根据文章url将信公众账号添加到分组并返回公众号相关信息接口调用失败-发生未知错误", e);
            resultMap.put("code", 0);
            resultMap.put("message", "接口调用失败，请重试！！");
            return resultMap;
        }
    }
}
