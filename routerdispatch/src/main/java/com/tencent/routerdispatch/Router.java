package com.tencent.routerdispatch;


import android.app.Activity;
import com.tencent.router.api.RouterInit;

import java.util.HashMap;

public class Router {
    public static HashMap<String,  Class<? extends Activity>> activityHashMap = new HashMap<>();

    public static void addActivity(String uriStr, Class<? extends Activity> activity) {
        activityHashMap.put(uriStr, activity);
    }

    /**
     * 初始化路由表的时候调用，比如第一次跳转的时候
     */
    public static void initRouteMap() {
        if (activityHashMap.isEmpty()) {
            RouterInit.init();
        }
    }

    public static Class<? extends Activity> getActivity(String uriStr) {
        initRouteMap();
        String key = uriStr.substring(uriStr.indexOf("//") + 2);
        return activityHashMap.get(key);
    }

}
