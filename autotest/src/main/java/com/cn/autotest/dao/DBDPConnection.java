package com.cn.autotest.dao;

import com.cn.autotest.utils.GetFileMess;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBDPConnection {

    static Logger log = Logger.getLogger(DBDPConnection.class);

    static Connection dBConnection = null; //数据库链接 oracle
    static Connection dPConnection = null; //数据池链接 mysql

    //数据库连接，oracle / mysql
    public static Connection getDBConnection() throws Exception {
        String database = "db.properties"; //通过filter配置数据库环境
        String url = new GetFileMess().getValue("DB_IP", database);
        String user = new GetFileMess().getValue("DB_Name", database);
        String password = new GetFileMess().getValue("DB_Password", database);

        Class.forName("oracle.jdbc.driver.OracleDriver");
        url = "jdbc:oracle:thin:@" + url;
        log.info("数据库：" + url + "|" + user + "|" + password);
        dBConnection = DriverManager.getConnection(url, user, password);
        return dBConnection;
    }

    //数据池连接，mysql
    public static Connection getDPConnection() throws Exception {
        //TODO
        String database = "db.properties"; //通过filter配置数据库环境
        String url = new GetFileMess().getValue("DP_IP", database);
        String user = new GetFileMess().getValue("DP_Name", database);
        String password = new GetFileMess().getValue("DP_Password", database);

        // 加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        url = "jdbc:mysql://" + url + "?characterEncoding=gb2312&serverTimezone=UTC&useSSL=false";  //ip配置;
        log.info("数据池：" + url + "|" + user + "|" + password);
        dPConnection = DriverManager.getConnection(url, user, password);
        return dPConnection;
    }
}
