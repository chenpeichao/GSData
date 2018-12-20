package org.pcchen.uar.service.impl;

import cn.gsdata.index.ApiSdk;
import org.apache.log4j.Logger;
import org.pcchen.uar.service.WXGroupService;
import org.pcchen.uar.utils.GSDataConstants;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 清博组相关操作
 *
 * @author cpc
 * @create 2018-12-20 10:20
 **/
@Service
public class WXGroupServiceImpl implements WXGroupService {
    private Logger logger = Logger.getLogger(WXGroupServiceImpl.class);


    /**
     * 根据文章url将信公众账号添加到分组并返回公众号相关信息
     *
     * @param groupId 清博中组id
     * @param url     要保存的微信公众号的文章url
     * @return
     */
    public Map<String, Object> addWXToGroupByurlAndReturnWXInfo(String groupId, String url) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            ApiSdk dataApi = ApiSdk.getApiSdk(GSDataConstants.GSDATA_APPID, GSDataConstants.GSDATA_APPKEY);
            Map<String, Object> requestParamsMap = new HashMap<String, Object>();

            // 分组ID
            requestParamsMap.put("groupid", groupId);
            // 待添加的微信公众号的文章地址： "url":"http://mp.weixin.qq.com/s?__biz=MTI0MDU3NDYwMQ==&mid=237223945&idx=4&sn=4dc9b60a32cc91e516bcb5983116314e&3rd=MzA3MDU4NTYzMw==&scene=6#rd"
            requestParamsMap.put("url", url);

            // 调用接口
            String jsonResponeData = dataApi.callInterFace(GSDataConstants.API_ADD_WX_TO_GROUP_BY_URL, requestParamsMap);

            returnMap.put("code", 1);
            returnMap.put("data", jsonResponeData);

            logger.info(GSDataConstants.API_ADD_WX_TO_GROUP_BY_URL + " params:" + requestParamsMap
                    + " response:" + jsonResponeData);

        } catch (Exception e) {
            returnMap.put("code", 0);
            returnMap.put("message", "发生未知错误，接口调用失败");
            logger.error(GSDataConstants.API_ADD_WX_TO_GROUP_BY_URL + "获取代码出错:", e);
        } finally {
            return returnMap;
        }
    }

    /**
     * 获取应用的所有分组信息
     *
     * @return
     */
    public Map<String, Object> getAllGroup() {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            ApiSdk dataApi = ApiSdk.getApiSdk(GSDataConstants.GSDATA_APPID, GSDataConstants.GSDATA_APPKEY);
            Map<String, Object> requestParamsMap = new HashMap<String, Object>();

            // 调用接口
            String jsonResponeData = dataApi.callInterFace(GSDataConstants.API_GET_ALL_GROUP_INFO, requestParamsMap);
            logger.info(GSDataConstants.API_GET_ALL_GROUP_INFO + " params:" + requestParamsMap
                    + " response:" + jsonResponeData);

            returnMap.put("code", 1);
            returnMap.put("data", jsonResponeData);
        } catch (Exception e) {
            returnMap.put("code", 0);
            returnMap.put("message", "发生未知错误，接口调用失败");
            logger.error(GSDataConstants.API_GET_ALL_GROUP_INFO + "获取代码出错:", e);
        } finally {
            return returnMap;
        }
    }
}
