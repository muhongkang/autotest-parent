package com.cn.autotest.functioins;


import com.cn.autotest.utils.RandomUtil;

/*
 *生成随机数函数，默认为6位
 */
public class RandomFunction implements Function {

    @Override
    public String excute(String[] args) {
        int len = args.length;
        int length = 6;// 默认为6
        boolean flag = false;// 默认为false,true为纯数字
        if (len > 0) {// 第一个参数字符串长度
            length = Integer.parseInt(args[0]);
        }
        if (len > 1) {// 第二个参数是否纯字符串
            flag = Boolean.parseBoolean(args[1]);
        }
        return RandomUtil.getRandom(length, flag);
    }

    @Override
    public String getReferenceKey() {
        return "random";
    }
}
