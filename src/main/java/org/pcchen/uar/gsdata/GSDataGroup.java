package org.pcchen.uar.gsdata;

import cn.gsdata.index.ApiSdk;
import org.apache.log4j.Logger;
import org.pcchen.uar.utils.DataApi;
import org.pcchen.uar.utils.GSDataConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * 公众号组相关操作
 *
 * @author cpc
 * @create 2018-12-18 17:23
 **/
public class GSDataGroup {
    //日志对象
    private static Logger logger = Logger.getLogger(GSDataGroup.class.getName());

    /**
     * 根据文章url将信公众账号添加到分组并返回公众号相关信息
     *
     * @param groupid 分组id
     * @param url     文章url
     * @return
     */
    public static String addWXToGroupByurlAndReturnWXInfo(String groupid, String url) {
        String jsonResponeData = "";
        try {
            ApiSdk dataApi = ApiSdk.getApiSdk(DataApi.gsdata_appID, DataApi.gsdata_appKey);
            Map<String, Object> requestParamsMap = new HashMap<String, Object>();

            // 分组ID
            requestParamsMap.put("groupid", groupid);
            // 待添加的微信公众号的文章地址： "url":"http://mp.weixin.qq.com/s?__biz=MTI0MDU3NDYwMQ==&mid=237223945&idx=4&sn=4dc9b60a32cc91e516bcb5983116314e&3rd=MzA3MDU4NTYzMw==&scene=6#rd"
            requestParamsMap.put("url", url);

            // 调用接口
            jsonResponeData = dataApi.callInterFace(DataApi.CustomAPI_add_weixin2group_byurl, requestParamsMap);

            logger.debug(DataApi.CustomAPI_add_weixin2group_byurl + " params:" + requestParamsMap
                    + " response:" + jsonResponeData);

        } catch (Exception e) {
            logger.error(DataApi.CustomAPI_add_weixin2group_byurl + "获取代码出错:", e);
        } finally {
            return jsonResponeData;
        }
    }

    /**
     * 获取应用的所有分组信息
     *
     * @return
     */
    public static String getAllGroup() {
        String jsonResponeData = "";
        try {
            ApiSdk dataApi = ApiSdk.getApiSdk(DataApi.gsdata_appID, DataApi.gsdata_appKey);
            Map<String, Object> requestParamsMap = new HashMap<String, Object>();

            // 调用接口
            jsonResponeData = dataApi.callInterFace(DataApi.get_all_group, requestParamsMap);

            logger.debug(DataApi.get_all_group + " params:" + requestParamsMap
                    + " response:" + jsonResponeData);

        } catch (Exception e) {
            logger.error(DataApi.get_all_group + "获取代码出错:", e);
        } finally {
            return jsonResponeData;
        }
    }

    public static void main(String[] args) {
        GSDataGroup.getAllGroup();
//        GSDataGroup.addWXToGroupByurlAndReturnWXInfo("45542", "https://mp.weixin.qq.com/s?timestamp=1545125787&src=3&ver=1&signature=J1jZFaXR5AS4*UxRDL*X8tWn0BZN52-WSXQc9onoEmMuptlH43I6Z4zdyojOE2rZiz-qyIYbv0FxmoCZPt4Nc2fzF7W1VXLGVekDO*XAcgrLuJyEFRQxEWxNqQHC6Gzewi6Ci8hby7u*dS3C2T2pS9QYgbHVO-7nUkg3KRLCgXo=");
    }
}
