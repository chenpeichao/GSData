package org.pcchen.uar.utils;

import cn.gsdata.index.ApiSdk;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import iims.crt.data.*;
import iims.crt.gsdata.*;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 通过GSData接口，对指定账户下的微信作品及其用户反馈数据进行采集
 *
 * @author
 */
public class DataApi {
    //日志对象
    private static Logger logger = Logger.getLogger(iims.crt.gsdata.DataApi.class.getName());

    //获取ApiSdk 单例对象
    private ApiSdk apiSdk = null;

    //获取文章的接口
    public static String gsdata_appID = "mzgvw1O7nYnglPvhjHST";
    public static String gsdata_appKey = "0h3mrJcord72zHOeJY4TnTFqO";
    // public static String CustomAPI_wx_url_monitor = "http://open.gsdata.cn/api/custom/customapi/wx_url_monitor";
    public static String CustomAPI_wx_url_monitor = "http://open.gsdata.cn/api/custom/customapi/wx_url_content_monitor";
    public static String CustomAPI_wx_url_2 = "http://open.gsdata.cn/api/custom/customapi/wx_articles_by_wxname";

    public static String CustomAPI_wx_content = "http://open.gsdata.cn/api/wx/wxapi/wx_content";
    //	public static String CustomAPI_add_weixin2group = "http://open.gsdata.cn/api/custom/customapi/addWXname2Group";
    public static String CustomAPI_add_weixin2group = "http://open.gsdata.cn/api/wx/wxapi/add_wx_to_groupMonitor";
    public static String CustomAPI_addname_weixin2group = "http://open.gsdata.cn/api/wx/wxapi/nickname_one";
    public static String CustomAPI_del_weixin2group = "http://open.gsdata.cn/api/wx/wxapi/del_wx_from_groupMonitor";
    public static String CustomAPI_add_weixin2group_byurl = "http://open.gsdata.cn/api/wx/wxapi/add_wx_to_group_by_url";//添加微信公众账号到分组并返回公众号相关信息
    public static String CustomAPI_add_groupandmonitor = "http://open.gsdata.cn/api/custom/customapi/add_wx_to_group_monitor";//添加微信公众账号到分组并加入监测，返回公众号相关信息

    /**
     * 实时跟踪的接口调用
     */
    public static String API_PDMI_KEYWORD = "http://open.gsdata.cn/api/pdmi/pdmi_keyword";

    /**
     * 实时获取单篇微信文章阅读数点赞数
     */
    public static String API_WX_NUMS_MONITOR = "http://open.gsdata.cn/api/wx/wxapi/wx_nums_monitor";

    /**
     * 根据关键词搜索公众号文章
     */
    public static String CUSTOMAPI_CONTENT_KEYWORD_SEARCH
            = "http://open.gsdata.cn/api/wx/opensearchapi/content_keyword_search";

    /**
     * 从关键字检索文章出正文的url通过清博接口获取正文内容
     */
    public static String CUSTOMAPI_CONTENT = "http://open.gsdata.cn/api/wx/wxapi/wx_content2";


    public static int gsdata_groupID = 27657;

    //每次返回的最大条目数
    public static int MaxRows_Request = 10;

    //接口声明的文章数
    public static int totalArticles = 0;
    //实际采集到的文章数
    public static int totalArticlesBeGet = 0;
    //更新的文章数
    public static int totalUpdatedArticles = 0;
    //保存的新文章数
    public static int newArticlesBeSaved = 0;


    private static DataApi apiService = null;

    /**
     * 单例
     */
    public static synchronized DataApi getInstance() {
        if (apiService == null)
            apiService = new DataApi();
        return apiService;
    }

    private DataApi() {
        logger.info("initializing the GSDATA API.");

        apiSdk = ApiSdk.getApiSdk(gsdata_appID, gsdata_appKey);
    }

    /**
     * 解析Json页面，获取Article列表
     *
     * @param strJson
     */
    public ArrayList<OriginNews> parseArticleList(String strJson) {
        ArrayList<OriginNews> list = new ArrayList<OriginNews>();

        try {
            JSONObject jo = new JSONObject(strJson);

            int returnCode = jo.getInt("returnCode");
            String returnMsg = jo.getString("returnMsg");

            logger.info("ReturnCode=" + returnCode + "\t" + returnMsg);

            JSONObject dataObject = jo.getJSONObject("returnData");

            totalArticles = dataObject.getInt("total");
            JSONArray articleListArray = dataObject.getJSONArray("rows");

            for (int i = 0; i < articleListArray.length(); i++) {
                JSONObject ao = (JSONObject) articleListArray.get(i);

                OriginNews oriArticle = new OriginNews();
                Website media = new Website();
                media.setWebName(ao.getString("name"));
                media.setReservedTag(ao.getString("wx_name"));

                ScanPanel scanPanel = new ScanPanel();
                MediaChannel channel = new MediaChannel();
                channel.setMedia(media);
                channel.setScanPanel(scanPanel);

                oriArticle.setChannel(channel);

                oriArticle.setSubject(ao.getString("title"));
                oriArticle.setUrl(ao.getString("url"));
                oriArticle.setPostTime(ao.getString("posttime"));

                FeedbackStat feedbackStat = new FeedbackStat();
                feedbackStat.setReadCount(ao.getInt("readnum"));
                feedbackStat.setAgreeCount(ao.getInt("likenum"));
                oriArticle.setFeedbackStat(feedbackStat);

                oriArticle.setContent(ao.getString("content"));

                logger.info(oriArticle.getFeedbackStat().getAgreeCount() + "/" + oriArticle.getFeedbackStat().getReadCount() + "\t" + oriArticle.getChannel().getMedia().getReservedTag() + "\t" + oriArticle.getChannel().getMedia().getWebName() + "\t" + oriArticle.getSubject());

                list.add(oriArticle);
            }
        } catch (Exception e) {
            logger.error("return json:" + strJson + "\n 获取代码出错:", e);
        }

        return list;
    }

