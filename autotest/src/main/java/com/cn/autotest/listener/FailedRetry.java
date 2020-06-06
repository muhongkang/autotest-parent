package com.cn.autotest.listener;

import com.cn.autotest.exceptions.ErrorException;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/*
 *案例执行抛异常则重试
 */
public class FailedRetry implements IRetryAnalyzer {
    private static final int maxRetryCount = 2;
    private int retryCount = 1;

    public boolean retry(ITestResult iTestResult) {
        //抛出异常则重跑失败案例
        if (iTestResult.getThrowable() instanceof ErrorException && retryCount % maxRetryCount != 0) {
            retryCount++;
            return true;
        } else {
            retryCount = 1;
            return false;
        }
    }
}
