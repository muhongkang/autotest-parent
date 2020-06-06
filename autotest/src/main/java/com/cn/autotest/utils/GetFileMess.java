package com.cn.autotest.utils;

import com.cn.autotest.asserts.ConstVariable;
import io.restassured.path.json.JsonPath;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取文件的信息
 *
 * @author muhon
 */
public class GetFileMess {
    private static Properties properties;
    Logger log = Logger.getLogger(GetFileMess.class);

    /**
     * 根据properties文件主键获取对应的值
     */
    public String getValue(String key, String propertiesFileName) throws Exception {
        String valueBeansValue = System.getProperty(ConstVariable.AUTOCONFIG, "config") + "\\" + propertiesFileName;
        log.info("load file " + valueBeansValue);
        System.out.println(valueBeansValue);
        InputStream stream = new FileInputStream(new File(valueBeansValue));
        properties = new Properties();
        properties.load(stream);
        String value = properties.getProperty(key);
        return value;
    }

    /**
     * 获取配置文件路径
     */
    public String getFilePath(String directory, String fileName) {
        //TODO 获取路径都在这里
        String basePath = System.getProperty(ConstVariable.AUTOCONFIG, "config");
        String filePath;
        if (directory == null || "".equals(directory)) {
            filePath = basePath + "/" + fileName;
        } else {
            filePath = basePath + "/" + directory + "/" + fileName;
        }
        return filePath;
    }

    /**
     * 获取测试案例数据的预期结果
     */
    public String getCaseMessKeyValue(String caseMess, String key) {
        JsonPath caseMessToJson = new JsonPath(caseMess);
        String value = caseMessToJson.get(key);
        return value;
    }
}