    /**
     * 通过GSDATA SDK调用
     *
     * @return
     */
    public String getResponseData(int pageNo) {
        String ret_json = null;

        try {
            /*
             * 参数集合 此处参数不需要加appid和appkey,因为上面构造单例对象时，已经默认加上了，
             * 如果使用非单例对象模式，在下面参数集合中需要 加上appid和appkey
             */
            Map<String, Object> requestParamsMap = new HashMap<String, Object>();

            SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd");
            String yesterday = formats.format(new Date().getTime() - 1 * 24 * 60 * 60 * 1000);

            requestParamsMap.put("beginDate", yesterday);
            requestParamsMap.put("group_id", gsdata_groupID);
            requestParamsMap.put("page", pageNo);

            //调用接口
            ret_json = apiSdk.callInterFace(CustomAPI_wx_url_monitor, requestParamsMap);

            logger.info(ret_json);

            //将Utf8转换成gbk
            //ret_json = StringUtil.utf82gbk(ret_json);

        } catch (Exception e) {
            logger.error("获取代码出错:", e);
        }

        return ret_json;
    }
    /**
     * 自定义接口
     *
     * @return
     */
    public String getResponseData2() {
        String ret_json = null;

        try {
            /*
             * 参数集合 此处参数不需要加appid和appkey,因为上面构造单例对象时，已经默认加上了，
             * 如果使用非单例对象模式，在下面参数集合中需要 加上appid和appkey
             */
            Map<String, Object> requestParamsMap = new HashMap<String, Object>();

            SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd");
            String yesterday = formats.format(new Date().getTime() - 1 * 24 * 60 * 60 * 1000);

            requestParamsMap.put("beginDate", "2018-09-12 00:00:00");
            requestParamsMap.put("endDate", "2018-09-12 24:00:00");
            requestParamsMap.put("wxName", "rmrbwx");

            //调用接口
            ret_json = apiSdk.callInterFace(CustomAPI_wx_url_2, requestParamsMap);

            logger.info(ret_json);

            //将Utf8转换成gbk
            //ret_json = StringUtil.utf82gbk(ret_json);

        } catch (Exception e) {
            logger.error("获取代码出错:", e);
        }

        return ret_json;
    }

    /**
     * 获取微信数据状况
     *
     * @param page        搜索结果开始页（默认为0）
     * @param nicknameIds 平台内公众号ID集以逗号分隔(格式：1001,1002,1003)
     * @return
     */
    public String getResponseData(int page, String nicknameIds) {
        String ret_json = null;

        try {
            /*
             * 参数集合 此处参数不需要加appid和appkey,因为上面构造单例对象时，已经默认加上了，
             * 如果使用非单例对象模式，在下面参数集合中需要 加上appid和appkey
             */
            Map<String, Object> requestParamsMap = new HashMap<String, Object>();

            SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd");
            String yesterday = formats.format(new Date().getTime() - 1 * 24 * 60 * 60 * 1000);

            requestParamsMap.put("beginDate", yesterday);
            // requestParamsMap.put("group_id", gsdata_groupID);
            requestParamsMap.put("nickname_ids", nicknameIds);
            requestParamsMap.put("page", page);

            //调用接口
            ret_json = apiSdk.callInterFace(CustomAPI_wx_url_monitor, requestParamsMap);

            logger.info(ret_json);

            //将Utf8转换成gbk
            //ret_json = StringUtil.utf82gbk(ret_json);

        } catch (Exception e) {
            logger.error("获取代码出错:", e);
        }

        return ret_json;
    }

    /**
     * 获取微信数据状况
     *
     * @param page        搜索结果开始页（默认为0）
     * @param nicknameIds 平台内公众号ID集以逗号分隔(格式：1001,1002,1003)
     * @param beginDate   查询开始时间 (格式：yyyy-MM-dd 默认：本月第一天)
     * @param endDate     查询结束时间 (格式：yyyy-MM-dd 默认：本月最后一天)
     * @return
     */
    public String getResponseData(int page, String nicknameIds, String beginDate, String endDate) {
        String ret_json = null;

        try {
            /*
             * 参数集合 此处参数不需要加appid和appkey,因为上面构造单例对象时，已经默认加上了，
             * 如果使用非单例对象模式，在下面参数集合中需要 加上appid和appkey
             */
            Map<String, Object> requestParamsMap = new HashMap<String, Object>();

            requestParamsMap.put("beginDate", beginDate);
            requestParamsMap.put("endDate", endDate);
            // requestParamsMap.put("group_id", gsdata_groupID);
            requestParamsMap.put("nickname_ids", nicknameIds);
            requestParamsMap.put("page", page);

            //调用接口
            ret_json = apiSdk.callInterFace(CustomAPI_wx_url_monitor, requestParamsMap);

            logger.info(ret_json);

            //将Utf8转换成gbk
            //ret_json = StringUtil.utf82gbk(ret_json);

        } catch (Exception e) {
            logger.error("获取代码出错:", e);
        }

        return ret_json;
    }

