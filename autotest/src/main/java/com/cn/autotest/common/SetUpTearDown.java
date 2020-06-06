package com.cn.autotest.common;


import com.cn.autotest.utils.GetFileMess;
import io.restassured.RestAssured;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.util.HashMap;
import java.util.Map;

import static com.cn.autotest.utils.WritePropertiesUtil.writePropertiesFile;


/**
 * @author muhon
 */
public class SetUpTearDown {
    static Logger log = Logger.getLogger(SetUpTearDown.class);

    @BeforeSuite
    public void dataSetUp() throws Exception {
        /* 什么也不做 */
    }

    /**
     * filter配置的信息获取RestAssured 的请求参数
     */
    @BeforeClass
    public void envSetUp() {
        try {
            //环境由filter配置
            String system = "env.properties";
            RestAssured.baseURI = new GetFileMess().getValue("baseURI", system);
            RestAssured.basePath = new GetFileMess().getValue("basePath", system);
            RestAssured.port = Integer.parseInt(new GetFileMess().getValue("port", system));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 创建environment.properties并放到allure-results目录下，测试报告展现
     */
    @AfterSuite
    public void createEnvPropertiesForReport() throws Exception {
        Map<String, String> data = new HashMap<>();
        String database = "db.properties";
        data.put("DatabaseLoginName", new GetFileMess().getValue("DB_Name", database));
        data.put("DatabaseLoginPass", new GetFileMess().getValue("DB_Password", database));
        data.put("DatabaseLoginIP", new GetFileMess().getValue("DB_IP", database));
        data.put("baseURI", RestAssured.baseURI + ":" + RestAssured.port + "/" + RestAssured.basePath);
        writePropertiesFile();
    }

    /**
     * 测试案例之后后处理
     * @throws Exception
     */
    @AfterSuite
    public void dataTearDown() throws Exception {
        //案例执行结束后，对数据池的数据进行清理（删除或更新状态）什么也不做
    }

}
