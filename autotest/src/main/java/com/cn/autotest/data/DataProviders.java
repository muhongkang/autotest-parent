package com.cn.autotest.data;

import com.cn.autotest.utils.AssembledMessForJson;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author muhon
 */
public class DataProviders {

    /**
     *map包含两部分json，key为caseNo等信息，value为接口入参
     */
    @DataProvider(name = "darneder", parallel = true)
    public static Object[][] dataP(Method method) throws Exception {
        /** */
        String className = method.getDeclaringClass().getSimpleName();
        /** //测试案例名称为：类名.xls */
        String caseFileName = className + ".xls";
        Object[][] objects;
        Map<String, String> map;
        //""表示读取所有的为Y的case
        map = AssembledMessForJson.assembleMess(caseFileName, "");
        objects = new Object[map.size()][2];
        int i = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            objects[i][0] = entry.getKey();
            objects[i][1] = entry.getValue();
            i++;
        }
        //需清空map，否则案例会不断叠加 2018-10-19 add by lrb
        map.clear();
        return objects;
    }
}