    /**
     * 获取微信数据状况
     *
     * @param page        搜索结果开始页（默认为0）
     * @param nicknameIds 平台内公众号ID集以逗号分隔(格式：1001,1002,1003)
     * @param beginDate   查询开始时间 (格式：yyyy-MM-dd 默认：本月第一天)
     * @param endDate     查询结束时间 (格式：yyyy-MM-dd 默认：本月最后一天)
     * @return
     */
    public List<WxUrlMonitorResult> getResponseData(int page, String nicknameIds, Date beginDate, Date endDate) {
        List<WxUrlMonitorResult> results = null;

        try {
            /*
             * 参数集合 此处参数不需要加appid和appkey,因为上面构造单例对象时，已经默认加上了，
             * 如果使用非单例对象模式，在下面参数集合中需要 加上appid和appkey
             */
            Map<String, Object> requestParamsMap = new HashMap<>();
            // 判断开始时间
            if (beginDate == null) {
                beginDate = new Date();
            }
            requestParamsMap.put("beginDate", DateFormatUtils.format(beginDate, "yyyy-MM-dd"));
            // 判断开始时间
            if (endDate == null) {
                endDate = new Date();
            }
            requestParamsMap.put("endDate", DateFormatUtils.format(endDate, "yyyy-MM-dd"));
            // requestParamsMap.put("group_id", gsdata_groupID);
            requestParamsMap.put("nickname_ids", nicknameIds);
            requestParamsMap.put("page", page);

            //调用接口
            String ret_json = apiSdk.callInterFace(CustomAPI_wx_url_monitor, requestParamsMap);
            // log
            logger.info(MessageFormat.format("params is {0}, \n response data is {1}",
                    new Gson().toJson(requestParamsMap), ret_json));

            JSONObject jo = new JSONObject(ret_json);

            JSONObject dataObject = jo.getJSONObject("returnData");
            JSONArray articleListArray = dataObject.getJSONArray("rows");

            if (articleListArray.length() > 0) {
                results = new ArrayList<>();
                WxUrlMonitorResult record = null;
                for (int i = 0; i < articleListArray.length(); i++) {
                    JSONObject returnData = (JSONObject) articleListArray.get(i);
                    record = new WxUrlMonitorResult();
                    // 	微信公众号名称
                    if (returnData.has("name"))
                        record.setName(returnData.getString("name"));

                    // 	微信公众号账号
                    if (returnData.has("wx_name"))
                        record.setWxName(returnData.getString("wx_name"));

                    // 	平台内公众号ID
                    if (returnData.has("nickname_id"))
                        record.setNickNameId(returnData.getInt("nickname_id"));

                    // 	文章发布时间
                    if (returnData.has("posttime"))
                        record.setPostTime(returnData.getString("posttime"));

                    // 		文章标题
                    if (returnData.has("title"))
                        record.setTitle(returnData.getString("title"));

                    // 	文章正文
                    if (returnData.has("content"))
                        record.setContent(returnData.getString("content"));

                    // 	微信文章地址
                    if (returnData.has("url"))
                        record.setUrl(returnData.getString("url"));

                    // 		文章入库时间
                    if (returnData.has("add_time"))
                        record.setAddTime(returnData.getString("add_time"));

                    // 	实时跟踪时间
                    if (returnData.has("monitor_time"))
                        record.setMonitorTime(returnData.getString("monitor_time"));

                    // 	文章阅读数
                    if (returnData.has("readnum"))
                        record.setReadNum(returnData.getInt("readnum"));

                    // 	文章点赞数
                    if (returnData.has("likenum"))
                        record.setLikeNum(returnData.getInt("likenum"));

                    // 	文章位置
                    if (returnData.has("top"))
                        record.setLikeNum(returnData.getInt("top"));

                    // 	是否同步
                    if (returnData.has("ispush"))
                        record.setIsPush(returnData.getInt("ispush"));

                    // 	封面地址
                    if (returnData.has("picurl"))
                        record.setPicUrl(returnData.getString("picurl"));

                    // 	原文地址
                    if (returnData.has("sourceurl"))
                        record.setSourceUrl(returnData.getString("sourceurl"));

                    // 	文章作者
                    if (returnData.has("author"))
                        record.setAuthor(returnData.getString("author"));

                    // 	图片地址 ( 说明 : 非定制不提供此URL地址)
                    if (returnData.has("desc"))
                        record.setDesc(returnData.getString("desc"));

                    // 	视频地址 ( 说明 : 非定制不提供此URL地址 )
                    if (returnData.has("videourl"))
                        record.setVideoUrl(returnData.getString("videourl"));

                    // 	图片地址 ( 说明 : 非定制不提供此URL地址)
                    if (returnData.has("imgsurl"))
                        record.setImgsUrl(returnData.getString("imgsurl"));

                    results.add(record);
                }
            }

        } catch (Exception e) {
            logger.error("获取代码出错:", e);
        }

        return results;
    }

    /**
     * 通过GSDATA SDK调用获取正文
     *
     * @return
     */
    public String getWeixinContent(String gsWeixinUrl) {
        String content = null;

        try {
            Map<String, Object> requestParamsMap = new HashMap<String, Object>();
            requestParamsMap.put("url", gsWeixinUrl);

            //调用接口
            String ret_json = apiSdk.callInterFace(CustomAPI_wx_content, requestParamsMap);

            //将Utf8转换成gbk
            //ret_json = StringUtil.utf82gbk(ret_json);

            if (ret_json == null)
                return null;

            logger.debug(ret_json);
            JSONObject jo = new JSONObject(ret_json);
            // int returnCode = jo.getInt("returnCode");

            JSONArray returnData = jo.getJSONArray("returnData");

            if (returnData == null || returnData.length() == 0)
                return null;

            JSONObject ao = (JSONObject) returnData.get(0);
            content = ao.getString("content");

            logger.debug(gsWeixinUrl + "\t" + content);

        } catch (Exception e) {
            logger.error("获取代码出错:", e);
        }

        return content;
    }

