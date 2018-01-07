package com.framework.core.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.RequestBody;

/**
 * @author : gaojigong
 * @date : 2017/11/13
 * @description:
 *  泛型及Type操作
 *  根据泛型查找类型，进行转换
 */


public class TUtil {
    /**
     * find the type by interfaces
     *
     * @param cls
     * @param <R>
     * @return
     */
    public static <R> Type findNeedType(Class<R> cls) {
        List<Type> typeList = TUtil.getMethodTypes(cls);
        if (typeList == null || typeList.isEmpty()) {
            return RequestBody.class;
        }
        return typeList.get(0);
    }

    /**
     * MethodHandler
     */
    public static <T> List<Type> getMethodTypes(Class<T> cls) {
        Type typeOri = cls.getGenericSuperclass();
        List<Type> needtypes = null;
        // if Type is T
        if (typeOri instanceof ParameterizedType) {
            needtypes = new ArrayList<>();
            Type[] parentypes = ((ParameterizedType) typeOri).getActualTypeArguments();
            for (Type childtype : parentypes) {
                needtypes.add(childtype);
                if (childtype instanceof ParameterizedType) {
                    Type[] childtypes = ((ParameterizedType) childtype).getActualTypeArguments();
                    Collections.addAll(needtypes, childtypes);
                }
            }
        }
        return needtypes;
    }

    public static Class getClass(Type type, int i) {
        if (type instanceof ParameterizedType) { // 处理泛型类型
            return getGenericClass((ParameterizedType) type, i);
        } else if (type instanceof TypeVariable) {
            return getClass(((TypeVariable) type).getBounds()[0], 0); // 处理泛型擦拭对象
        } else {// class本身也是type，强制转型
            return (Class) type;
        }
    }

    /**
     *Type所有类型指代的有：
     *  原始类型 (raw types)【对应Class】，
     *  参数化类型 (parameterizedtypes)【对应ParameterizedType】，
     *  数组类型 (array types)【对应GenericArrayType】，
     *  类型变量 (type variables)【对应TypeVariable】，
     *  基本数据类型(primitivetypes)【仍然对应Class】
     *
     *
     */
    public static Type getType(Type type, int i) {
        if (type instanceof ParameterizedType) { // 处理泛型类型
            return getGenericType((ParameterizedType) type, i);
        } else if (type instanceof TypeVariable) {
            return getType(((TypeVariable) type).getBounds()[0], 0); // 处理泛型擦拭对象
        } else {// class本身也是type，强制转型
            return type;
        }
    }
    public static Type getParameterizedType(Type type, int i) {
        if (type instanceof ParameterizedType) { // 处理泛型类型
            Type genericType = ((ParameterizedType) type).getActualTypeArguments()[i];
            return genericType;
        } else if (type instanceof TypeVariable) {
            return getType(((TypeVariable) type).getBounds()[0], 0); // 处理泛型擦拭对象
        } else {// class本身也是type，强制转型
            return type;
        }
    }

    public static Class getGenericClass(ParameterizedType parameterizedType, int i) {
        Type genericClass = parameterizedType.getActualTypeArguments()[i];
        if (genericClass instanceof ParameterizedType) { // 处理多级泛型
            return (Class) ((ParameterizedType) genericClass).getRawType();
        } else if (genericClass instanceof GenericArrayType) { // 处理数组泛型
            return (Class) ((GenericArrayType) genericClass).getGenericComponentType();
        } else if (genericClass instanceof TypeVariable) { // 处理泛型擦拭对象
            return getClass(((TypeVariable) genericClass).getBounds()[0], 0);
        } else {
            return (Class) genericClass;
        }
    }

    public static Type getGenericType(ParameterizedType parameterizedType, int i) {
        //getActualTypeArguments();
        //无论<>中有几层<>嵌套，这个方法仅仅脱去最外层的<>之后剩下的内容就作为这个方法的返回值。
        Type genericType = parameterizedType.getActualTypeArguments()[i];
        if (genericType instanceof ParameterizedType) { // 处理多级泛型
            return ((ParameterizedType) genericType).getRawType();
        } else if (genericType instanceof GenericArrayType) { // 处理数组泛型
            //getGenericComponentType();
            //无论从左向右有几个[]并列，这个方法仅仅脱去最右边的[]之后剩下的内容就作为这个方法的返回值。
            return ((GenericArrayType) genericType).getGenericComponentType();
        } else if (genericType instanceof TypeVariable) { // 处理泛型擦拭对象
            return getClass(((TypeVariable) genericType).getBounds()[0], 0);
        } else {
            return genericType;
        }
    }


    /**
     * 普通类反射获取泛型方式，获取需要实际解析的类型
     *
     * @param <T>
     * @return
     */
    public static <T> Type findNeedClass(Class<T> cls) {
        //以下代码是通过泛型解析实际参数,泛型必须传
        Type genType = cls.getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        Type finalNeedType;
        if (params.length > 1) {//这个类似是：CacheResult<SkinTestResult> 2层
            if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型参数");
            finalNeedType = ((ParameterizedType) type).getActualTypeArguments()[0];
            //Type rawType = ((ParameterizedType) type).getRawType();
        } else {//这个类似是:SkinTestResult  1层
            finalNeedType = type;
        }
        return finalNeedType;
    }

    /**
     * 普通类反射获取泛型方式，获取最顶层的类型
     */
    public static <T> Type findRawType(Class<T> cls) {
        Type genType = cls.getGenericSuperclass();
        return getGenericType((ParameterizedType) genType, 0);
    }

    /**
     * 反射创建类中的第几个泛型的实例
     * @param o
     * @param i
     * @param <T>
     * @return
     */
    public static <T> T getT(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }
}
