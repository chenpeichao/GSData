package org.pcchen.uar.service.impl;

import org.apache.log4j.Logger;
import org.pcchen.uar.mapper.uar_analyse_content_search.RongMeiTiWXDTOMapper;
import org.pcchen.uar.service.RongMeiTiWXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cpc
 * @create 2018-12-20 15:07
 **/
@Service
public class RongMeiTiWXServiceImpl implements RongMeiTiWXService {
    private Logger logger = Logger.getLogger(RongMeiTiWXServiceImpl.class);

    @Autowired
    private RongMeiTiWXDTOMapper rongMeiTiWXDTOMapper;

    /**
     * 查询有效的公众号集合
     *
     * @return
     */
    public List<String> selectEffectWXNoList() {
        return rongMeiTiWXDTOMapper.selectEffectWXNoList();
    }
}
