package utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import base.Constant;
import base.MyRequest;
import entity.News;
import entity.NewsEntity;

/**
 * Created by Administrator on 2016/7/22.
 * 网络访问封装类
 */
public class HttpWrapper {

    public Context mContext;
    private static HttpWrapper mHttpWrapper;
    private HttpWrapper(Context context) {
        this.mContext=context;
    }
    public static HttpWrapper getmHttpWrapper(Context context) {
        if (mHttpWrapper==null) {
            return mHttpWrapper=new HttpWrapper(context);
        }
        return  mHttpWrapper;
    }

    /**
     * 访问网路请求
     * @param method  访问模式 get？post
     * @param path	网址
     * @param map	请求数据
     * @param listener  正确响应请求，用来接受返回数据
     * @param error 错误响应请求，返回错误代码
     */

    public  void httpRequest(int method, String path, Map<String,String> map, Response.Listener listener, Response.ErrorListener error)
    {
        StringBuffer pathBuf = MapToString(path, map);
        MyRequest.setRequest(mContext,new JsonObjectRequest(method, pathBuf.toString(), null,listener,error));
    }

    /**
     * 拼接获取访问地址
     * @param path
     * @param map
     * @return
     */
    @NonNull
    private StringBuffer MapToString(String path, Map<String, String> map) {
        //获取网址
        StringBuffer pathBuf=new StringBuffer();
        pathBuf.append(path).append("?");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            pathBuf.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        pathBuf.deleteCharAt(pathBuf.length()-1);
        return pathBuf;
    }
}
