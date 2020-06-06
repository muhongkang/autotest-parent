package com.cn.autotest.dao;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;

//执行sql文件语句
public class ExcutSqlFile {

    public static void excite(String fileName) throws Exception {
        Connection conn = DBDPConnection.getDPConnection();
        ScriptRunner runner = new ScriptRunner(conn);
        Resources.setCharset(StandardCharsets.UTF_8);
        runner.setDelimiter(";"); //语句结束符号设置
        runner.setLogWriter(null);//设置是否输出日志
        //案例执行前的参数维护
        runner.runScript(Resources.getResourceAsReader("./sql/" + fileName + ".sql"));
        runner.closeConnection();
        conn.close();
    }
}
