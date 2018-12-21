package org.pcchen.uar.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.pcchen.uar.service.WXAddTaskService;
import org.pcchen.uar.utils.GSDataConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
     * 根据公众号搜索公众号默认1条记录
     *
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/searchKeywordByWXId")
    public Map<String, Object> searchKeywordByWXId(HttpServletRequest request, HttpServletResponse response) {
        // 解决跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");

        Map<String, Object> resultMap = new HashMap<String, Object>();

        String wxId = request.getParameter("wxId");
        String startStr = request.getParameter("start");
        String numStr = request.getParameter("num");

        if (StringUtils.isBlank(wxId)) {
            resultMap.put("code", 0);
            resultMap.put("message", "微信公众号不能为空");
            return resultMap;
        }

        try {
            Integer start = StringUtils.isNotBlank(startStr) ? Integer.parseInt(startStr.trim()) : GSDataConstants.GSDATA_SEARCH_KEYWORD_API_START;
            Integer num = StringUtils.isNotBlank(numStr) ? Integer.parseInt(numStr.trim()) : GSDataConstants.GSDATA_SEARCH_KEYWORD_API_NUM;
            return wxAddTaskService.searchKeywordByWXId(wxId.trim(), start, num);
        } catch (Exception e) {
            logger.error("根据公众号搜索公众号记录，并且添加到公众号待执行任务表接口调用失败-发生未知错误", e);
            resultMap.put("code", 0);
            resultMap.put("message", "接口调用失败，请重试！！");
            return resultMap;
        }
    }

    /**
     * 根据公众号搜索公众号记录，并且添加到公众号待执行任务表中
     *
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/fromSearchKeywordAndSaveTaskByWXId")
    public Map<String, Object> fromSearchKeywordAndSaveTaskByWXId(HttpServletRequest request, HttpServletResponse response) {
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
            return wxAddTaskService.fromSearchKeywordAndSaveTaskByWXId(wxId);
        } catch (Exception e) {
            logger.error("根据公众号搜索公众号记录，并且添加到公众号待执行任务表接口调用失败-发生未知错误", e);
            resultMap.put("code", 0);
            resultMap.put("message", "接口调用失败，请重试！！");
            return resultMap;
        }
    }

    /**
     * 根据公众号搜索公众号记录，并且添加到公众号待执行任务表中
     *
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/fromLocalMysqlAndSaveTaskByWXId")
    public Map<String, Object> fromLocalMysqlAndSaveTaskByWXId(HttpServletRequest request, HttpServletResponse response) {
        // 解决跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");

        Map<String, Object> resultMap = new HashMap<String, Object>();

        String isExecuteStr = request.getParameter("isExecute");

        if (StringUtils.isBlank(isExecuteStr)) {
            resultMap.put("code", 0);
            resultMap.put("message", "缺少必填参数isExecute");
            return resultMap;
        }

        boolean isExecute = Boolean.parseBoolean(isExecuteStr);

        try {
            JSONObject jsonObject = new JSONObject();
            if (isExecute) {
                return wxAddTaskService.fromLocalMysqlAndSaveTaskByWXId();
            } else {
                resultMap.put("code", 0);
                resultMap.put("data", jsonObject.toJSONString());
                return resultMap;
            }
        } catch (Exception e) {
            logger.error("根据公众号搜索公众号记录，并且添加到公众号待执行任务表接口调用失败-发生未知错误", e);
            resultMap.put("code", 0);
            resultMap.put("message", "接口调用失败，请重试！！");
            return resultMap;
        }
    }

    /**
     * 对于任务表中微信的url信息为null的数据进行更新
     *
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/updateTaskTableURLBySearchAPI")
    public Map<String, Object> updateTaskTableURLBySearchAPI(HttpServletRequest request, HttpServletResponse response) {
        // 解决跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");

        Map<String, Object> resultMap = new HashMap<String, Object>();

        String isExecuteStr = request.getParameter("isExecute");

        if (StringUtils.isBlank(isExecuteStr)) {
            resultMap.put("code", 0);
            resultMap.put("message", "缺少必填参数isExecute");
            return resultMap;
        }

        boolean isExecute = Boolean.parseBoolean(isExecuteStr);

        try {
            JSONObject jsonObject = new JSONObject();
            if (isExecute) {
                return wxAddTaskService.updateTaskTableURLBySearchAPI();
            } else {
                resultMap.put("code", 0);
                resultMap.put("data", jsonObject.toJSONString());
                return resultMap;
            }
        } catch (Exception e) {
            logger.error("根据公众号搜索公众号记录，并且添加到公众号待执行任务表接口调用失败-发生未知错误", e);
            resultMap.put("code", 0);
            resultMap.put("message", "接口调用失败，请重试！！");
            return resultMap;
        }
    }

    /**
     * 获取到未添加到task表里面的公众号
     *
     * @param request
     * @return
     */
    @ResponseBody
    @GetMapping("/getExceptWxNoList")
    public Map<String, Object> getExceptWxNoList(HttpServletRequest request, HttpServletResponse response) {
        // 解决跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");

        Map<String, Object> resultMap = new HashMap<String, Object>();

        String isExecuteStr = request.getParameter("isExecute");

        if (StringUtils.isBlank(isExecuteStr)) {
            resultMap.put("code", 0);
            resultMap.put("message", "缺少必填参数isExecute");
            return resultMap;
        }

        boolean isExecute = Boolean.parseBoolean(isExecuteStr);

        try {
            JSONObject jsonObject = new JSONObject();
            if (isExecute) {
                return wxAddTaskService.getExceptWxNoList();
            } else {
                resultMap.put("code", 0);
                resultMap.put("data", jsonObject.toJSONString());
                return resultMap;
            }
        } catch (Exception e) {
            logger.error("查找未添加到task任务表里面的公众号列表接口调用失败-发生未知错误", e);
            resultMap.put("code", 0);
            resultMap.put("message", "接口调用失败，请重试！！");
            return resultMap;
        }
    }
}
