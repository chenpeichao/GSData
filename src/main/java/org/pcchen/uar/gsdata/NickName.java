package org.pcchen.uar.gsdata;

import cn.gsdata.index.ApiSdk;
import org.apache.log4j.Logger;
import org.pcchen.uar.utils.GSDataConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * 公众号相关操作
 *
 * @author cpc
 * @create 2018-12-21 10:00
 **/
public class NickName {
    //日志对象
    private static Logger logger = Logger.getLogger(NickName.class.getName());

    /**
     * 根据公众号获取一个公众号详情
     *
     * @param wxName 微信号
     * @return
     */
    public static String getNicknameOneByWXName(String wxName) {
        String jsonResponeData = "";
        try {
            ApiSdk dataApi = ApiSdk.getApiSdk(GSDataConstants.GSDATA_APPID, GSDataConstants.GSDATA_APPKEY);
            Map<String, Object> requestParamsMap = new HashMap<String, Object>();

            // 搜索的关键词
            requestParamsMap.put("wx_name", wxName);

            // 调用接口
            jsonResponeData = dataApi.callInterFace(GSDataConstants.API_GET_NICKNAME_ONE, requestParamsMap);

            logger.info(GSDataConstants.API_NICKNAME_KEYWORD_SEARCH + " params:" + requestParamsMap
                    + " response:" + jsonResponeData);

        } catch (Exception e) {
            logger.error(GSDataConstants.API_NICKNAME_KEYWORD_SEARCH + "获取代码出错:", e);
        } finally {
            return jsonResponeData;
        }
    }

    public static void main(String[] args) {
//        NickName.getNicknameOneByWXName("sgjtzhbfgs", 0, 10);
    }
}
