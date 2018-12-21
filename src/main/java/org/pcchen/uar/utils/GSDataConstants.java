package org.pcchen.uar.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 清博配置信息
 *
 * @author cpc
 * @create 2018-12-19 17:03
 **/
@Component
public class GSDataConstants {
    //uar微信模块清博配置相关参数
    public static String GSDATA_APPID;
    public static String GSDATA_APPKEY;

    /**
     * 根据公众号查询公众号信息接口查询起始值 默认为0
     */
    public static Integer GSDATA_SEARCH_KEYWORD_API_START;
    /**
     * 根据公众号查询公众号信息接口查询搜索总数 默认为10
     */
    public static Integer GSDATA_SEARCH_KEYWORD_API_NUM;

    /**
     * 公众号添加到的分组的id
     */
    public static Integer GSDATA_GROUP_ID;

    /**
     * 清博接口返回固定值
     */
    public static String RETURN_CODE;
    public static String RETURN_MSG;
    public static String RETURN_DATA;
    public static String RETURN_CODE_SUCCEED;

    /**
     * 根据关键词搜索公众号
     */
    public static String API_NICKNAME_KEYWORD_SEARCH;
    /**
     * 根据文章url将信公众账号添加到分组并返回公众号相关信息
     */
    public static String API_ADD_WX_TO_GROUP_BY_URL;
    /**
     * 通过UID返回用户分组
     */
    public static String API_GET_ALL_GROUP_INFO;
    /**
     * 获取一个公众号详情
     */
    public static String API_GET_NICKNAME_ONE;


    @Value("${gsdata_appId}")
    public void setGsdataAppid(String gsdataAppid) {
        this.GSDATA_APPID = gsdataAppid;
    }

    @Value("${gsdata_appKey}")
    public void setGsdataAppkey(String gsdataAppkey) {
        this.GSDATA_APPKEY = gsdataAppkey;
    }

    @Value("${api_nickname_keyword_search}")
    public void setApiNicknameKeywordSearch(String apiNicknameKeywordSearch) {
        this.API_NICKNAME_KEYWORD_SEARCH = apiNicknameKeywordSearch;
    }

    @Value("${api_add_wx_to_group_by_url}")
    public void setApiAddWxToGroupByUrl(String apiAddWxToGroupByUrl) {
        this.API_ADD_WX_TO_GROUP_BY_URL = apiAddWxToGroupByUrl;
    }

    @Value("${api_get_all_group_info}")
    public void setApiGetAllGroupInfo(String apiGetAllGroupInfo) {
        this.API_GET_ALL_GROUP_INFO = apiGetAllGroupInfo;
    }

    @Value("${gsdata_search_keyword_api_start}")
    public void setGsdataSearchKeywordApiStart(Integer gsdataSearchKeywordApiStart) {
        this.GSDATA_SEARCH_KEYWORD_API_START = gsdataSearchKeywordApiStart;
    }

    @Value("${gsdata_search_keyword_api_num}")
    public void setGsdataSearchKeywordApiNum(Integer gsdataSearchKeywordApiNum) {
        this.GSDATA_SEARCH_KEYWORD_API_NUM = gsdataSearchKeywordApiNum;
    }

    @Value("${return_code}")
    public void setReturnCode(String returnCode) {
        this.RETURN_CODE = returnCode;
    }

    @Value("${return_msg}")
    public void setReturnMsg(String returnMsg) {
        this.RETURN_MSG = returnMsg;
    }

    @Value("${return_data}")
    public void setReturnData(String returnData) {
        this.RETURN_DATA = returnData;
    }

    @Value("${return_code_succeed}")
    public void setReturnCodeSucceed(String returnCodeSucceed) {
        this.RETURN_CODE_SUCCEED = returnCodeSucceed;
    }

    @Value("${gsdata_group_id}")
    public void setGsdataGroupId(Integer gsdataGroupId) {
        this.GSDATA_GROUP_ID = gsdataGroupId;
    }

    @Value("${api_get_nickname_one}")
    public void setApiGetNicknameOne(String apiGetNicknameOne) {
        this.API_GET_NICKNAME_ONE = apiGetNicknameOne;
    }
}
