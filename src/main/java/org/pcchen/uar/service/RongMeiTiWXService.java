package org.pcchen.uar.service;

import java.util.List;

/**
 * 数据库中需要保存到任务表中的公众号元数据数据表
 *
 * @author cpc
 * @create 2018-12-20 15:06
 **/
public interface RongMeiTiWXService {
    /**
     * 查询有效的公众号集合
     *
     * @return
     */
    public List<String> selectEffectWXNoList();
}
