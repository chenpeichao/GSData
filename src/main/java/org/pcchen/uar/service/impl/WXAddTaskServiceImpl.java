package org.pcchen.uar.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.pcchen.uar.bean.local_pdmi.PubAccountAddTaskDTO;
import org.pcchen.uar.gsdata.SearchKeyword;
import org.pcchen.uar.mapper.local_pdmi.PubAccountAddTaskDTOMapper;
import org.pcchen.uar.service.RongMeiTiWXService;
import org.pcchen.uar.service.WXAddTaskService;
import org.pcchen.uar.service.WXNoService;
import org.pcchen.uar.utils.GSDataConstants;
import org.pcchen.uar.utils.GSDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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


    @Autowired
    private RongMeiTiWXService rongMeiTiWXService;
    @Autowired
    private WXNoService wxNoService;

    /**
     * 根据公众号搜索公众号默认1条记录
     *
     * @param wxId  公众号
     * @param start 起始位置
     * @param num   显示总记录数
     * @return
     */
    public Map<String, Object> searchKeywordByWXId(String wxId, Integer start, Integer num) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        // 1、根据公众号查询公众号信息(得到公众号名称、最新一篇文章的url)
        String nicknameByKeywordSearch = SearchKeyword.getNicknameByKeywordSearch(wxId, start, num);

        logger.info("根据公众号查询返回字符串为：" + nicknameByKeywordSearch);

        JSONObject returnDataJsonObject = GSDataUtils.extractDataJson(nicknameByKeywordSearch);

        returnMap.put("code", 1);
        returnMap.put("data", returnDataJsonObject.toJSONString());

        return returnMap;
    }

    /**
     * 根据公众号id，搜索公众号信息，并且保存到任务待执行表中
     *
     * @param wxId 微信公众号
     * @return
     */
    public Map<String, Object> fromSearchKeywordAndSaveTaskByWXId(String wxId) {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        return this.searchKeywordAndSaveTaskByWXId(wxId);
    }

    /**
     * 将本地数据库中的公众号添加到任务待执行表中
     *
     * @return
     */
    public Map<String, Object> fromLocalMysqlAndSaveTaskByWXId() {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        List<String> localMysqlWXNoList = rongMeiTiWXService.selectEffectWXNoList();

        Map<String, String> errorWXNoMap = new HashMap<String, String>();
        List<String> successWXNoList = new ArrayList<String>();
        for (String wxNo : localMysqlWXNoList) {
            Map<String, Object> stringObjectMap = wxNoService.getGSDataWXNoInfoByWXId(wxNo);
            if (1 != (Integer) stringObjectMap.get("code")) {
                errorWXNoMap.put(wxNo, stringObjectMap.get("message").toString());
            } else {
                successWXNoList.add(wxNo);
            }
            try {
                //每次接口sleep500毫秒，避免频繁调用接口失败
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        returnMap.put("code", 1);
        returnMap.put("successWXIds", successWXNoList);
        returnMap.put("errorWXNoMap", errorWXNoMap);

        return returnMap;
    }

    /**
     * 对于任务表中微信的url信息为null的数据进行更新
     *
     * @return
     */
    public Map<String, Object> updateTaskTableURLBySearchAPI() {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        List<String> localMysqlWXNoList = pubAccountAddTaskDTOMapper.selectWXIdListByUrlIsNull();

        Map<String, String> errorWXNoMap = new HashMap<String, String>();
        List<String> successWXNoList = new ArrayList<String>();
        for (String wxId : localMysqlWXNoList) {
            // 1、根据公众号查询公众号信息(得到公众号名称、最新一篇文章的url)
            String nicknameByKeywordSearch = SearchKeyword.getNicknameByKeywordSearch(wxId, GSDataConstants.GSDATA_SEARCH_KEYWORD_API_START, GSDataConstants.GSDATA_SEARCH_KEYWORD_API_NUM);

            logger.info("根据公众号查询返回字符串为：" + nicknameByKeywordSearch);

            JSONObject returnDataJsonObject = GSDataUtils.extractDataJson(nicknameByKeywordSearch);
            if (returnDataJsonObject != null) {
                JSONArray items = returnDataJsonObject.getJSONArray("items");
                if (null != items && items.size() == 1) {
                    JSONObject itemJo = items.getJSONObject(0);

                    String wxIdBySearchAPI = itemJo.getString("wx_name");
                    String wxUrl = itemJo.getString("wx_url");

                    if (wxId.equals(wxIdBySearchAPI) && StringUtils.isNotBlank(wxUrl)) {
                        // 3、将记录保存
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("wxId", wxId);
                        params.put("wxUrl", wxUrl);
                        pubAccountAddTaskDTOMapper.updateUrlByWXId(params);
//                        returnMap.put("code", wxId);
//                        returnMap.put("message", "公众号【"+wxId+"】更新url成功");
                    } else if (!wxId.equals(wxIdBySearchAPI)) {
                        returnMap.put(wxId, "公众号【" + wxId + "】没有找对");
                    } else if (StringUtils.isBlank(wxUrl)) {
                        returnMap.put(wxId, "公众号【" + wxId + "】未找到url");
                    } else {
                        returnMap.put(wxId, "公众号【" + wxId + "】查找到了未知错误");
                    }
                } else if (null != items && items.size() > 1) {
                    returnMap.put(wxId, "公众号【" + wxId + "】在清博中有多个数据");
                } else {
                    returnMap.put(wxId, "公众号【" + wxId + "】在清博中未找到数据");
                }
            } else {
                returnMap.put(wxId, "公众号【" + wxId + "】在清博中未找到数据");
            }
            try {
                //每次接口sleep500毫秒，避免频繁调用接口失败
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        returnMap.put("code", 1);
        returnMap.put("successWXIds", successWXNoList);
        returnMap.put("errorWXNoMap", errorWXNoMap);

        return returnMap;
    }

    /**
     * 获取到未添加到task表里面的公众号
     *
     * @return
     */
    public Map<String, Object> getExceptWxNoList() {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        List<String> localMysqlRongMeiTiWXNoList = rongMeiTiWXService.selectEffectWXNoList();
        List<String> localMysqlToDoTaskWXNoList = pubAccountAddTaskDTOMapper.selectAllWxIdList();

        List<String> noInTaskWXIdList = new ArrayList<String>();
        List<String> inTaskWXIdList = new ArrayList<String>();
        for (String wxId : localMysqlRongMeiTiWXNoList) {
            if (!localMysqlToDoTaskWXNoList.contains(wxId)) {
                noInTaskWXIdList.add(wxId);
            }
        }
        for (String wxId : localMysqlRongMeiTiWXNoList) {
            if (localMysqlToDoTaskWXNoList.contains(wxId)) {
                inTaskWXIdList.add(wxId);
            }
        }
        returnMap.put("code", 1);
        returnMap.put("noInTaskWXIdList", noInTaskWXIdList);
        returnMap.put("noInTaskWXIdList.size()", noInTaskWXIdList.size());
        returnMap.put("inTaskWXIdList", inTaskWXIdList);
        returnMap.put("inTaskWXIdList.size()", inTaskWXIdList.size());
        return returnMap;
    }


    /**
     * 根据查询公众号，查询清博中公众号信息并将数据保存导任务待执行表中
     *
     * @param wxId 公众号
     * @return
     */
    private Map<String, Object> searchKeywordAndSaveTaskByWXId(String wxId) {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        // 1、根据公众号查询公众号信息(得到公众号名称、最新一篇文章的url)
        String nicknameByKeywordSearch = SearchKeyword.getNicknameByKeywordSearch(wxId, GSDataConstants.GSDATA_SEARCH_KEYWORD_API_START, GSDataConstants.GSDATA_SEARCH_KEYWORD_API_NUM);

        logger.info("根据公众号查询返回字符串为：" + nicknameByKeywordSearch);

        JSONObject returnDataJsonObject = GSDataUtils.extractDataJson(nicknameByKeywordSearch);
        if (returnDataJsonObject != null) {
            JSONArray items = returnDataJsonObject.getJSONArray("items");
            if (null != items && items.size() == 1) {
                JSONObject itemJo = items.getJSONObject(0);

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

                if (null == pubAccountAddTaskDTOMapper.selectByWXId(pubAccountAddTaskDTO.getWxId()) && wxId.equals(wxName)) {
                    // 3、将记录保存
                    pubAccountAddTaskDTOMapper.insert(pubAccountAddTaskDTO);
                    returnMap.put("code", 1);
                    returnMap.put("message", "保存成功");
                } else if (!wxId.equals(wxName)) {
                    returnMap.put("code", 0);
                    returnMap.put("message", "公众号【" + wxId + "】没有找对");
                } else {
                    returnMap.put("code", 0);
                    returnMap.put("message", "公众号【" + wxId + "】已经保存");
                }
            } else if (null != items && items.size() > 1) {
                returnMap.put("code", 0);
                returnMap.put("message", "公众号【" + wxId + "】在清博中有多个数据");
            } else {
                returnMap.put("code", 0);
                returnMap.put("message", "公众号【" + wxId + "】在清博中未找到数据");
            }
        } else {
            returnMap.put("code", 0);
            returnMap.put("message", "公众号【" + wxId + "】在清博中未找到数据");
        }
        return returnMap;
    }
}
