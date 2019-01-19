package org.pcchen.uar.utils;

/**
 * ???GSData??????????????????????????????????????��??
 *
 * @author
 */
public class DataApi {
    //    //???????
////    private static Logger logger = Logger.getLogger(iims.crt.gsdata.DataApi.class.getName());
//
//    //???ApiSdk ????????
//    private ApiSdk apiSdk = null;
//
//    //??????????
    //uar-wechat
//    public static String gsdata_appID = "5ko4v23gv89Ue61C7ss7";
//    public static String gsdata_appKey = "1UXqUKFyznnBLqkaD54liqMyr";
    //crt
    public static String gsdata_appID = "mzgvw1O7nYnglPvhjHST";
    public static String gsdata_appKey = "0h3mrJcord72zHOeJY4TnTFqO";
//    // public static String CustomAPI_wx_url_monitor = "http://open.gsdata.cn/api/custom/customapi/wx_url_monitor";
//    public static String CustomAPI_wx_url_monitor = "http://open.gsdata.cn/api/custom/customapi/wx_url_content_monitor";
//    public static String CustomAPI_wx_url_2 = "http://open.gsdata.cn/api/custom/customapi/wx_articles_by_wxname";
//    public static String CustomAPI_wx_url_3 = "http://open.gsdata.cn/api/wx/opensearchapi/nickname_keyword_search";
//    public static String CustomAPI_wx_url_4 = "http://open.gsdata.cn/api/wx/wxapi/wx_week_readnum";
//
//    public static String CustomAPI_wx_content = "http://open.gsdata.cn/api/wx/wxapi/wx_content";
//    //	public static String CustomAPI_add_weixin2group = "http://open.gsdata.cn/api/custom/customapi/addWXname2Group";
//    public static String CustomAPI_add_weixin2group = "http://open.gsdata.cn/api/wx/wxapi/add_wx_to_groupMonitor";
//    public static String CustomAPI_addname_weixin2group = "http://open.gsdata.cn/api/wx/wxapi/nickname_one";
//    public static String CustomAPI_del_weixin2group = "http://open.gsdata.cn/api/wx/wxapi/del_wx_from_groupMonitor";
public static String CustomAPI_add_weixin2group_byurl = "http://open.gsdata.cn/api/wx/wxapi/add_wx_to_group_by_url";//????????????????��??????????????
    //    public static String CustomAPI_add_groupandmonitor = "http://open.gsdata.cn/api/custom/customapi/add_wx_to_group_monitor";//????????????????��????????????????????
//
//
    public static String get_all_group = "http://open.gsdata.cn/api/wx/wxapi/group_name";//???UID???????????
    // query
    public static String query_readnum_detail = "http://open.gsdata.cn/api/custom/customapi/query_readnum_detail";
    //获取实时文章接口
    public static String wx_url_monitor = "http://open.gsdata.cn/api/custom/customapi/wx_url_monitor";
    //通过文章URL获取该文章评论信息
    public static String wx_comment_by_url = "http://open.gsdata.cn/api/wx/wxapi2/wx_comment_by_url";
//
//    /**
//     * ????????????
//     */
//    public static String API_PDMI_KEYWORD = "http://open.gsdata.cn/api/pdmi/pdmi_keyword";
//
//    /**
//     * ??????????????????????????
//     */
//    public static String API_WX_NUMS_MONITOR = "http://open.gsdata.cn/api/wx/wxapi/wx_nums_monitor";
//
//    /**
//     * ?????????????????????
//     */
//    public static String CUSTOMAPI_CONTENT_KEYWORD_SEARCH
//            = "http://open.gsdata.cn/api/wx/opensearchapi/content_keyword_search";
//
//    /**
//     * ???????????????????url????��?????????????
//     */
//    public static String CUSTOMAPI_CONTENT = "http://open.gsdata.cn/api/wx/wxapi/wx_content2";
//
//
//    public static int gsdata_groupID = 27657;
//
//    //??��????????????
//    public static int MaxRows_Request = 10;
//
//    //???????????????
//    public static int totalArticles = 0;
//    //???????????????
//    public static int totalArticlesBeGet = 0;
//    //???????????
//    public static int totalUpdatedArticles = 0;
//    //?????????????
//    public static int newArticlesBeSaved = 0;
//
//
//    private static DataApi apiService = null;
//
//    /**
//     * ????
//     */
//    public static synchronized DataApi getInstance() {
//        if (apiService == null)
//            apiService = new DataApi();
//        return apiService;
//    }
//
//    private DataApi() {
////        logger.info("initializing the GSDATA API.");
//
//        apiSdk = ApiSdk.getApiSdk(gsdata_appID, gsdata_appKey);
//    }
//
//    /**
//     * ????Json??��???Article?��?
//     *
//     * @param strJson
//     */
//    public ArrayList<OriginNews> parseArticleList(String strJson) {
//        ArrayList<OriginNews> list = new ArrayList<OriginNews>();
//
//        try {
//            JSONObject jo = new JSONObject(strJson);
//
//            int returnCode = jo.getInt("returnCode");
//            String returnMsg = jo.getString("returnMsg");
//
//            logger.info("ReturnCode=" + returnCode + "\t" + returnMsg);
//
//            JSONObject dataObject = jo.getJSONObject("returnData");
//
//            totalArticles = dataObject.getInt("total");
//            JSONArray articleListArray = dataObject.getJSONArray("rows");
//
//            for (int i = 0; i < articleListArray.length(); i++) {
//                JSONObject ao = (JSONObject) articleListArray.get(i);
//
//                OriginNews oriArticle = new OriginNews();
//                Website media = new Website();
//                media.setWebName(ao.getString("name"));
//                media.setReservedTag(ao.getString("wx_name"));
//
//                ScanPanel scanPanel = new ScanPanel();
//                MediaChannel channel = new MediaChannel();
//                channel.setMedia(media);
//                channel.setScanPanel(scanPanel);
//
//                oriArticle.setChannel(channel);
//
//                oriArticle.setSubject(ao.getString("title"));
//                oriArticle.setUrl(ao.getString("url"));
//                oriArticle.setPostTime(ao.getString("posttime"));
//
//                FeedbackStat feedbackStat = new FeedbackStat();
//                feedbackStat.setReadCount(ao.getInt("readnum"));
//                feedbackStat.setAgreeCount(ao.getInt("likenum"));
//                oriArticle.setFeedbackStat(feedbackStat);
//
//                oriArticle.setContent(ao.getString("content"));
//
//                logger.info(oriArticle.getFeedbackStat().getAgreeCount() + "/" + oriArticle.getFeedbackStat().getReadCount() + "\t" + oriArticle.getChannel().getMedia().getReservedTag() + "\t" + oriArticle.getChannel().getMedia().getWebName() + "\t" + oriArticle.getSubject());
//
//                list.add(oriArticle);
//            }
//        } catch (Exception e) {
//            logger.error("return json:" + strJson + "\n ??????????:", e);
//        }
//
//        return list;
//    }
//
//    /**
//     * ???GSDATA SDK????
//     *
//     * @return
//     */
//    public String getResponseData(int pageNo) {
//        String ret_json = null;
//
//        try {
//            /*
//             * ???????? ??????????????appid??appkey,??????��???????????????????????
//             * ?????��?????????????????????????????? ????appid??appkey
//             */
//            Map<String, Object> requestParamsMap = new HashMap<String, Object>();
//
//            SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd");
//            String yesterday = formats.format(new Date().getTime() - 1 * 24 * 60 * 60 * 1000);
//
//            requestParamsMap.put("beginDate", yesterday);
//            requestParamsMap.put("group_id", gsdata_groupID);
//            requestParamsMap.put("page", pageNo);
//
//            //??????
//            ret_json = apiSdk.callInterFace(CustomAPI_wx_url_monitor, requestParamsMap);
//
//            logger.info(ret_json);
//
//            //??Utf8?????gbk
//            //ret_json = StringUtil.utf82gbk(ret_json);
//
//        } catch (Exception e) {
//            logger.error("??????????:", e);
//        }
//
//        return ret_json;
//    }
//    /**
//     * ???????
//     *
//     * @return
//     */
//    public String getResponseData2() {
//        String ret_json = null;
//
//        try {
//            /*
//             * ???????? ??????????????appid??appkey,??????��???????????????????????
//             * ?????��?????????????????????????????? ????appid??appkey
//             */
//            Map<String, Object> requestParamsMap = new HashMap<String, Object>();
//
//            SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd");
//            String yesterday = formats.format(new Date().getTime() - 1 * 24 * 60 * 60 * 1000);
//
//            requestParamsMap.put("beginDate", "2018-09-12 00:00:00");
//            requestParamsMap.put("endDate", "2018-09-12 24:00:00");
//            requestParamsMap.put("wxName", "rmrbwx");
//
//            //??????
//            ret_json = apiSdk.callInterFace(CustomAPI_wx_url_2, requestParamsMap);
//
//            logger.info(ret_json);
//
//            //??Utf8?????gbk
//            //ret_json = StringUtil.utf82gbk(ret_json);
//
//        } catch (Exception e) {
//            logger.error("??????????:", e);
//        }
//
//        return ret_json;
//    }
//    /**
//     * ???????3
//     *
//     * @return
//     */
//    public String getResponseData3() {
//        String ret_json = null;
//
//        try {
//            /*
//             * ???????? ??????????????appid??appkey,??????��???????????????????????
//             * ?????��?????????????????????????????? ????appid??appkey
//             */
//            Map<String, Object> requestParamsMap = new HashMap<String, Object>();
//
//            SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd");
//            String yesterday = formats.format(new Date().getTime() - 1 * 24 * 60 * 60 * 1000);
//
////            requestParamsMap.put("keyword", "???????");
////            requestParamsMap.put("start", "0");
////            requestParamsMap.put("num", "10");
//
////            requestParamsMap.put("start_time", "2018-12-10");
////            requestParamsMap.put("end_time", "2018-12-17");
////            requestParamsMap.put("nickname_id", "5253380");
////            requestParamsMap.put("page", 0);
////            requestParamsMap.put("rows", 10);
//
//            requestParamsMap.put("keyword", "?��?");
//            requestParamsMap.put("start", 0);
//            requestParamsMap.put("num", 10);
//
//            //??????
////            ret_json = apiSdk.callInterFace(CustomAPI_wx_url_4, requestParamsMap);
//            ret_json = apiSdk.callInterFace(CustomAPI_wx_url_3, requestParamsMap);
//
//            logger.info(ret_json);
//
//            //??Utf8?????gbk
//            //ret_json = StringUtil.utf82gbk(ret_json);
//
//        } catch (Exception e) {
//            logger.error("??????????:", e);
//        }
//
//        return ret_json;
//    }
//
//    /**
//     * ?????????????
//     *
//     * @param page        ?????????????????0??
//     * @param nicknameIds ????????ID?????????(?????1001,1002,1003)
//     * @return
//     */
//    public String getResponseData(int page, String nicknameIds) {
//        String ret_json = null;
//
//        try {
//            /*
//             * ???????? ??????????????appid??appkey,??????��???????????????????????
//             * ?????��?????????????????????????????? ????appid??appkey
//             */
//            Map<String, Object> requestParamsMap = new HashMap<String, Object>();
//
//            SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd");
//            String yesterday = formats.format(new Date().getTime() - 1 * 24 * 60 * 60 * 1000);
//
//            requestParamsMap.put("beginDate", yesterday);
//            // requestParamsMap.put("group_id", gsdata_groupID);
//            requestParamsMap.put("nickname_ids", nicknameIds);
//            requestParamsMap.put("page", page);
//
//            //??????
//            ret_json = apiSdk.callInterFace(CustomAPI_wx_url_monitor, requestParamsMap);
//
//            logger.info(ret_json);
//
//            //??Utf8?????gbk
//            //ret_json = StringUtil.utf82gbk(ret_json);
//
//        } catch (Exception e) {
//            logger.error("??????????:", e);
//        }
//
//        return ret_json;
//    }
//
//    /**
//     * ?????????????
//     *
//     * @param page        ?????????????????0??
//     * @param nicknameIds ????????ID?????????(?????1001,1002,1003)
//     * @param beginDate   ????????? (?????yyyy-MM-dd ????????????)
//     * @param endDate     ?????????? (?????yyyy-MM-dd ??????????????)
//     * @return
//     */
//    public String getResponseData(int page, String nicknameIds, String beginDate, String endDate) {
//        String ret_json = null;
//
//        try {
//            /*
//             * ???????? ??????????????appid??appkey,??????��???????????????????????
//             * ?????��?????????????????????????????? ????appid??appkey
//             */
//            Map<String, Object> requestParamsMap = new HashMap<String, Object>();
//
//            requestParamsMap.put("beginDate", beginDate);
//            requestParamsMap.put("endDate", endDate);
//            // requestParamsMap.put("group_id", gsdata_groupID);
//            requestParamsMap.put("nickname_ids", nicknameIds);
//            requestParamsMap.put("page", page);
//
//            //??????
//            ret_json = apiSdk.callInterFace(CustomAPI_wx_url_monitor, requestParamsMap);
//
//            logger.info(ret_json);
//
//            //??Utf8?????gbk
//            //ret_json = StringUtil.utf82gbk(ret_json);
//
//        } catch (Exception e) {
//            logger.error("??????????:", e);
//        }
//
//        return ret_json;
//    }
//
//    /**
//     * ?????????????
//     *
//     * @param page        ?????????????????0??
//     * @param nicknameIds ????????ID?????????(?????1001,1002,1003)
//     * @param beginDate   ????????? (?????yyyy-MM-dd ????????????)
//     * @param endDate     ?????????? (?????yyyy-MM-dd ??????????????)
//     * @return
//     */
//    public List<WxUrlMonitorResult> getResponseData(int page, String nicknameIds, Date beginDate, Date endDate) {
//        List<WxUrlMonitorResult> results = null;
//
//        try {
//            /*
//             * ???????? ??????????????appid??appkey,??????��???????????????????????
//             * ?????��?????????????????????????????? ????appid??appkey
//             */
//            Map<String, Object> requestParamsMap = new HashMap<>();
//            // ?��??????
//            if (beginDate == null) {
//                beginDate = new Date();
//            }
//            requestParamsMap.put("beginDate", DateFormatUtils.format(beginDate, "yyyy-MM-dd"));
//            // ?��??????
//            if (endDate == null) {
//                endDate = new Date();
//            }
//            requestParamsMap.put("endDate", DateFormatUtils.format(endDate, "yyyy-MM-dd"));
//            // requestParamsMap.put("group_id", gsdata_groupID);
//            requestParamsMap.put("nickname_ids", nicknameIds);
//            requestParamsMap.put("page", page);
//
//            //??????
//            String ret_json = apiSdk.callInterFace(CustomAPI_wx_url_monitor, requestParamsMap);
//            // log
//            logger.info(MessageFormat.format("params is {0}, \n response data is {1}",
//                    new Gson().toJson(requestParamsMap), ret_json));
//
//            JSONObject jo = new JSONObject(ret_json);
//
//            JSONObject dataObject = jo.getJSONObject("returnData");
//            JSONArray articleListArray = dataObject.getJSONArray("rows");
//
//            if (articleListArray.length() > 0) {
//                results = new ArrayList<>();
//                WxUrlMonitorResult record = null;
//                for (int i = 0; i < articleListArray.length(); i++) {
//                    JSONObject returnData = (JSONObject) articleListArray.get(i);
//                    record = new WxUrlMonitorResult();
//                    // 	???????????
//                    if (returnData.has("name"))
//                        record.setName(returnData.getString("name"));
//
//                    // 	??????????
//                    if (returnData.has("wx_name"))
//                        record.setWxName(returnData.getString("wx_name"));
//
//                    // 	????????ID
//                    if (returnData.has("nickname_id"))
//                        record.setNickNameId(returnData.getInt("nickname_id"));
//
//                    // 	???��??????
//                    if (returnData.has("posttime"))
//                        record.setPostTime(returnData.getString("posttime"));
//
//                    // 		???��???
//                    if (returnData.has("title"))
//                        record.setTitle(returnData.getString("title"));
//
//                    // 	????????
//                    if (returnData.has("content"))
//                        record.setContent(returnData.getString("content"));
//
//                    // 	?????????
//                    if (returnData.has("url"))
//                        record.setUrl(returnData.getString("url"));
//
//                    // 		??????????
//                    if (returnData.has("add_time"))
//                        record.setAddTime(returnData.getString("add_time"));
//
//                    // 	?????????
//                    if (returnData.has("monitor_time"))
//                        record.setMonitorTime(returnData.getString("monitor_time"));
//
//                    // 	?????????
//                    if (returnData.has("readnum"))
//                        record.setReadNum(returnData.getInt("readnum"));
//
//                    // 	?????????
//                    if (returnData.has("likenum"))
//                        record.setLikeNum(returnData.getInt("likenum"));
//
//                    // 	????��??
//                    if (returnData.has("top"))
//                        record.setLikeNum(returnData.getInt("top"));
//
//                    // 	??????
//                    if (returnData.has("ispush"))
//                        record.setIsPush(returnData.getInt("ispush"));
//
//                    // 	??????
//                    if (returnData.has("picurl"))
//                        record.setPicUrl(returnData.getString("picurl"));
//
//                    // 	?????
//                    if (returnData.has("sourceurl"))
//                        record.setSourceUrl(returnData.getString("sourceurl"));
//
//                    // 	????????
//                    if (returnData.has("author"))
//                        record.setAuthor(returnData.getString("author"));
//
//                    // 	????? ( ??? : ??????????URL???)
//                    if (returnData.has("desc"))
//                        record.setDesc(returnData.getString("desc"));
//
//                    // 	?????? ( ??? : ??????????URL??? )
//                    if (returnData.has("videourl"))
//                        record.setVideoUrl(returnData.getString("videourl"));
//
//                    // 	????? ( ??? : ??????????URL???)
//                    if (returnData.has("imgsurl"))
//                        record.setImgsUrl(returnData.getString("imgsurl"));
//
//                    results.add(record);
//                }
//            }
//
//        } catch (Exception e) {
//            logger.error("??????????:", e);
//        }
//
//        return results;
//    }
//
//    /**
//     * ???GSDATA SDK??????????
//     *
//     * @return
//     */
//    public String getWeixinContent(String gsWeixinUrl) {
//        String content = null;
//
//        try {
//            Map<String, Object> requestParamsMap = new HashMap<String, Object>();
//            requestParamsMap.put("url", gsWeixinUrl);
//
//            //??????
//            String ret_json = apiSdk.callInterFace(CustomAPI_wx_content, requestParamsMap);
//
//            //??Utf8?????gbk
//            //ret_json = StringUtil.utf82gbk(ret_json);
//
//            if (ret_json == null)
//                return null;
//
//            logger.debug(ret_json);
//            JSONObject jo = new JSONObject(ret_json);
//            // int returnCode = jo.getInt("returnCode");
//
//            JSONArray returnData = jo.getJSONArray("returnData");
//
//            if (returnData == null || returnData.length() == 0)
//                return null;
//
//            JSONObject ao = (JSONObject) returnData.get(0);
//            content = ao.getString("content");
//
//            logger.debug(gsWeixinUrl + "\t" + content);
//
//        } catch (Exception e) {
//            logger.error("??????????:", e);
//        }
//
//        return content;
//    }
//
//    /**
//     * ???????????????
//     *
//     * @param wxName ????
//     * @return
//     */
//    public ResNickNameOneResult getNickNameOne(String wxName) {
//        ResNickNameOneResult resNickNameOneResult = null;
//
//        try {
//            Map<String, Object> requestParamsMap = new HashMap<String, Object>();
//            requestParamsMap.put("wx_name", wxName);
//
//            //??????
//            String ret_json = apiSdk.callInterFace(CustomAPI_addname_weixin2group, requestParamsMap);
//
//            //??Utf8?????gbk
//            //ret_json = StringUtil.utf82gbk(ret_json);
//
//            if (ret_json == null)
//                return null;
//
//            logger.debug(ret_json);
//            JSONObject jo = new JSONObject(ret_json);
//            // int returnCode = jo.getInt("returnCode");
//            if (jo.has("returnData")) {
//                JSONObject returnData = jo.getJSONObject("returnData");
//
//                if (returnData == null || returnData.length() == 0)
//                    return null;
//
//                // ??????
//                resNickNameOneResult = new ResNickNameOneResult();
//                // ????????ID
//                if (returnData.has("id"))
//                    resNickNameOneResult.setId(returnData.getInt("id"));
//                // ??????????
//                if (returnData.has("wx_nickname"))
//                    resNickNameOneResult.setWxNickname(returnData.getString("wx_nickname"));
//
//                // ????
//                if (returnData.has("wx_openid"))
//                    resNickNameOneResult.setWxOpenid(returnData.getString("wx_openid"));
//
//                // ?????biz
//                if (returnData.has("wx_biz"))
//                    resNickNameOneResult.setWxBiz(returnData.getString("wx_biz"));
//
//                // ?????????
//                if (returnData.has("wx_type"))
//                    resNickNameOneResult.setWxType(returnData.getInt("wx_type"));
//
//                // ???????
//                if (returnData.has("wx_name"))
//                    resNickNameOneResult.setWxName(returnData.getString("wx_name"));
//
//                // ???????????
//                if (returnData.has("wx_qrcode"))
//                    resNickNameOneResult.setWxQrcode(returnData.getString("wx_qrcode"));
//
//                // ?????????
//                if (returnData.has("wx_note"))
//                    resNickNameOneResult.setWxNote(returnData.getString("wx_note"));
//
//                // ??????
//                if (returnData.has("wx_vip")) {
//                    resNickNameOneResult.setWxVip(returnData.getString("wx_vip"));
//                }
//
//                // ??????
//                if (returnData.has("wx_vip_note")) {
//                    resNickNameOneResult.setWxVipNote(returnData.getString("wx_vip_note"));
//                }
//
//                // ?????????????
//                if (returnData.has("wx_country")) {
//                    resNickNameOneResult.setWxCountry(returnData.getString("wx_country"));
//                }
//
//                // ????????????
//                if (returnData.has("wx_country"))
//                    resNickNameOneResult.setWxProvince(returnData.getString("wx_province"));
//
//                // ?????????????
//                if (returnData.has("wx_city"))
//                    resNickNameOneResult.setWxCity(returnData.getString("wx_city"));
//
//                // ???????��???
//                if (returnData.has("wx_title"))
//                    resNickNameOneResult.setWxTitle(returnData.getString("wx_title"));
//
//                // 	??????????
//                if (returnData.has("wx_url"))
//                    resNickNameOneResult.setWxUrl(returnData.getString("wx_url"));
//
//                // ???????��??????
//                if (returnData.has("wx_url_posttime"))
//                    resNickNameOneResult.setWxUrlPosttime(returnData.getString("wx_url_posttime"));
//
//                // 	???ID
//                if (returnData.has("uid"))
//                    resNickNameOneResult.setUid(returnData.getInt("uid"));
//
//                // ?????????
//                if (returnData.has("time_start"))
//                    resNickNameOneResult.setTimeSart(returnData.getString("time_start"));
//
//                // ??????????
//                if (returnData.has("time_end"))
//                    resNickNameOneResult.setTimeEnd(returnData.getString("time_end"));
//
//                // ???????
//                if (returnData.has("time_stop"))
//                    resNickNameOneResult.setTimeStop(returnData.getString("time_stop"));
//
//                // ??????
//                if (returnData.has("add_time"))
//                    resNickNameOneResult.setAddTime(returnData.getString("add_time"));
//
//                // 	??
//                if (returnData.has("status"))
//                    resNickNameOneResult.setStatus(returnData.getInt("status"));
//
//                // 	?????? 0???? 1????
//                if (returnData.has("isenable"))
//                    resNickNameOneResult.setIsEnable(returnData.getInt("isenable"));
//
//                // 	??????
//                if (returnData.has("update_status"))
//                    resNickNameOneResult.setUpdateStatus(returnData.getInt("update_status"));
//
//                logger.debug("\t" + resNickNameOneResult);
//            } else {
//                logger.error(MessageFormat.format("wexin code {0} get request error,return data is \n "
//                        , wxName) + ret_json);
//            }
//
//        } catch (Exception e) {
//            logger.error("get data error:", e);
//        }
//
//        return resNickNameOneResult;
//    }
//
//    /**
//     * ????????????
//     *
//     * @param weixinNames ????????????��?,?????
//     * @return
//     */
//    public boolean addWeixin2Group(String weixinNames, String originUrl) {
//        String ret_json = null;
//        try {
//            ApiSdk dataApi = ApiSdk.getApiSdk(gsdata_appID, gsdata_appKey);
//            Map<String, Object> requestParamsMap = new HashMap<String, Object>();
//            requestParamsMap.put("url", originUrl);
//            ret_json = dataApi.callInterFace(CustomAPI_add_groupandmonitor, requestParamsMap);
//             /*
//             requestParamsMap.put("wx_name",weixinNames);
//             ret_json = dataApi.callInterFace(CustomAPI_addname_weixin2group, requestParamsMap);
//
//             JsonParser json = new JsonParser();
//
//             JsonObject jsonObject = json.parse(ret_json).getAsJsonObject().getAsJsonObject("returnData");
//             if(jsonObject==null){//?��????returnData?null?????????��???????????
//             requestParamsMap = new HashMap<String, Object>();
//                 requestParamsMap.put("groupid",gsdata_groupID);
//                 requestParamsMap.put("url", originUrl);
//                 ret_json = dataApi.callInterFace(CustomAPI_add_weixin2group_byurl, requestParamsMap);
//                 requestParamsMap = new HashMap<String, Object>();
//                 requestParamsMap.put("wx_name",weixinNames);
//                 ret_json = dataApi.callInterFace(CustomAPI_addname_weixin2group, requestParamsMap);
//                 jsonObject = json.parse(ret_json).getAsJsonObject().getAsJsonObject("returnData");
//             }
//            if(jsonObject!=null){
//                String id = jsonObject.get("id").getAsString();
//
//                requestParamsMap = new HashMap<String, Object>();
//
//             requestParamsMap.put("wxJson", "[ {\"nickname_id\":\"" + id + "\"}]");
//
//             //??????
//             ret_json = dataApi.callInterFace(CustomAPI_add_weixin2group, requestParamsMap);
//            }else{
//                logger.error("?????"+weixinNames+"??��?????????????????�????��?????��???????????");
//            }
//			*/
//        } catch (Exception e) {
//            logger.error("??????????:", e);
//        }
//
//        if (ret_json != null) {
//            try {
//
//                JSONObject jo = new JSONObject(ret_json);
//
//                int returnCode = jo.getInt("returnCode");
//                //String returnMsg = jo.getString("returnMsg");
//
//                logger.info("\n\nAdd to gsdata WeixinHao. ReturnCode=" + returnCode + "\t" + ret_json);
//
//                if (returnCode == 1001)
//                    return true;
//
//            } catch (Exception e) {
//                logger.error("??????????:", e);
//            }
//        }
//
//        return false;
//    }
//
//    /**
//     * ????????????????��????????????????????????????????????
//     *
//     * @param originUrl ?????????????????????? "url":"http://mp.weixin.qq.com/s?__biz=MTI0MDU3NDYwMQ==&mid=237223945&idx=4&sn=4dc9b60a32cc91e516bcb5983116314e&3rd=MzA3MDU4NTYzMw==&scene=6#rd"
//     * @return
//     */
//    public GroupMonitorAddResult addWeixin2Group(String originUrl) {
//        GroupMonitorAddResult result = null;
//
//        try {
//            ApiSdk dataApi = ApiSdk.getApiSdk(gsdata_appID, gsdata_appKey);
//            Map<String, Object> requestParamsMap = new HashMap<String, Object>();
//            requestParamsMap.put("url", originUrl);
//            String ret_json = dataApi.callInterFace(CustomAPI_add_groupandmonitor, requestParamsMap);
//            JSONObject jsonObject = new JSONObject(ret_json);
//
//            JSONObject returnData = jsonObject.getJSONObject("returnData");
//
//            logger.info("\n\nAdd to gsdata WeixinHao.response:" + ret_json);
//
//            result = new GroupMonitorAddResult();
//            if (returnData.has("errmsg"))
//                result.setErrmsg(returnData.getString("errmsg"));
//            if (returnData.has("errcode"))
//                result.setErrcode(returnData.getInt("errcode"));
//            if (returnData.has("wx_nickname"))
//                result.setWxNickname(returnData.getString("wx_nickname"));
//            if (returnData.has("wx_name"))
//                result.setWxName(returnData.getString("wx_name"));
//            if (returnData.has("biz"))
//                result.setBiz(returnData.getString("biz"));
//
//        } catch (Exception e) {
//            logger.error("??????????:", e);
//        }
//
//
//        return result;
//    }
//
//
//    /**
//     * ????????????
//     *
//     * @param weixinNames
//     * @return
//     */
//    public boolean delWeixin2Group(String weixinNames) {
//        String ret_json = null;
//        try {
//            ApiSdk dataApi = ApiSdk.getApiSdk(gsdata_appID, gsdata_appKey);
//            Map<String, Object> requestParamsMap = new HashMap<String, Object>();
//            requestParamsMap.put("wx_name", weixinNames);
//            ret_json = dataApi.callInterFace(CustomAPI_addname_weixin2group, requestParamsMap);
//
//            JsonParser json = new JsonParser();
//            String id = json.parse(ret_json).getAsJsonObject().getAsJsonObject("returnData").get("id").getAsString();
//
//            requestParamsMap = new HashMap<String, Object>();
//            requestParamsMap.put("wxJson", "[ {\"nickname_id\":\"" + id + "\"}]");
//
//            //??????
//            ret_json = dataApi.callInterFace(CustomAPI_del_weixin2group, requestParamsMap);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (ret_json != null) {
//            try {
//                JSONObject jo = new JSONObject(ret_json);
//                int returnCode = jo.getInt("returnCode");
//                //String returnMsg = jo.getString("returnMsg");
//                logger.info("\n\nAdd to gsdata WeixinHao. ReturnCode=" + returnCode + "\t" + ret_json);
//
//                if (returnCode == 1001)
//                    return true;
//
//            } catch (Exception e) {
//                logger.error("??????????:", e);
//            }
//        }
//        return false;
//    }
//
//    /**
//     * api????????????????
//     */
//    public static String getWeixinContentByUrls(List<String> contentUrlList) {
//        String result = null;
//        Map<String, Object> requestParamsMap = new HashMap<>();
//        try {
//            //
//            ApiSdk dataApi = ApiSdk.getApiSdk(gsdata_appID, gsdata_appKey);
//            // ???URL????
//            requestParamsMap.put("url", StringUtils.join(contentUrlList, ","));
//            // ??????
//            result = dataApi.callInterFace(CUSTOMAPI_CONTENT, requestParamsMap);
//            logger.info("url:" + requestParamsMap + " api:" + CUSTOMAPI_CONTENT + " response json:" + result);
//
//        } catch (Exception ex) {
//            // ??????
//            logger.error(CUSTOMAPI_CONTENT + "??????????:", ex);
//        }
//        return result;
//    }
//
//    /**
//     * ?????????��??????
//     *
//     * @return
//     */
//    public String getAllGroup() {
//        String ret_json = null;
//
//        try {
//            /*
//             * ???????? ??????????????appid??appkey,??????��???????????????????????
//             * ?????��?????????????????????????????? ????appid??appkey
//             */
//            Map<String, Object> requestParamsMap = new HashMap<String, Object>();
//
//            SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd");
//
//            requestParamsMap.put("groupid", 45024);
//            //???????????????Key
//            requestParamsMap.put("start", 0);
//            requestParamsMap.put("num", 20);
//
//            //??????
////            ret_json = apiSdk.callInterFace(CustomAPI_wx_url_4, requestParamsMap);
//            ret_json = apiSdk.callInterFace(get_all_group, requestParamsMap);
//
//            logger.info(ret_json);
//
//            //??Utf8?????gbk
//            //ret_json = StringUtil.utf82gbk(ret_json);
//
//        } catch (Exception e) {
//            logger.error("??????????:", e);
//        }
//
//        return ret_json;
//    }
//
//    /**
//     * Main Entrys
//     *
//     * @param args
//     */
//    public static void main(String[] args) {
//
//        DataApi spider = new DataApi();
//
//        logger.info("iWeixin Starts at :" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//
//        ArrayList<OriginNews> articleList = new ArrayList<OriginNews>();
//
//        //2. ???????????????????????
//        int pageNo = 2;
//        String articlePage = spider.getAllGroup();
////        while (true) {
////            String articlePage = spider.getResponseData3();
//
////            ArrayList<OriginNews> list = null;
////            if (articlePage != null) {
////                logger.info("articlePage=" + articlePage);
////
////                list = spider.parseArticleList(articlePage);
////
////            }
////
////            if (list != null)
////                articleList.addAll(list);
////
////            if (list == null || list.size() < MaxRows_Request) {
////                logger.info("Null or less than 10 items, " + pageNo + " should be the last page. break.");
////                break;
////            }
////
////            pageNo++;
////
////            try {
////                Thread.sleep(5000);
////            } catch (Exception e) {
////                ;
////            }
////
////        }
////
////        totalArticlesBeGet = articleList.size();
////
////        if (articleList.size() == 0) {
////            logger.fatal("NO ARTICLES BE FOUND FROM THE API. Return.");
////
////            return;
////        }
//    }
//
//    /**
//     * ???????��?????????????
//     *
//     * @param key       ???????????????Key
//     * @param keyword   ??????????????
//     * @param expiryNum ?????????????��??????????????????+ expiry_num
//     */
//    public PdmiKeyWordAddResult addPdmiKeyWord(String key, String keyword, int expiryNum) {
//
//        String jsonResponeData = "";
//        Map<String, Object> requestParamsMap = new HashMap<>();
//
//        try {
//
//            // ??????????
//            requestParamsMap.put("keyword", keyword);
//            //???????????????Key
//            requestParamsMap.put("key", key);
//            // ?????????????��??????????????????+ expiry_num
//            requestParamsMap.put("expiry_num", expiryNum);
//            // ????????
//            requestParamsMap.put("opt_type", 1);
//            // ??????
//            jsonResponeData = searchRealKeyWord(requestParamsMap);
//
//            if (StringUtils.isNotBlank(jsonResponeData)) {
//                JSONObject jsonObject = new JSONObject(jsonResponeData);
//                // int returnCode = jo.getInt("returnCode");
//
//                JSONObject returnData = jsonObject.getJSONObject("returnData");
//                //  ??????????????
//                PdmiKeyWordAddResult result = new PdmiKeyWordAddResult();
//                if (returnData.has("key"))
//                    result.setKey(returnData.getString("key"));
//                if (returnData.has("keyword"))
//                    result.setKeyWord(returnData.getString("keyword"));
//                if (returnData.has("starttime"))
//                    result.setStartTime(returnData.getLong("starttime"));
//                if (returnData.has("endtime"))
//                    result.setEndTime(returnData.getLong("endtime"));
//                if (returnData.has("create_time"))
//                    result.setCreateTime(returnData.getLong("create_time"));
//
//                logger.debug(API_PDMI_KEYWORD + " params:" + requestParamsMap
//                        + "\n response:" + jsonResponeData);
//
//                // ??????
//                return result;
//            }
//            logger.info(API_PDMI_KEYWORD + " params:" + requestParamsMap
//                    + "\n response is null:" + jsonResponeData);
//
//        } catch (Exception e) {
//            logger.error(API_PDMI_KEYWORD + "  ?????:"
//                    + new Gson().toJson(requestParamsMap) + "\n response:" + jsonResponeData + "\n??????????:", e);
//        }
//        return null;
//    }
//
//    /**
//     * ???????��?????????????
//     *
//     * @param key       ???????????????Key,???????
//     * @param pageIndex ???????????
//     * @param pageSize  ???????????????��??????50
//     */
//    public PdmiKeyWordGetResult getPdmiKeyWordList(String key, int pageIndex, int pageSize) {
//        try {
//            Map<String, Object> requestParamsMap = new HashMap<String, Object>();
//
//            //???????????????Key
//            if (StringUtils.isNotBlank(key)) {
//                requestParamsMap.put("key", key);
//            }
//            // ????????
//            requestParamsMap.put("opt_type", 2);
//            // ???????????
//            requestParamsMap.put("page_index", pageIndex);
//            // ?????50
//            if (pageSize <= 0 || pageSize > 50) {
//                pageSize = 50;
//            }
//            // ???????????????��??????50
//            requestParamsMap.put("page_size", pageSize);
//            // ??????
//            String jsonResponeData = searchRealKeyWord(requestParamsMap);
//
//            if (StringUtils.isNotBlank(jsonResponeData)) {
//                JSONObject jsonObject = new JSONObject(jsonResponeData);
//                // int returnCode = jo.getInt("returnCode");
//
//                JSONArray returnData = jsonObject.getJSONArray("returnData");
//                if (returnData.length() > 0) {
//
//                    PdmiKeyWordGetResult result = new PdmiKeyWordGetResult();
//                    List<PdmiKeyWordAddResult> records = new ArrayList<>();
//                    PdmiKeyWordAddResult record = null;
//                    for (int i = 0; i < returnData.length(); i++) {
//                        JSONObject item = (JSONObject) returnData.get(i);
//                        //  ??????????????
//                        record = new PdmiKeyWordAddResult();
//                        if (item.has("key"))
//                            record.setKey(item.getString("key"));
//                        if (item.has("keyword"))
//                            record.setKeyWord(item.getString("keyword"));
//                        if (item.has("starttime"))
//                            record.setStartTime(item.getLong("starttime"));
//                        if (item.has("endtime"))
//                            record.setEndTime(item.getLong("endtime"));
//                        if (item.has("create_time"))
//                            record.setCreateTime(item.getLong("create_time"));
//                        records.add(record);
//                    }
//                    result.setRecords(records);
//                    // ??????
//                    return result;
//                }
//            }
//            logger.debug(API_PDMI_KEYWORD + " params:" + requestParamsMap
//                    + " response:" + jsonResponeData);
//
//        } catch (Exception e) {
//            logger.error(API_PDMI_KEYWORD + "??????????:", e);
//        }
//        return null;
//    }
//
//    /**
//     * ???????��?????????????
//     *
//     * @param key       ???????????????Key,???????
//     * @param pageIndex ???????????
//     * @param pageSize  ???????????????��??????50
//     * @param searchDay ???????????? ????????????2017-05-25,??????????????????
//     */
//    public PdmiKeyWordGetResult getPdmiKeyWordList(String key, int pageIndex, int pageSize, String searchDay) {
//        try {
//            Map<String, Object> requestParamsMap = new HashMap<String, Object>();
//
//            //???????????????Key
//            if (StringUtils.isNotBlank(key)) {
//                requestParamsMap.put("key", key);
//            }
//            // ????????
//            requestParamsMap.put("opt_type", 2);
//            // ???????????
//            requestParamsMap.put("page_index", pageIndex);
//            // ?????50
//            if (pageSize <= 0 || pageSize > 50) {
//                pageSize = 50;
//            }
//            // ???????????????��??????50
//            requestParamsMap.put("page_size", pageSize);
//
//            long datetime = 0l;
//            // ?��????????????
//            if (StringUtils.isBlank(searchDay)) {
//                // ????????????
//                datetime = DateUtils.parseDate(DateFormatUtils.format(new Date(),
//                        "yyyy-MM-dd") + " 00:00:00", new String[]{"yyyy-MM-dd HH:mm:ss"}).getTime();
//            } else {
//                // ???????????????????????
//                datetime = DateUtils.parseDate(searchDay + " 00:00:00", new String[]{"yyyy-MM-dd HH:mm:ss"}).getTime();
//            }
//            // ??????????
//            requestParamsMap.put("datetime", String.valueOf(datetime));
//
//            // ??????
//            String jsonResponeData = searchRealKeyWord(requestParamsMap);
//
//            if (StringUtils.isNotBlank(jsonResponeData)) {
//                JSONObject jsonObject = new JSONObject(jsonResponeData);
//                // int returnCode = jo.getInt("returnCode");
//
//                JSONArray returnData = jsonObject.getJSONArray("returnData");
//                if (returnData.length() > 0) {
//
//                    PdmiKeyWordGetResult result = new PdmiKeyWordGetResult();
//                    List<PdmiKeyWordAddResult> records = new ArrayList<>();
//                    PdmiKeyWordAddResult record = null;
//                    for (int i = 0; i < returnData.length(); i++) {
//                        JSONObject item = (JSONObject) returnData.get(i);
//                        //  ??????????????
//                        record = new PdmiKeyWordAddResult();
//                        if (item.has("key"))
//                            record.setKey(item.getString("key"));
//                        if (item.has("keyword"))
//                            record.setKeyWord(item.getString("keyword"));
//                        if (item.has("starttime"))
//                            record.setStartTime(item.getLong("starttime"));
//                        if (item.has("endtime"))
//                            record.setEndTime(item.getLong("endtime"));
//                        if (item.has("create_time"))
//                            record.setCreateTime(item.getLong("create_time"));
//                        records.add(record);
//                    }
//                    result.setRecords(records);
//                    // ??????
//                    return result;
//                }
//            }
//            logger.debug(API_PDMI_KEYWORD + " params:" + requestParamsMap
//                    + " response:" + jsonResponeData);
//
//        } catch (Exception e) {
//            logger.error(API_PDMI_KEYWORD + "??????????:", e);
//        }
//        return null;
//    }
//
//    /**
//     * ?��???????????
//     *
//     * @param key ???????????????Key
//     */
//    public String deletePdmiKeyWord(String key) {
//        try {
//            Map<String, Object> requestParamsMap = new HashMap<String, Object>();
//            //???????????????Key
//            requestParamsMap.put("key", key);
//            // ????????
//            requestParamsMap.put("opt_type", 3);
//            // ??????
//            String jsonResponeData = searchRealKeyWord(requestParamsMap);
//
//            if (StringUtils.isNotBlank(jsonResponeData)) {
//                JSONObject jsonObject = new JSONObject(jsonResponeData);
//                // int returnCode = jo.getInt("returnCode");
//
//                JSONObject returnData = jsonObject.getJSONObject("returnData");
//                if (returnData.has("status")) {
//                    return returnData.getString("status");
//                }
//            }
//            logger.debug(API_PDMI_KEYWORD + " params:" + requestParamsMap
//                    + " response:" + jsonResponeData);
//
//        } catch (Exception e) {
//            logger.error(API_PDMI_KEYWORD + "??????????:", e);
//        }
//        return null;
//    }
//
//    /**
//     * ????????????????
//     *
//     * @param requestParamsMap ????????
//     * @return ???????????
//     */
//    private String searchRealKeyWord(Map<String, Object> requestParamsMap) {
//
//        String jsonResponeData = null;
//        try {
//            ApiSdk dataApi = ApiSdk.getApiSdk(gsdata_appID, gsdata_appKey);
//
//            // ??????
//            jsonResponeData = dataApi.callInterFace(API_PDMI_KEYWORD, requestParamsMap);
//
//            logger.debug(API_PDMI_KEYWORD + " params:" + requestParamsMap + " response:" + jsonResponeData);
//
//        } catch (Exception e) {
//            logger.error(API_PDMI_KEYWORD + "??????????:", e);
//        }
//        return jsonResponeData;
//    }
//
//    /**
//     * ???????????��??????
//     *
//     * @param wxname
//     * @return
//     */
//    public String getInfoByWxname(String wxname) {
//        ApiSdk dataApi = ApiSdk.getApiSdk(gsdata_appID, gsdata_appKey);
//        Map<String, Object> requestParamsMap = new HashMap<String, Object>();
//        requestParamsMap.put("wx_name", wxname);
//        String ret_json = dataApi.callInterFace(CustomAPI_addname_weixin2group, requestParamsMap);
//        return ret_json;
//    }
//
//    /**
//     * ??????????????????????????
//     *
//     * @param url ????????URL???
//     * @return ???????????
//     */
//    public WxNumsMonitorResult getWxNumsMonitorData(String url) {
//        WxNumsMonitorResult result = null;
//        // ??????
//        String jsonResponeData = null;
//        try {
//            ApiSdk dataApi = ApiSdk.getApiSdk(gsdata_appID, gsdata_appKey);
//
//            Map<String, Object> requestParamsMap = new HashMap<String, Object>();
//            //???????????????Key
//            requestParamsMap.put("url", url);
//            // ??????
//            jsonResponeData = dataApi.callInterFace(API_WX_NUMS_MONITOR, requestParamsMap);
//
//            if (StringUtils.isNotBlank(jsonResponeData)) {
//                JSONObject jsonObject = new JSONObject(jsonResponeData);
//
//                JSONObject returnData = jsonObject.getJSONObject("returnData");
//                if (returnData.length() > 0) {
//                    result = new WxNumsMonitorResult();
//                    // 	?????????0:????????????????????
//                    if (returnData.has("status"))
//                        result.setStatus(returnData.getInt("status"));
//                    // 	??????
//                    if (returnData.has("likenum"))
//                        result.setLikeNum(returnData.getInt("likenum"));
//                    // ?????????????10w+???????
//                    if (returnData.has("real_read_num"))
//                        result.setRealReadNum(returnData.getInt("real_read_num"));
//                    // ?????
//                    if (returnData.has("readnum"))
//                        result.setReadNum(returnData.getInt("readnum"));
//
//                    // ??????
//                    return result;
//                }
//            }
//            // ??????
//            logger.debug(API_WX_NUMS_MONITOR + " params:" + requestParamsMap + " response:" + jsonResponeData);
//
//        } catch (Exception e) {
//            logger.error(API_WX_NUMS_MONITOR + "response data:" + jsonResponeData + "??????????:", e);
//            result = null;
//        }
//        return result;
//    }
}
