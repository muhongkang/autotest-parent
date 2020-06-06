package com.cn.autotest.report;

import com.alibaba.fastjson.JSONObject;
import io.qameta.allure.Attachment;

/**
 * 测试步骤，测试报告中展现
 *
 * @author muhon
 */
public class TestStep {

    public static void requestAndRespondBody(String URL, String Body, String Respond) {
        requestBody(URL, Body);
        respondBody(Respond);
    }

    @Attachment("请求报文")
    public static String requestBody(String URL, String body) {


        JSONObject jsonObject = JSONObject.parseObject(body);
        String str = JSONObject.toJSONString(jsonObject, true);

        //报告展现请求报文
        return URL + "\n" + str;
    }

    @Attachment("响应报文")
    public static String respondBody(String respond) {
        String str;
        try {
            //格式化json串
            JSONObject jsonObject = JSONObject.parseObject(respond);
            str = JSONObject.toJSONString(jsonObject, true);
        } catch (Exception e) {
            str = respond;
        }


        //报告展现响应报文
        return str;
    }

    @Attachment("数据库断言结果")
    public static StringBuffer databaseAssertResult(StringBuffer assertResult) {
        //报告展现数据库断言结果
        return assertResult;
    }

    @Attachment("响应报文断言结果")
    public static StringBuffer assertRespond(StringBuffer assertResult) {
        //报告展现数据库断言结果
        return assertResult;
    }
}
