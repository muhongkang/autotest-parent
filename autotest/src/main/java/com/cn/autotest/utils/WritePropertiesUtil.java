package com.cn.autotest.utils;


import com.cn.autotest.asserts.ConstVariable;

import java.io.File;
import java.io.IOException;

import static com.cn.autotest.utils.FileUtil.copyFiles;


/**
 * @author muhon
 */
public class WritePropertiesUtil {


    public static void writePropertiesFile() throws IOException {

        String environment = "environment.properties";
        String categories = "categories.json";
        // 生成xml文件

        File environmentFile = new File(new GetFileMess().getFilePath(ConstVariable.ALLURECONFIG, environment));
        File categoriesFile = new File(new GetFileMess().getFilePath(ConstVariable.ALLURECONFIG, categories));
        File environmentToFile = new File(ConstVariable.ALLURE_RESULTS_OUTPUT_PATH + environment);
        File categoriesToFile = new File(ConstVariable.ALLURE_RESULTS_OUTPUT_PATH + categories);
        //新建文件
        File resultsOutputPath = new File(ConstVariable.ALLURE_RESULTS_OUTPUT_PATH);
        if (!(resultsOutputPath.isDirectory() || resultsOutputPath.isFile())) {
            boolean ret = resultsOutputPath.mkdirs();
        }
        copyFiles(environmentFile, environmentToFile);
        copyFiles(categoriesFile, categoriesToFile);

    }

}
