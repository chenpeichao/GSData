package org.pcchen.uar.mapper.local_pdmi;

import org.pcchen.uar.bean.local_pdmi.PubAccountAddTaskDTO;

import java.util.List;
import java.util.Map;

public interface PubAccountAddTaskDTOMapper {
    int deleteByPrimaryKey(Integer taskId);

    int insert(PubAccountAddTaskDTO record);

    int insertSelective(PubAccountAddTaskDTO record);

    PubAccountAddTaskDTO selectByPrimaryKey(Integer taskId);

    int updateByPrimaryKeySelective(PubAccountAddTaskDTO record);

    int updateByPrimaryKey(PubAccountAddTaskDTO record);

    /**
     * 根据微信公众号查询记录
     *
     * @param wxId
     * @return
     */
    public PubAccountAddTaskDTO selectByWXId(String wxId);

    /**
     * 查询url为null的微信公众号
     *
     * @return
     */
    public List<String> selectWXIdListByUrlIsNull();

    /**
     * 根据wxId对于任务表中微信的url信息为null的数据进行更新
     *
     * @param wxId  微信公众号
     * @param wxUrl 公众号文章url
     */
    public void updateUrlByWXId(Map<String, String> params);

    /**
     * 获取所有的公众号集合
     *
     * @return
     */
    public List<String> selectAllWxIdList();
}