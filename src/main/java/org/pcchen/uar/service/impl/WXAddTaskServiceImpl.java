package org.pcchen.uar.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.pcchen.uar.bean.local_pdmi.PubAccountAddTaskDTO;
import org.pcchen.uar.gsdata.SearchKeyword;
import org.pcchen.uar.mapper.local_pdmi.PubAccountAddTaskDTOMapper;
import org.pcchen.uar.service.WXAddTaskService;
import org.pcchen.uar.utils.GSDataConstants;
import org.pcchen.uar.utils.GSDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 公众号任务
 *
 * @author cpc
 * @create 2018-12-19 16:34
 **/
@Service
public class WXAddTaskServiceImpl implements WXAddTaskService {
    private Logger logger = Logger.getLogger(WXAddTaskServiceImpl.class);

    @Autowired
    private PubAccountAddTaskDTOMapper pubAccountAddTaskDTOMapper;

    /**
     * 根据公众号id，搜索公众号信息，并且保存到任务待执行表中
     *
     * @param wxId 微信公众号
     * @return
     */
    public Map<String, Object> fromSearchKeywordAndSavetaskbywxid(String wxId) {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        // 1、根据公众号查询公众号信息(得到公众号名称、最新一篇文章的url)
        String nicknameByKeywordSearch = SearchKeyword.getNicknameByKeywordSearch(wxId, GSDataConstants.GSDATA_SEARCH_KEYWORD_API_START, GSDataConstants.GSDATA_SEARCH_KEYWORD_API_NUM);

        logger.info("根据公众号查询返回字符串为：" + nicknameByKeywordSearch);

        JSONObject returnDataJsonObject = GSDataUtils.extractDataJson(nicknameByKeywordSearch);
        if (returnDataJsonObject != null) {
            JSONArray items = returnDataJsonObject.getJSONArray("items");
            if (null != items && items.size() == 1) {
                for (int i = 0; i != items.size(); ++i) {
                    JSONObject itemJo = items.getJSONObject(i);

                    String wxName = itemJo.getString("wx_name");
                    String wxNickname = itemJo.getString("wx_nickname");
                    String wxUrl = itemJo.getString("wx_url");

                    Date now = new Date();

                    // 2、封装定时任务指定爬取实例，保存数据库，用于微信文章以及阅读数和点赞数的爬取
                    PubAccountAddTaskDTO pubAccountAddTaskDTO = new PubAccountAddTaskDTO(
                            null, "4d4eb6317e5e4871897fdd7165964d3a", 2, now, now,
                            wxUrl, null, wxName, wxNickname, GSDataConstants.GSDATA_GROUP_ID,
                            0, now, now, null
                    );

                    if (null == pubAccountAddTaskDTOMapper.selectByWXId(pubAccountAddTaskDTO.getWxId())) {
                        // 3、将记录保存
                        pubAccountAddTaskDTOMapper.insert(pubAccountAddTaskDTO);
                        returnMap.put("code", 1);
                        returnMap.put("message", "记录保存成功！");
                    } else {
                        returnMap.put("code", 0);
                        returnMap.put("message", "该公众号已经保存！");
                    }
                }
            } else {
                returnMap.put("code", 0);
                returnMap.put("message", "接口返回数据不是唯一的");
            }
        }

        return returnMap;
    }
}
