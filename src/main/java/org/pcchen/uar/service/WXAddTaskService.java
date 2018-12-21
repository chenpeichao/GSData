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
     * 根据公众号搜索公众号默认1条记录
     * @param wxId      公众号
     * @param start     起始位置
     * @param num       显示总记录数
     * @return
     */
    public Map<String, Object> searchKeywordByWXId(String wxId, Integer start, Integer num);

    /**
     * 根据公众号id，搜索公众号信息，并且保存到任务待执行表中
     *
     * @param wxId 微信公众号
     * @return
     */
    public Map<String, Object> fromSearchKeywordAndSaveTaskByWXId(String wxId);

    /**
     * 将本地数据库中的公众号添加到任务待执行表中
     *
     * @return
     */
    public Map<String, Object> fromLocalMysqlAndSaveTaskByWXId();

    /**
     * 对于任务表中微信的url信息为null的数据进行更新
     *
     * @return
     */
    public Map<String, Object> updateTaskTableURLBySearchAPI();

    /**
     * 获取到未添加到task表里面的公众号
     *
     * @return
     */
    public Map<String, Object> getExceptWxNoList();
}
