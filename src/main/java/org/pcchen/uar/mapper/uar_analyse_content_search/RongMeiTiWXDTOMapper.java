package org.pcchen.uar.mapper.uar_analyse_content_search;

import org.pcchen.uar.bean.uar_analyse_content_search.RongMeiTiWXDTO;

import java.util.List;

public interface RongMeiTiWXDTOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RongMeiTiWXDTO record);

    int insertSelective(RongMeiTiWXDTO record);

    RongMeiTiWXDTO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RongMeiTiWXDTO record);

    int updateByPrimaryKey(RongMeiTiWXDTO record);

    /**
     * 查询有效公众号的集合
     *
     * @return
     */
    public List<String> selectEffectWXNoList();
}