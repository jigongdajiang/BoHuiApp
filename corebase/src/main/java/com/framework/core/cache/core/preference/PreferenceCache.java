package com.framework.core.cache.core.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.framework.core.cache.core.BaseCache;
import com.framework.core.log.PrintLog;
import com.framework.core.util.CUtil;
import com.framework.core.util.TUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author : gaojigong
 * @date : 2017/11/14
 * @description:
 */


public class PreferenceCache extends BaseCache {
    public static final String SYS_PREFERENCE = "sys_prefs";
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private String preferenceName;
    private AbsObjectStrategy objectStrategy;

    public PreferenceCache(Context context) {
        this(context,SYS_PREFERENCE);
    }
    public PreferenceCache(Context context, String name) {
        this(context,name,null);
    }
    public PreferenceCache(Context context, String name,AbsObjectStrategy strategy) {
        if(TextUtils.isEmpty(name)){
            name = SYS_PREFERENCE;
        }
        this.preferenceName = name;
        if(strategy == null){
            strategy = new GsonObjectStrategy();
        }
        this.objectStrategy = strategy;
        mPreferences = context.getApplicationContext().getSharedPreferences(preferenceName,Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }
    @Override
    protected boolean doContainsKey(String key) {
        return true;
    }

    @Override
    protected boolean isExpiry(String key, long existTime) {
        return false;
    }

    @Override
    protected <T> T doLoad(Type type, String key) {
        Object value;
        try {
            if(TUtil.getClass(type,0) == String.class){
                value = mPreferences.getString(key,"");
            }else if(TUtil.getClass(type,0) == Boolean.class){
                value = mPreferences.getBoolean(key,false);
            }else if(TUtil.getClass(type,0) == Integer.class){
                value = mPreferences.getInt(key,-1);
            }else if(TUtil.getClass(type,0) == Float.class){
                value = mPreferences.getFloat(key,-1.0f);
            }else if(TUtil.getClass(type,0) == Long.class){
                value = mPreferences.getLong(key,-1);
            }else {
                String string = mPreferences.getString(key, "");
                //将16进制的数据转为数组，准备反序列化
                value = objectStrategy.read(string,type);
            }
            return (T)value;
        }catch (Exception e){
            PrintLog.e(e);
        }
        return null;
    }

    @Override
    protected <T> boolean doSave(String key, T object) {
        if (object instanceof String) {
            mEditor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            mEditor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            mEditor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            mEditor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            mEditor.putLong(key, (Long) object);
        } else {

            String byteString = objectStrategy.save(object);
            //保存
            mEditor.putString(key, byteString);
        }
        return SharedPreferencesCompat.apply(mEditor);
    }

    @Override
    protected boolean doRemove(String key) {
        mEditor.remove(key);
        return SharedPreferencesCompat.apply(mEditor);
    }

    @Override
    protected boolean doClear() {
        mEditor.clear();
        return SharedPreferencesCompat.apply(mEditor);
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     *
     */
    public static class SharedPreferencesCompat
    {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private static Method findApplyMethod()
        {
            try
            {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e)
            {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static boolean apply(SharedPreferences.Editor editor)
        {
            try
            {
                if (sApplyMethod != null)
                {
                    sApplyMethod.invoke(editor);
                    return true;
                }
            } catch (IllegalArgumentException e) {
                return false;
            } catch (IllegalAccessException e) {
                return false;
            } catch (InvocationTargetException e) {
                return false;
            }
            return editor.commit();
        }
    }
}
