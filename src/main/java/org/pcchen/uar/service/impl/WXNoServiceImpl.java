package org.pcchen.uar.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.pcchen.uar.bean.local_pdmi.PubAccountAddTaskDTO;
import org.pcchen.uar.gsdata.NickName;
import org.pcchen.uar.gsdata.SearchKeyword;
import org.pcchen.uar.mapper.local_pdmi.PubAccountAddTaskDTOMapper;
import org.pcchen.uar.service.WXNoService;
import org.pcchen.uar.utils.GSDataConstants;
import org.pcchen.uar.utils.GSDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 公众号信息
 *
 * @author cpc
 * @create 2018-12-21 9:55
 **/
@Service
public class WXNoServiceImpl implements WXNoService {
    private Logger logger = Logger.getLogger(WXAddTaskServiceImpl.class);


    @Autowired
    private PubAccountAddTaskDTOMapper pubAccountAddTaskDTOMapper;

    /**
     * 根据公众号获取清博中公众号相关信息
     *
     * @param wxId
     * @return
     */
    public Map<String, Object> getGSDataWXNoInfoByWXId(String wxId) {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        // 1、根据公众号查询公众号信息(得到公众号名称、最新一篇文章的url)
        String nicknameOneByWXName = NickName.getNicknameOneByWXName(wxId);

        logger.info("根据公众号获取公众号信息返回字符串为：" + nicknameOneByWXName);

        JSONObject returnDataJsonObject = GSDataUtils.extractDataJson(nicknameOneByWXName);
        if (returnDataJsonObject != null) {
            String wxName = returnDataJsonObject.getString("wx_name");
            String wxNickname = returnDataJsonObject.getString("wx_nickname");
            String wxUrl = returnDataJsonObject.getString("wx_url");


            String addTime = returnDataJsonObject.getString("add_time"); // 清博添加时间

            Date now = new Date();

            if (StringUtils.isNotBlank(addTime)) {
                try {
                    now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(addTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

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
                returnMap.put("message", "公众号【" + wxId + "】因未知错误未保存成功！");
            }
        } else {
            returnMap.put("code", 0);
            returnMap.put("message", "公众号【" + wxId + "】在清博中未找到数据");
        }
        return returnMap;
    }
}
