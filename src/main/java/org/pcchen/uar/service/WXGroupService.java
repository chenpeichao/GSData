package org.pcchen.uar.service;

import java.util.Map;

/**
 * 清博组相关操作
 *
 * @author cpc
 * @create 2018-12-20 10:20
 **/
public interface WXGroupService {
    /**
     * 根据文章url将信公众账号添加到分组并返回公众号相关信息
     *
     * @param groupId 清博中组id
     * @param url     要保存的微信公众号的文章url
     * @return
     */
    public Map<String, Object> addWXToGroupByurlAndReturnWXInfo(String groupId, String url);

    /**
     * 获取应用的所有分组信息
     *
     * @return
     */
    public Map<String, Object> getAllGroup();
}