    /**
     * 获取一个公众号详情
     *
     * @param wxName 微信号
     * @return
     */
    public ResNickNameOneResult getNickNameOne(String wxName) {
        ResNickNameOneResult resNickNameOneResult = null;

        try {
            Map<String, Object> requestParamsMap = new HashMap<String, Object>();
            requestParamsMap.put("wx_name", wxName);

            //调用接口
            String ret_json = apiSdk.callInterFace(CustomAPI_addname_weixin2group, requestParamsMap);

            //将Utf8转换成gbk
            //ret_json = StringUtil.utf82gbk(ret_json);

            if (ret_json == null)
                return null;

            logger.debug(ret_json);
            JSONObject jo = new JSONObject(ret_json);
            // int returnCode = jo.getInt("returnCode");
            if (jo.has("returnData")) {
                JSONObject returnData = jo.getJSONObject("returnData");

                if (returnData == null || returnData.length() == 0)
                    return null;

                // 返回结果
                resNickNameOneResult = new ResNickNameOneResult();
                // 平台内公众号ID
                if (returnData.has("id"))
                    resNickNameOneResult.setId(returnData.getInt("id"));
                // 公众号名昵称
                if (returnData.has("wx_nickname"))
                    resNickNameOneResult.setWxNickname(returnData.getString("wx_nickname"));

                // 待定
                if (returnData.has("wx_openid"))
                    resNickNameOneResult.setWxOpenid(returnData.getString("wx_openid"));

                // 微信官方biz
                if (returnData.has("wx_biz"))
                    resNickNameOneResult.setWxBiz(returnData.getString("wx_biz"));

                // 公众号类型
                if (returnData.has("wx_type"))
                    resNickNameOneResult.setWxType(returnData.getInt("wx_type"));

                // 公众账号
                if (returnData.has("wx_name"))
                    resNickNameOneResult.setWxName(returnData.getString("wx_name"));

                // 公众号二维码地址
                if (returnData.has("wx_qrcode"))
                    resNickNameOneResult.setWxQrcode(returnData.getString("wx_qrcode"));

                // 公众号描述
                if (returnData.has("wx_note"))
                    resNickNameOneResult.setWxNote(returnData.getString("wx_note"));

                // 是否认证
                if (returnData.has("wx_vip")) {
                    resNickNameOneResult.setWxVip(returnData.getString("wx_vip"));
                }

                // 认证信息
                if (returnData.has("wx_vip_note")) {
                    resNickNameOneResult.setWxVipNote(returnData.getString("wx_vip_note"));
                }

                // 公众号所属国家
                if (returnData.has("wx_country")) {
                    resNickNameOneResult.setWxCountry(returnData.getString("wx_country"));
                }

                // 公众号所属省份
                if (returnData.has("wx_country"))
                    resNickNameOneResult.setWxProvince(returnData.getString("wx_province"));

                // 公众号所属城市
                if (returnData.has("wx_city"))
                    resNickNameOneResult.setWxCity(returnData.getString("wx_city"));

                // 最新文章标题
                if (returnData.has("wx_title"))
                    resNickNameOneResult.setWxTitle(returnData.getString("wx_title"));

                // 	最新文章地址
                if (returnData.has("wx_url"))
                    resNickNameOneResult.setWxUrl(returnData.getString("wx_url"));

                // 最新文章发布时间
                if (returnData.has("wx_url_posttime"))
                    resNickNameOneResult.setWxUrlPosttime(returnData.getString("wx_url_posttime"));

                // 	用户ID
                if (returnData.has("uid"))
                    resNickNameOneResult.setUid(returnData.getInt("uid"));

                // 开始采集时间
                if (returnData.has("time_start"))
                    resNickNameOneResult.setTimeSart(returnData.getString("time_start"));

                // 结束采集时间
                if (returnData.has("time_end"))
                    resNickNameOneResult.setTimeEnd(returnData.getString("time_end"));

                // 结束时间
                if (returnData.has("time_stop"))
                    resNickNameOneResult.setTimeStop(returnData.getString("time_stop"));

                // 添加时间
                if (returnData.has("add_time"))
                    resNickNameOneResult.setAddTime(returnData.getString("add_time"));

                // 	状态
                if (returnData.has("status"))
                    resNickNameOneResult.setStatus(returnData.getInt("status"));

                // 	是否可用 0可用 1禁用
                if (returnData.has("isenable"))
                    resNickNameOneResult.setIsEnable(returnData.getInt("isenable"));

                // 	更新状态
                if (returnData.has("update_status"))
                    resNickNameOneResult.setUpdateStatus(returnData.getInt("update_status"));

                logger.debug("\t" + resNickNameOneResult);
            } else {
                logger.error(MessageFormat.format("wexin code {0} get request error,return data is \n "
                        , wxName) + ret_json);
            }

        } catch (Exception e) {
            logger.error("get data error:", e);
        }

        return resNickNameOneResult;
    }

