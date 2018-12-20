package org.pcchen.uar.mapper.local_pdmi;

import org.pcchen.uar.bean.local_pdmi.PubAccountAddTaskDTO;

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
    PubAccountAddTaskDTO selectByWXId(String wxId);
}