package org.pcchen.uar.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * 清博工具类
 *
 * @author cpc
 * @create 2018-12-18 17:02
 **/
public class GSDataUtils {
    private static Logger logger = Logger.getLogger(GSDataUtils.class);

    public static JSONObject extractDataJson(String retJsonStr) {
        logger.info("接口返回值为：" + retJsonStr);
        Date responseTime = new Date();

        JSONObject dataJson = new JSONObject();
        if (null != retJsonStr) {
            JSONObject jsonObject = JSONObject.parseObject(retJsonStr);
            String returnCode = jsonObject.getString(GSDataConstants.RETURN_CODE);
            if (GSDataConstants.RETURN_CODE_SUCCEED.equals(returnCode)) {
                dataJson = jsonObject.getJSONObject(GSDataConstants.RETURN_DATA);
            } else {
                logger.error(jsonObject.toString());
            }
        } else {
            logger.error("retJson is null");
        }
        return dataJson;
    }
}
