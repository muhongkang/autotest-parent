package com.cn.autotest.common;

import com.cn.autotest.asserts.ConstVariable;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.log4j.Logger;

import static io.restassured.RestAssured.given;

/**
 * @author muhon
 */
public class RunCaseJson {
    static Logger log = Logger.getLogger(RunCaseJson.class);

    /**
     * post或get方式请求,返回响应报文（json格式）
     *
     * @bodyString:json格式的请求报文体
     * @para:requestType post或get
     */
    public static Response runCase(String bodyString, String requestType) {
        Response response;

        if (ConstVariable.HTTP_GET.equalsIgnoreCase(requestType)) {
            log.info("执行HTTP["+ConstVariable.HTTP_GET+"]操作");
            log.info(RestAssured.baseURI);
            response = given()
                    .contentType("application/json;charset=UTF-8")
                    .request()
                    .body(bodyString)
                    .get();
        } else {
            log.info("执行HTTP["+ConstVariable.HTTP_POST+"]操作");
            log.info(RestAssured.baseURI);

            response = given()
                    .contentType("application/json;charset=UTF-8")
                    .request()
                    .body(bodyString)
                    .post();

            //打印格式化的参数
            //response.prettyPrint(); ////去掉部分日志 add by lrb 20181029
        }
        return response;
    }
}
