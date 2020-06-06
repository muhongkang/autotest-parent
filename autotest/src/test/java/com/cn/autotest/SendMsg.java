package com.cn.autotest;

import com.cn.autotest.common.RunCaseJson;
import com.cn.autotest.common.SetUpTearDown;
import com.cn.autotest.data.DataProviders;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.cn.autotest.asserts.Asserts.asserts;

public class SendMsg extends SetUpTearDown {

    @Story("发送短信")
    @Test(dataProvider = "darneder", dataProviderClass = DataProviders.class,
            description = "发送短信")
    public void runCase(String caseMess, String bodyString) throws Exception {
        //发送请求
        Response response = RunCaseJson.runCase(bodyString, "get");
        //只进行响应报文断言
        asserts(caseMess, bodyString, response.asString(), "", null);
    }
}
