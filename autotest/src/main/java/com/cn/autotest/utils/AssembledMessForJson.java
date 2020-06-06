package com.cn.autotest.utils;

import com.cn.autotest.asserts.RespondAssertForJson;
import com.google.gson.Gson;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 根据测试用例文件拼装报文body(json格式)
 * 输入一贯改造_短信发送.xls，return json串列表
 *
 * @author muhon
 */
public class AssembledMessForJson {

    static Logger log = Logger.getLogger(AssembledMessForJson.class);
    static Map<String, Object> bodyMap = new HashMap<>();
    static Map<String, Object> dataMap = new HashMap<>();
    static Map<String, Object> caseMessMap = new HashMap<>();
    static Map<String, String> map = new HashMap<>();


    /**
     * 入参为testCase目录下的案例数据文件名
     */
    public static Map<String, String> assembleMess(String fileName, String caseNo) throws Exception {

        String filePath = new GetFileMess().getFilePath("testCase", fileName);
        log.info("读取测试文件:" + filePath);
        File file = new File(filePath);
        //添加文件判断并抛出异常
        if (!file.isFile()) {
            throw new IOException("文件" + filePath + "不存在！");
        }
        File xlsFile = new File(filePath);
        Workbook workbook = Workbook.getWorkbook(xlsFile);
        Sheet sheet = workbook.getSheet(0);
        int rows = sheet.getRows();
        int cols = sheet.getColumns();

        String pubArgs = new GetFileMess().getValue("pubArgs", "PublicArgs.properties");
        log.info("接口公共入参pubArgs：" + pubArgs);

        bodyMap.clear();
        dataMap.clear();
        caseMessMap.clear();

        for (int row = 1; row < rows; row++) {
            String yOn = sheet.getCell(3, row).getContents();
            String caseNo1 = sheet.getCell(0, row).getContents().toLowerCase();
            if ("Y".equals(yOn) && "".equals(caseNo)) {
                getMap(sheet, cols, row, pubArgs);
            } else if (caseNo1.equals(caseNo.toLowerCase())) {
                getMap(sheet, cols, row, pubArgs);
            }
        }

        workbook.close();
        return map;
    }

    public static void getMap(Sheet sheet, int cols, int row, String pubArgs) {

        for (int col = 0; col < cols; col++) {

            String cellKey = sheet.getCell(col, 0).getContents();
            String cellValue = sheet.getCell(col, row).getContents();
            if (col >= 5) {
                //appid,api,version属于公共入参,公共入参字段在PublicArgs.properties文件进行配置
                // getBuildValue(value1,value2)方法用于转换${}或者函数为对应的值
                if (pubArgs.toLowerCase().contains(cellKey.toLowerCase().trim())) {
                    bodyMap.put(cellKey, RespondAssertForJson.getBuildValue("", sheet.getCell(col, row).getContents()));
                } else {
                    dataMap.put(cellKey, RespondAssertForJson.getBuildValue("", sheet.getCell(col, row).getContents()));
                }
            } else {
                caseMessMap.put(cellKey, cellValue);
            }
        }
        bodyMap.put("data", dataMap);
        map.put(new Gson().toJson(caseMessMap), new Gson().toJson(bodyMap));
    }
}
