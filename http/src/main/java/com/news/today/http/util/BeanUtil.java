package com.news.today.http.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yh on 2016/12/1.
 */

public class BeanUtil {

    /**
     * 对象转map
     *
     * @param o
     * @return
     * @throws Exception
     */
    public static Map<String, Object> po2Map(Object o) throws Exception {
        if (o == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            int modifiers = field.getModifiers();
            if (Modifier.isStatic(modifiers)) {
                continue;
            }
            String proName = field.getName();
            Object proValue = field.get(o);
            if (proValue != null) {
                map.put(proName, proValue);
            }
        }
        return map;
    }

    /**
     * 对象转map
     *
     * @param params
     * @return
     */
    public static Map<String, Object> toMap(Object params) {
        Map<String, Object> result = null;
        try {
            if (params instanceof Map) {
                result = (Map<String, Object>) params;
            } else {
                result = po2Map(params);
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 取得某一个类的父类的范型参数
     *
     * @param cls
     * @return
     */
    public static Type getSuperClassParam(Class cls) {
        Type superClassType = cls.getGenericSuperclass();
        if (superClassType != null && superClassType instanceof ParameterizedType) {
            ParameterizedType parameterized = (ParameterizedType) superClassType;
            Type[] args = parameterized.getActualTypeArguments();
            if (args != null && args.length > 0) {
                return args[0];
            }
        }
        return null;
    }
}
