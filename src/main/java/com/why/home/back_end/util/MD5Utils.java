package com.why.home.back_end.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*---------------------------------------------------------------
              MD5Utils Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/8/22.
;
;  基于 AOP (Aspect Oriented Programming) 面向切面编程知识。
;
;  Function:
;          MD5加密 用于密码防护
;           @Param: 要加密的字符串
;           @Return: 加密后的字符串
;
----------------------------------------------------------------*/

public class MD5Utils {
    /*------定义静态方法 引入后可直接使用-----*/
    /*------用于加密的code方法 输入原始密码 输出加密字符串-----*/
    public static String code(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[]byteDigest = md.digest();
            int i;
            StringBuffer buf = new StringBuffer();
            for(int byteD:byteDigest){
                i=byteD;
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            /*------可输出16/32位的加密后的密码-----*/
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*-------------------用于测试--------------------*/
    public static void main(String[] args) {
        /*------打印111111的MD5加密后的字符串-----*/
        System.out.println(code("q5967738"));
    }
}
