package com.cn.autotest.utils.common;


import com.cn.autotest.asserts.ConstVariable;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 简单http
 *
 * @author muhon
 */
public class SimpleHttp {
    /**
     *
     * @param urlPath
     * @param bodyString
     * @param requestType
     * @return
     * @throws Exception
     */
    public static StringBuilder buildConnection(String urlPath, String bodyString, String requestType) throws Exception {
        if (ConstVariable.HTTP_GET.equalsIgnoreCase(requestType)) {
            return doGet(urlPath, bodyString);
        } else if (ConstVariable.HTTP_GET.equalsIgnoreCase(requestType)) {
            return doPost(urlPath, bodyString);
        } else {
            throw new Exception("不支持的http参数:" + requestType);
        }
    }

    /**
     *
     * @param urlPath
     * @param bodyString
     * @return
     * @throws IOException
     */
    private static StringBuilder doGet(String urlPath, String bodyString) throws IOException {
        URL url;
        HttpURLConnection connection;
        url = new URL(urlPath);
        connection = (HttpURLConnection) url.openConnection();
        //设置请求的方式
        connection.setRequestMethod("GET");
        return build(connection);
    }

    /**
     *
     * @param connection
     * @return
     * @throws IOException
     */
    private static StringBuilder build(HttpURLConnection connection) throws IOException {
        connection.connect();
        //获取请求返回的输入流
        InputStream inputStream = connection.getInputStream();
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }
        connection.disconnect();
        bufferedReader.close();
        inputStream.close();
        reader.close();
        return result;
    }

    /**
     *
     * @param urlPath
     * @param bodyString
     * @return
     * @throws IOException
     */
    private static StringBuilder doPost(String urlPath, String bodyString) throws IOException {
        URL url;
        HttpURLConnection connection;
        url = new URL(urlPath);
        connection = (HttpURLConnection) url.openConnection();
        //设置请求的方式
        connection.setRequestMethod("POST");
        //post请求必须设置下面两行
        connection.setDoInput(true);
        connection.setDoOutput(true);


        OutputStream outputStream = connection.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeBytes(bodyString);
        dataOutputStream.flush();
        dataOutputStream.close();
        return build(connection);

    }
}