package org.pcchen.uar.service;

import java.util.Map;

/**
 * 公众号任务
 *
 * @author cpc
 * @create 2018-12-19 16:32
 **/
public interface WXAddTaskService {
    /**
     * 根据公众号id，搜索公众号信息，并且保存到任务待执行表中
     *
     * @param wxId 微信公众号
     * @return
     */
    public Map<String, Object> fromSearchKeywordAndSavetaskbywxid(String wxId);
}
