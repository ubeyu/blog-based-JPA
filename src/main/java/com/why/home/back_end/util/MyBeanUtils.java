package com.why.home.back_end.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;


/*---------------------------------------------------------------
              MyBeanUtils Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/9/2.
;
;  Function:
;           BeanUtils方法 用于博客更新时处理Blog对象中为空的字段
;           从而使新对象的createTime、views等值不在更新时变为null值
;
----------------------------------------------------------------*/

public class MyBeanUtils {
    public static String[] getNullPropertyNames(Object source) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds =  beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyNames = new ArrayList<>();
        for (PropertyDescriptor pd : pds) {
            String propertyName = pd.getName();
            if (beanWrapper.getPropertyValue(propertyName) == null) {
                nullPropertyNames.add(propertyName);
            }
        }
        return nullPropertyNames.toArray(new String[nullPropertyNames.size()]);
    }
}
