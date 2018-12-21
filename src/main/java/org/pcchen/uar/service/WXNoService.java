package org.pcchen.uar.service;

import java.util.Map;

/**
 * 公众号信息
 *
 * @author cpc
 * @create 2018-12-21 9:48
 **/
public interface WXNoService {
    /**
     * 根据公众号获取清博中公众号相关信息
     *
     * @param wxId
     * @return
     */
    public Map<String, Object> getGSDataWXNoInfoByWXId(String wxId);
}