    /**
     * 添加微信号到分组
     *
     * @param weixinNames 微信公众号，多个用“,”分隔
     * @return
     */
    public boolean addWeixin2Group(String weixinNames, String originUrl) {
        String ret_json = null;
        try {
            ApiSdk dataApi = ApiSdk.getApiSdk(gsdata_appID, gsdata_appKey);
            Map<String, Object> requestParamsMap = new HashMap<String, Object>();
            requestParamsMap.put("url", originUrl);
            ret_json = dataApi.callInterFace(CustomAPI_add_groupandmonitor, requestParamsMap);
             /*
             requestParamsMap.put("wx_name",weixinNames);
             ret_json = dataApi.callInterFace(CustomAPI_addname_weixin2group, requestParamsMap);

             JsonParser json = new JsonParser();

             JsonObject jsonObject = json.parse(ret_json).getAsJsonObject().getAsJsonObject("returnData");
             if(jsonObject==null){//判读如果returnData为null则分组中没有此微信号，则添加
             requestParamsMap = new HashMap<String, Object>();
                 requestParamsMap.put("groupid",gsdata_groupID);
                 requestParamsMap.put("url", originUrl);
                 ret_json = dataApi.callInterFace(CustomAPI_add_weixin2group_byurl, requestParamsMap);
                 requestParamsMap = new HashMap<String, Object>();
                 requestParamsMap.put("wx_name",weixinNames);
                 ret_json = dataApi.callInterFace(CustomAPI_addname_weixin2group, requestParamsMap);
                 jsonObject = json.parse(ret_json).getAsJsonObject().getAsJsonObject("returnData");
             }
            if(jsonObject!=null){
                String id = jsonObject.get("id").getAsString();

                requestParamsMap = new HashMap<String, Object>();

             requestParamsMap.put("wxJson", "[ {\"nickname_id\":\"" + id + "\"}]");

             //调用接口
             ret_json = dataApi.callInterFace(CustomAPI_add_weixin2group, requestParamsMap);
            }else{
                logger.error("微信号："+weixinNames+"，未获取到该微信公众号详情，且向小组添加该工作微信号失败！");
            }
			*/
        } catch (Exception e) {
            logger.error("获取代码出错:", e);
        }

        if (ret_json != null) {
            try {

                JSONObject jo = new JSONObject(ret_json);

                int returnCode = jo.getInt("returnCode");
                //String returnMsg = jo.getString("returnMsg");

                logger.info("\n\nAdd to gsdata WeixinHao. ReturnCode=" + returnCode + "\t" + ret_json);

                if (returnCode == 1001)
                    return true;

            } catch (Exception e) {
                logger.error("获取代码出错:", e);
            }
        }

        return false;
    }

    /**
     * 添加微信公众账号到分组并加入监测，返回公众号相关信息（只适用人民日报）
     *
     * @param originUrl 待添加的微信公众号的文章地址： "url":"http://mp.weixin.qq.com/s?__biz=MTI0MDU3NDYwMQ==&mid=237223945&idx=4&sn=4dc9b60a32cc91e516bcb5983116314e&3rd=MzA3MDU4NTYzMw==&scene=6#rd"
     * @return
     */
    public GroupMonitorAddResult addWeixin2Group(String originUrl) {
        GroupMonitorAddResult result = null;

        try {
            ApiSdk dataApi = ApiSdk.getApiSdk(gsdata_appID, gsdata_appKey);
            Map<String, Object> requestParamsMap = new HashMap<String, Object>();
            requestParamsMap.put("url", originUrl);
            String ret_json = dataApi.callInterFace(CustomAPI_add_groupandmonitor, requestParamsMap);
            JSONObject jsonObject = new JSONObject(ret_json);

            JSONObject returnData = jsonObject.getJSONObject("returnData");

            logger.info("\n\nAdd to gsdata WeixinHao.response:" + ret_json);

            result = new GroupMonitorAddResult();
            if (returnData.has("errmsg"))
                result.setErrmsg(returnData.getString("errmsg"));
            if (returnData.has("errcode"))
                result.setErrcode(returnData.getInt("errcode"));
            if (returnData.has("wx_nickname"))
                result.setWxNickname(returnData.getString("wx_nickname"));
            if (returnData.has("wx_name"))
                result.setWxName(returnData.getString("wx_name"));
            if (returnData.has("biz"))
                result.setBiz(returnData.getString("biz"));

        } catch (Exception e) {
            logger.error("获取代码出错:", e);
        }


        return result;
    }


