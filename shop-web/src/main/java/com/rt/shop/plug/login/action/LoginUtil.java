package com.rt.shop.plug.login.action;

import java.util.Random;

/**
 * @author : Hui.Wang [wang.hui@rhxtune.com]
 * @version : 1.0
 * @created on  : 2016-06-20,  11:08 AM
 * @copyright : Copyright(c) 2015  北京zsCat信息技术有限公司
 */
public class LoginUtil {

    public static String randomUserName(){
        int length = 6;
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(62);
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }
}
