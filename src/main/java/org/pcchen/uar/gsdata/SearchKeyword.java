package org.pcchen.uar.gsdata;

import cn.gsdata.index.ApiSdk;
import org.apache.log4j.Logger;
import org.pcchen.uar.utils.GSDataConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * 根据公众号关键字查询
 *
 * @author cpc
 * @create 2018-12-18 17:00
 **/
public class SearchKeyword {
    //日志对象
    private static Logger logger = Logger.getLogger(SearchKeyword.class.getName());

    /**
     * 根据关键词搜索公众号--最好是公众号
     *
     * @param keyword 搜索关键词
     * @param start   搜索结果开始位置（默认为0,start=start+num 假定num=10 例：0,10;10,10;20,10;30,10...）
     * @param num     返回数据最大记录数（默认为10，最大不超过10）
     * @return
     */
    public static String getNicknameByKeywordSearch(String keyword, int start, int num) {
        String jsonResponeData = "";
        try {
            ApiSdk dataApi = ApiSdk.getApiSdk(GSDataConstants.GSDATA_APPID, GSDataConstants.GSDATA_APPKEY);
            Map<String, Object> requestParamsMap = new HashMap<String, Object>();

            // 搜索的关键词
            requestParamsMap.put("keyword", keyword);
            // 搜索结果开始位置（默认为0,start=start+num 假定num=10 例：0,10;10,10;20,10;30,10...）
            requestParamsMap.put("start", start);
            // 返回数据最大记录数（默认为10，最大不超过10）
            requestParamsMap.put("num", num);

            // 调用接口
            jsonResponeData = dataApi.callInterFace(GSDataConstants.API_NICKNAME_KEYWORD_SEARCH, requestParamsMap);

            logger.info(GSDataConstants.API_NICKNAME_KEYWORD_SEARCH + " params:" + requestParamsMap
                    + " response:" + jsonResponeData);

        } catch (Exception e) {
            logger.error(GSDataConstants.API_NICKNAME_KEYWORD_SEARCH + "获取代码出错:", e);
        } finally {
            return jsonResponeData;
        }
    }

    public static void main(String[] args) {
        SearchKeyword.getNicknameByKeywordSearch("sgjtzhbfgs", 0, 10);
    }
}
