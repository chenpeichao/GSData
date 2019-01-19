package org.pcchen.uar.gsdata;

import cn.gsdata.index.ApiSdk;
import org.apache.log4j.Logger;
import org.pcchen.uar.utils.DataApi;
import org.pcchen.uar.utils.GSDataConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信文章相关信息
 *
 * @author cpc
 * @create 2018-12-18 17:23
 **/
public class WXArticle {
    //日志对象
    private static Logger logger = Logger.getLogger(WXArticle.class.getName());

    /**
     * 查询文章阅读数变化明细
     *
     * @param beginDate 监测开始时间，格式：yyyy-MM-dd
     * @param endDate   监测结束时间，格式：yyyy-MM-dd
     * @param urls      监测的url列表，可用英文逗号隔开
     * @return
     */
    public static String queryReadnumDetail(String beginDate, String endDate, String urls) {
        String jsonResponeData = "";
        try {
            ApiSdk dataApi = ApiSdk.getApiSdk(DataApi.gsdata_appID, DataApi.gsdata_appKey);
            Map<String, Object> requestParamsMap = new HashMap<String, Object>();

            requestParamsMap.put("beginDate", beginDate);
            requestParamsMap.put("endDate", endDate);
            requestParamsMap.put("urls", urls);

            // 调用接口
            jsonResponeData = dataApi.callInterFace(DataApi.query_readnum_detail, requestParamsMap);

            logger.debug(DataApi.query_readnum_detail + " params:" + requestParamsMap
                    + " response:" + jsonResponeData);

        } catch (Exception e) {
            logger.error(DataApi.query_readnum_detail + "获取代码出错:", e);
        } finally {
            return jsonResponeData;
        }
    }


    /**
     * 获取实时文章接口
     *
     * @param beginDate    监测开始时间，格式：yyyy-MM-dd
     * @param endDate      监测结束时间，格式：yyyy-MM-dd
     * @param group_id     分组id
     * @param nickname_ids 平台微信id
     * @param page         当前页数
     * @param rows         每页大小，默认10
     * @return
     */
    public static String wxUrlMonitor(String beginDate, String endDate, String group_id,
                                      String nickname_ids, String page, String rows) {
        String jsonResponeData = "";
        try {
            ApiSdk dataApi = ApiSdk.getApiSdk(DataApi.gsdata_appID, DataApi.gsdata_appKey);
            Map<String, Object> requestParamsMap = new HashMap<String, Object>();

            requestParamsMap.put("beginDate", beginDate);
            requestParamsMap.put("endDate", endDate);
            requestParamsMap.put("group_id", group_id);
            requestParamsMap.put("nickname_ids", nickname_ids);
            requestParamsMap.put("page", page);
//            requestParamsMap.put("rows", rows);

            // 调用接口
            jsonResponeData = dataApi.callInterFace(DataApi.wx_url_monitor, requestParamsMap);

            logger.debug(DataApi.wx_url_monitor + " params:" + requestParamsMap
                    + " response:" + jsonResponeData);

        } catch (Exception e) {
            logger.error(DataApi.wx_url_monitor + "获取代码出错:", e);
        } finally {
            return jsonResponeData;
        }
    }

    /**
     * 通过文章URL获取该文章评论信息
     *
     * @param url 文章url
     * @return
     */
    public static String wxCommentByUrl(String url) {
        String jsonResponeData = "";
        try {
            ApiSdk dataApi = ApiSdk.getApiSdk(DataApi.gsdata_appID, DataApi.gsdata_appKey);
            Map<String, Object> requestParamsMap = new HashMap<String, Object>();

            requestParamsMap.put("url", url);

            // 调用接口
            jsonResponeData = dataApi.callInterFace(DataApi.wx_comment_by_url, requestParamsMap);

            logger.debug(DataApi.wx_comment_by_url + " params:" + requestParamsMap
                    + " response:" + jsonResponeData);

        } catch (Exception e) {
            logger.error(DataApi.wx_comment_by_url + "获取代码出错:", e);
        } finally {
            return jsonResponeData;
        }
    }

    public static void main(String[] args) {
        WXArticle.queryReadnumDetail("2019-01-01", "2019-01-01", "http://mp.weixin.qq.com/s?__biz=MjM5MjAxNDM4MA==&mid=2666232508&idx=1&sn=446438bfafcc57e052243ba371e4b79c&chksm=bdb313ff8ac49ae9487c25af6bb8501ecb99954aaadadd9abe08e8f5d937399b6df88fcac2ea&scene=0");
//        WXArticle.wxUrlMonitor("2019-01-02", "2019-01-03", "27657", "107562", "0", "10");
//        WXArticle.queryReadnumDetail("2019-01-01", "2019-01-03",
//                "http://mp.weixin.qq.com/s?__biz=MjM5MjAxNDM4MA==&mid=2666232322&idx=1&sn=4c6e928e77108fe9eca1669f75e0869a&chksm=bdb313418ac49a5772b0f69d3ac9696321bb9204fcf3e3bcce4b914d1de085d0ed949a67544b&scene=0"
//        );
//        WXArticle.getAllGroup();
//        GSDataGroup.addWXToGroupByurlAndReturnWXInfo("45542", "https://mp.weixin.qq.com/s?timestamp=1545125787&src=3&ver=1&signature=J1jZFaXR5AS4*UxRDL*X8tWn0BZN52-WSXQc9onoEmMuptlH43I6Z4zdyojOE2rZiz-qyIYbv0FxmoCZPt4Nc2fzF7W1VXLGVekDO*XAcgrLuJyEFRQxEWxNqQHC6Gzewi6Ci8hby7u*dS3C2T2pS9QYgbHVO-7nUkg3KRLCgXo=");


        WXArticle.wxCommentByUrl("http://mp.weixin.qq.com/s?__biz=MjM5MjAxNDM4MA==&mid=2666232508&idx=1&sn=446438bfafcc57e052243ba371e4b79c&chksm=bdb313ff8ac49ae9487c25af6bb8501ecb99954aaadadd9abe08e8f5d937399b6df88fcac2ea&scene=0");
    }
}
