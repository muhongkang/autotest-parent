package com.cn.autotest.asserts;

import com.alibaba.fastjson.JSONObject;
import com.cn.autotest.utils.GetFileMess;
import io.restassured.RestAssured;
import org.apache.log4j.Logger;
import org.testng.Assert;

import java.util.Map;

import static com.cn.autotest.asserts.DatabaseAssert.verifyDatabase;
import static com.cn.autotest.asserts.RespondAssertForJson.verifyResult;
import static com.cn.autotest.report.TestStep.*;
import static com.cn.autotest.utils.SplitXmlForCaseNo.xmlOutTemp;


/**
 * 数据库断言及响应报文断言
 */
public class Asserts {

    static Logger log = Logger.getLogger(Asserts.class);

    public static void asserts(String caseMess, String bodyString, String response, String xmlFileName, Map<String, String> map) throws Exception {


        //测试报告展现请求报文 响应报文
        String url = RestAssured.baseURI + ":" + RestAssured.port + "/" + RestAssured.basePath;
        requestAndRespondBody(url, bodyString, response);

        String preResult = new GetFileMess().getCaseMessKeyValue(caseMess, "preResult");
        String tableCheck = new GetFileMess().getCaseMessKeyValue(caseMess, "tableCheck");
        String caseNo = new GetFileMess().getCaseMessKeyValue(caseMess, "caseNo");

        //格式化json串
        JSONObject jsonObject = JSONObject.parseObject(response);

        response = JSONObject.toJSONString(jsonObject, true);

        log.info("案例编号：" + caseNo);
        log.info("响应报文：" + response);
        //断言（包含响应报文断言和数据库断言）
        databaseAndRespondAsserts(response, preResult, tableCheck, xmlFileName, caseNo, map);
    }

    public static void databaseAndRespondAsserts(String sourceData, String verifyData, String tableCheck, String fileName, String caseNo, Map<String, String> map) throws Exception {

        StringBuffer stringBufferResult = new StringBuffer();
        StringBuffer stringBufferDatabase = new StringBuffer();
        String path = "";
        boolean assertFlag = true;

        //响应报文断言
        stringBufferResult = verifyResult(sourceData, verifyData);
        //测试报告展现 响应报文断言结果（无论成功还是失败）
        assertRespond(stringBufferResult);
        // 断言不通过，flag标志赋值为false
        if (stringBufferResult.indexOf("断言false") != -1) {
            assertFlag = false;
        }

        //tableCheck为Y/y才进行数据库响应断言
        /**
         * 屏蔽数据库断言
         */
        if (tableCheck.toUpperCase().trim().equals(ConstVariable.AUTOCONFIG)) {
            //if (tableCheck.toUpperCase().trim().equals("Y")) {
            //临时xml文件
            path = xmlOutTemp(fileName, caseNo, map);

            if (!path.equals("")) {
                stringBufferDatabase = verifyDatabase(caseNo, path);

                //测试报告展现 数据库断言结果（无论成功还是失败）
                databaseAssertResult(stringBufferDatabase);

                if (stringBufferDatabase.indexOf("检查不通过") != -1) {
                    assertFlag = false;
                }
            }
        }

        //断言
        Assert.assertTrue(assertFlag, "响应报文断言或数据库断言失败，请查看断言结果！");
    }
}
