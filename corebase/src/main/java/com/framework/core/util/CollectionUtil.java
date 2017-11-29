package com.framework.core.util;

import java.util.Collection;
import java.util.Map;

/**
 * 集合操作工具类
 *
 */
public class CollectionUtil {
    public static boolean isEmpty(Collection collection){
        return null == collection || collection.size() == 0;
    }
    public static boolean isEmpty(Map collection){
        return null == collection || collection.size() == 0;
    }
}