    /**
     * 删除微信号到分组
     *
     * @param weixinNames
     * @return
     */
    public boolean delWeixin2Group(String weixinNames) {
        String ret_json = null;
        try {
            ApiSdk dataApi = ApiSdk.getApiSdk(gsdata_appID, gsdata_appKey);
            Map<String, Object> requestParamsMap = new HashMap<String, Object>();
            requestParamsMap.put("wx_name", weixinNames);
            ret_json = dataApi.callInterFace(CustomAPI_addname_weixin2group, requestParamsMap);

            JsonParser json = new JsonParser();
            String id = json.parse(ret_json).getAsJsonObject().getAsJsonObject("returnData").get("id").getAsString();

            requestParamsMap = new HashMap<String, Object>();
            requestParamsMap.put("wxJson", "[ {\"nickname_id\":\"" + id + "\"}]");

            //调用接口
            ret_json = dataApi.callInterFace(CustomAPI_del_weixin2group, requestParamsMap);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (ret_json != null) {
            try {
                JSONObject jo = new JSONObject(ret_json);
                int returnCode = jo.getInt("returnCode");
                //String returnMsg = jo.getString("returnMsg");
                logger.info("\n\nAdd to gsdata WeixinHao. ReturnCode=" + returnCode + "\t" + ret_json);

                if (returnCode == 1001)
                    return true;

            } catch (Exception e) {
                logger.error("获取代码出错:", e);
            }
        }
        return false;
    }

    /**
     * 根据关键词搜索公众号文章
     *
     * @param keyword 搜索的关键词
     * @param start   默认为0
     */
    public static String getContentKeywordSearch(String keyword, int start) {
        try {
            ApiSdk dataApi = ApiSdk.getApiSdk(gsdata_appID, gsdata_appKey);
            Map<String, Object> requestParamsMap = new HashMap<String, Object>();

            // 搜索的关键词
            requestParamsMap.put("keyword", keyword);
            // 搜索结果开始位置（默认为0,start=start+num 假定num=10 例：0,10;10,10;20,10;30,10...）
            requestParamsMap.put("start", start);
            // 开始时间(格式：yyyy-MM-dd)
            requestParamsMap.put("startdate", DateFormatUtils.format(
                    DateUtils.addDays(new Date(), -2), "yyyy-MM-dd"));
            // 结束时间(格式：yyyy-MM-dd)
            requestParamsMap.put("enddate", DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
            // 排序条件-字段[readnum|likenum|readnum_pm|likenum_pm|readnum_week|likenum_week|posttime]
            requestParamsMap.put("sortname", "readnum");
            // sort
            requestParamsMap.put("sort", "desc");
            // 调用接口
            String jsonResponeData = dataApi.callInterFace(CUSTOMAPI_CONTENT_KEYWORD_SEARCH, requestParamsMap);

            logger.debug(CUSTOMAPI_CONTENT_KEYWORD_SEARCH + " params:" + requestParamsMap
                    + " response:" + jsonResponeData);

            return jsonResponeData;

        } catch (Exception e) {
            logger.error(CUSTOMAPI_CONTENT_KEYWORD_SEARCH + "获取代码出错:", e);
        }
        return null;
    }

    /**
     * api接口实时获取文章正文
     */
    public static String getWeixinContentByUrls(List<String> contentUrlList) {
        String result = null;
        Map<String, Object> requestParamsMap = new HashMap<>();
        try {
            //
            ApiSdk dataApi = ApiSdk.getApiSdk(gsdata_appID, gsdata_appKey);
            // 获取URL的地址
            requestParamsMap.put("url", StringUtils.join(contentUrlList, ","));
            // 调用接口
            result = dataApi.callInterFace(CUSTOMAPI_CONTENT, requestParamsMap);
            logger.info("url:" + requestParamsMap + " api:" + CUSTOMAPI_CONTENT + " response json:" + result);

        } catch (Exception ex) {
            // 输出日志
            logger.error(CUSTOMAPI_CONTENT + "获取代码出错:", ex);
        }
        return result;
    }

    /**
     * Main Entrys
     *
     * @param args
     */
    public static void main(String[] args) {

        DataApi spider = new DataApi();

        logger.info("iWeixin Starts at :" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        ArrayList<OriginNews> articleList = new ArrayList<OriginNews>();

        //2. 获取接口数据，并解析成文章
        int pageNo = 2;
        while (true) {
            String articlePage = spider.getResponseData2();

            ArrayList<OriginNews> list = null;
            if (articlePage != null) {
                logger.info("articlePage=" + articlePage);

                list = spider.parseArticleList(articlePage);

            }

            if (list != null)
                articleList.addAll(list);

            if (list == null || list.size() < MaxRows_Request) {
                logger.info("Null or less than 10 items, " + pageNo + " should be the last page. break.");
                break;
            }

            pageNo++;

            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                ;
            }

        }

        totalArticlesBeGet = articleList.size();

        if (articleList.size() == 0) {
            logger.fatal("NO ARTICLES BE FOUND FROM THE API. Return.");

            return;
        }
    }

    /**
     * 主要是供清博实时跟踪的接口的调用
     *
     * @param key       用于搜索的关键词的Key
     * @param keyword   用于搜索的关键词
     * @param expiryNum 搜索的时长，单位是天，结束时间为开始时间+ expiry_num
     */
    public PdmiKeyWordAddResult addPdmiKeyWord(String key, String keyword, int expiryNum) {

        String jsonResponeData = "";
        Map<String, Object> requestParamsMap = new HashMap<>();

        try {

            // 搜索的关键词
            requestParamsMap.put("keyword", keyword);
            //用于搜索的关键词的Key
            requestParamsMap.put("key", key);
            // 搜索的时长，单位是天，结束时间为开始时间+ expiry_num
            requestParamsMap.put("expiry_num", expiryNum);
            // 操作类型
            requestParamsMap.put("opt_type", 1);
            // 调用接口
            jsonResponeData = searchRealKeyWord(requestParamsMap);

            if (StringUtils.isNotBlank(jsonResponeData)) {
                JSONObject jsonObject = new JSONObject(jsonResponeData);
                // int returnCode = jo.getInt("returnCode");

                JSONObject returnData = jsonObject.getJSONObject("returnData");
                //  初始化返回的内容
                PdmiKeyWordAddResult result = new PdmiKeyWordAddResult();
                if (returnData.has("key"))
                    result.setKey(returnData.getString("key"));
                if (returnData.has("keyword"))
                    result.setKeyWord(returnData.getString("keyword"));
                if (returnData.has("starttime"))
                    result.setStartTime(returnData.getLong("starttime"));
                if (returnData.has("endtime"))
                    result.setEndTime(returnData.getLong("endtime"));
                if (returnData.has("create_time"))
                    result.setCreateTime(returnData.getLong("create_time"));

                logger.debug(API_PDMI_KEYWORD + " params:" + requestParamsMap
                        + "\n response:" + jsonResponeData);

                // 返回结果
                return result;
            }
            logger.info(API_PDMI_KEYWORD + " params:" + requestParamsMap
                    + "\n response is null:" + jsonResponeData);

        } catch (Exception e) {
            logger.error(API_PDMI_KEYWORD + "  参数为:"
                    + new Gson().toJson(requestParamsMap) + "\n response:" + jsonResponeData + "\n获取代码出错:", e);
        }
        return null;
    }

    /**
     * 主要是供清博实时跟踪的接口的调用
     *
     * @param key       用于搜索的关键词的Key,可以为空
     * @param pageIndex 当前获取的页码
     * @param pageSize  每页返回的数据量大小。最大为50
     */
    public PdmiKeyWordGetResult getPdmiKeyWordList(String key, int pageIndex, int pageSize) {
        try {
            Map<String, Object> requestParamsMap = new HashMap<String, Object>();

            //用于搜索的关键词的Key
            if (StringUtils.isNotBlank(key)) {
                requestParamsMap.put("key", key);
            }
            // 操作类型
            requestParamsMap.put("opt_type", 2);
            // 当前获取的页码
            requestParamsMap.put("page_index", pageIndex);
            // 默认是50
            if (pageSize <= 0 || pageSize > 50) {
                pageSize = 50;
            }
            // 每页返回的数据量大小。最大为50
            requestParamsMap.put("page_size", pageSize);
            // 调用接口
            String jsonResponeData = searchRealKeyWord(requestParamsMap);

            if (StringUtils.isNotBlank(jsonResponeData)) {
                JSONObject jsonObject = new JSONObject(jsonResponeData);
                // int returnCode = jo.getInt("returnCode");

                JSONArray returnData = jsonObject.getJSONArray("returnData");
                if (returnData.length() > 0) {

                    PdmiKeyWordGetResult result = new PdmiKeyWordGetResult();
                    List<PdmiKeyWordAddResult> records = new ArrayList<>();
                    PdmiKeyWordAddResult record = null;
                    for (int i = 0; i < returnData.length(); i++) {
                        JSONObject item = (JSONObject) returnData.get(i);
                        //  初始化返回的内容
                        record = new PdmiKeyWordAddResult();
                        if (item.has("key"))
                            record.setKey(item.getString("key"));
                        if (item.has("keyword"))
                            record.setKeyWord(item.getString("keyword"));
                        if (item.has("starttime"))
                            record.setStartTime(item.getLong("starttime"));
                        if (item.has("endtime"))
                            record.setEndTime(item.getLong("endtime"));
                        if (item.has("create_time"))
                            record.setCreateTime(item.getLong("create_time"));
                        records.add(record);
                    }
                    result.setRecords(records);
                    // 返回结果
                    return result;
                }
            }
            logger.debug(API_PDMI_KEYWORD + " params:" + requestParamsMap
                    + " response:" + jsonResponeData);

        } catch (Exception e) {
            logger.error(API_PDMI_KEYWORD + "获取代码出错:", e);
        }
        return null;
    }

    /**
     * 主要是供清博实时跟踪的接口的调用
     *
     * @param key       用于搜索的关键词的Key,可以为空
     * @param pageIndex 当前获取的页码
     * @param pageSize  每页返回的数据量大小。最大为50
     * @param searchDay 需要查询的日期 可以为空，格式为2017-05-25,如果为空，表示只查询当天
     */
    public PdmiKeyWordGetResult getPdmiKeyWordList(String key, int pageIndex, int pageSize, String searchDay) {
        try {
            Map<String, Object> requestParamsMap = new HashMap<String, Object>();

            //用于搜索的关键词的Key
            if (StringUtils.isNotBlank(key)) {
                requestParamsMap.put("key", key);
            }
            // 操作类型
            requestParamsMap.put("opt_type", 2);
            // 当前获取的页码
            requestParamsMap.put("page_index", pageIndex);
            // 默认是50
            if (pageSize <= 0 || pageSize > 50) {
                pageSize = 50;
            }
            // 每页返回的数据量大小。最大为50
            requestParamsMap.put("page_size", pageSize);

            long datetime = 0l;
            // 判断查询日期的内容
            if (StringUtils.isBlank(searchDay)) {
                // 查询当天的数据
                datetime = DateUtils.parseDate(DateFormatUtils.format(new Date(),
                        "yyyy-MM-dd") + " 00:00:00", new String[]{"yyyy-MM-dd HH:mm:ss"}).getTime();
            } else {
                // 转换成需要查询的那一天的数据
                datetime = DateUtils.parseDate(searchDay + " 00:00:00", new String[]{"yyyy-MM-dd HH:mm:ss"}).getTime();
            }
            // 添加查询的时间
            requestParamsMap.put("datetime", String.valueOf(datetime));

            // 调用接口
            String jsonResponeData = searchRealKeyWord(requestParamsMap);

            if (StringUtils.isNotBlank(jsonResponeData)) {
                JSONObject jsonObject = new JSONObject(jsonResponeData);
                // int returnCode = jo.getInt("returnCode");

                JSONArray returnData = jsonObject.getJSONArray("returnData");
                if (returnData.length() > 0) {

                    PdmiKeyWordGetResult result = new PdmiKeyWordGetResult();
                    List<PdmiKeyWordAddResult> records = new ArrayList<>();
                    PdmiKeyWordAddResult record = null;
                    for (int i = 0; i < returnData.length(); i++) {
                        JSONObject item = (JSONObject) returnData.get(i);
                        //  初始化返回的内容
                        record = new PdmiKeyWordAddResult();
                        if (item.has("key"))
                            record.setKey(item.getString("key"));
                        if (item.has("keyword"))
                            record.setKeyWord(item.getString("keyword"));
                        if (item.has("starttime"))
                            record.setStartTime(item.getLong("starttime"));
                        if (item.has("endtime"))
                            record.setEndTime(item.getLong("endtime"));
                        if (item.has("create_time"))
                            record.setCreateTime(item.getLong("create_time"));
                        records.add(record);
                    }
                    result.setRecords(records);
                    // 返回结果
                    return result;
                }
            }
            logger.debug(API_PDMI_KEYWORD + " params:" + requestParamsMap
                    + " response:" + jsonResponeData);

        } catch (Exception e) {
            logger.error(API_PDMI_KEYWORD + "获取代码出错:", e);
        }
        return null;
    }

    /**
     * 清博数据的删除接口
     *
     * @param key 用于搜索的关键词的Key
     */
    public String deletePdmiKeyWord(String key) {
        try {
            Map<String, Object> requestParamsMap = new HashMap<String, Object>();
            //用于搜索的关键词的Key
            requestParamsMap.put("key", key);
            // 操作类型
            requestParamsMap.put("opt_type", 3);
            // 调用接口
            String jsonResponeData = searchRealKeyWord(requestParamsMap);

            if (StringUtils.isNotBlank(jsonResponeData)) {
                JSONObject jsonObject = new JSONObject(jsonResponeData);
                // int returnCode = jo.getInt("returnCode");

                JSONObject returnData = jsonObject.getJSONObject("returnData");
                if (returnData.has("status")) {
                    return returnData.getString("status");
                }
            }
            logger.debug(API_PDMI_KEYWORD + " params:" + requestParamsMap
                    + " response:" + jsonResponeData);

        } catch (Exception e) {
            logger.error(API_PDMI_KEYWORD + "获取代码出错:", e);
        }
        return null;
    }

    /**
     * 获取实时数据的接口数据
     *
     * @param requestParamsMap 传入的参数
     * @return 返回的结果内容
     */
    private String searchRealKeyWord(Map<String, Object> requestParamsMap) {

        String jsonResponeData = null;
        try {
            ApiSdk dataApi = ApiSdk.getApiSdk(gsdata_appID, gsdata_appKey);

            // 调用接口
            jsonResponeData = dataApi.callInterFace(API_PDMI_KEYWORD, requestParamsMap);

            logger.debug(API_PDMI_KEYWORD + " params:" + requestParamsMap + " response:" + jsonResponeData);

        } catch (Exception e) {
            logger.error(API_PDMI_KEYWORD + "获取代码出错:", e);
        }
        return jsonResponeData;
    }

    /**
     * 根据微信号获取清博微信信息
     *
     * @param wxname
     * @return
     */
    public String getInfoByWxname(String wxname) {
        ApiSdk dataApi = ApiSdk.getApiSdk(gsdata_appID, gsdata_appKey);
        Map<String, Object> requestParamsMap = new HashMap<String, Object>();
        requestParamsMap.put("wx_name", wxname);
        String ret_json = dataApi.callInterFace(CustomAPI_addname_weixin2group, requestParamsMap);
        return ret_json;
    }

    /**
     * 实时获取单篇微信文章阅读数点赞数
     *
     * @param url 需要获取的URL地址
     * @return 返沪获取的结果
     */
    public WxNumsMonitorResult getWxNumsMonitorData(String url) {
        WxNumsMonitorResult result = null;
        // 调用接口
        String jsonResponeData = null;
        try {
            ApiSdk dataApi = ApiSdk.getApiSdk(gsdata_appID, gsdata_appKey);

            Map<String, Object> requestParamsMap = new HashMap<String, Object>();
            //用于搜索的关键词的Key
            requestParamsMap.put("url", url);
            // 调用接口
            jsonResponeData = dataApi.callInterFace(API_WX_NUMS_MONITOR, requestParamsMap);

            if (StringUtils.isNotBlank(jsonResponeData)) {
                JSONObject jsonObject = new JSONObject(jsonResponeData);

                JSONObject returnData = jsonObject.getJSONObject("returnData");
                if (returnData.length() > 0) {
                    result = new WxNumsMonitorResult();
                    // 	结果描述（0:默认成功，其他见接口返回）
                    if (returnData.has("status"))
                        result.setStatus(returnData.getInt("status"));
                    // 	点赞数
                    if (returnData.has("likenum"))
                        result.setLikeNum(returnData.getInt("likenum"));
                    // 真实阅读数（针对10w+阅读量）
                    if (returnData.has("real_read_num"))
                        result.setRealReadNum(returnData.getInt("real_read_num"));
                    // 阅读数
                    if (returnData.has("readnum"))
                        result.setReadNum(returnData.getInt("readnum"));

                    // 返回结果
                    return result;
                }
            }
            // 打出日志
            logger.debug(API_WX_NUMS_MONITOR + " params:" + requestParamsMap + " response:" + jsonResponeData);

        } catch (Exception e) {
            logger.error(API_WX_NUMS_MONITOR + "response data:" + jsonResponeData + "获取代码出错:", e);
            result = null;
        }
        return result;
    }
}
