package com.tencent.routerdispatch;

import android.net.Uri;
import android.text.TextUtils;

import java.util.ArrayList;

public class UriPath {
    private String scheme;
    private String host;
    private int port;
    private String path;
    private ArrayList<Params> paramsList;

    public static UriPath createUriPath(Uri uri) {

        UriPath path = new UriPath();
        path.host = uri.getHost();
        path.port = uri.getPort();
        path.path = uri.getPath();
        path.paramsList = new ArrayList<>();
        String queryStr = uri.getQuery();
        if (!TextUtils.isEmpty(queryStr)) {
            String[] keyValues = queryStr.split("&");
            for (String keyvalue : keyValues) {
                String str[] = keyvalue.split("=");
                path.paramsList.add(new Params(str[0], str[1]));
            }
        }
        return path;
    }

    public ArrayList<Params> getParamsList() {
        return paramsList;
    }

    public String getScheme() {
        return scheme;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }
}
