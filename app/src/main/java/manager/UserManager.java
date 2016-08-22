package manager;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.android.volley.Response;

import org.json.JSONObject;

import java.util.Map;

import utils.HttpWrapper;

/**
 * 获取用户信息
 * Created by Administrator on 2016/7/25.
 */
public class UserManager {
    Context mContext;
    private static UserManager mUserManager;
    private UserManager(Context context) {
        this.mContext=context;
    }
    public static UserManager getmUserManager(Context context)
    {
        if (mUserManager==null) {
            synchronized (UserManager.class) {
                mUserManager=new UserManager(context);
            }
        }
        return mUserManager;
    }

    /**
     * 登录请求
     * @param method
     * @param path
     * @param map
     * @param listener
     * @param error
     */
    public void login(int method, String path, Map<String, String> map, Response.Listener<JSONObject> listener, Response.ErrorListener error)
    {
        HttpWrapper httpWrapper = HttpWrapper.getmHttpWrapper(mContext);
        httpWrapper.httpRequest(method,path,map,listener,error);

    }

    /**
     * 注册帐户请求
     * @param method
     * @param path
     * @param map
     * @param listener
     * @param error
     */
    public void Register(int method, String path, Map<String, String> map, Response.Listener<JSONObject> listener, Response.ErrorListener error)
    {
        HttpWrapper httpWrapper = HttpWrapper.getmHttpWrapper(mContext);
        httpWrapper.httpRequest(method,path,map,listener,error);

    }

    /**
     * 获取用户中心数据请求
     * @param method
     * @param path
     * @param map
     * @param listener
     * @param error
     */
    public void getUserMessage(int method, String path, Map<String, String> map, Response.Listener<JSONObject> listener, Response.ErrorListener error)
    {
        HttpWrapper httpWrapper = HttpWrapper.getmHttpWrapper(mContext);
        httpWrapper.httpRequest(method,path,map,listener,error);

    }

    /**
     * 获取找回密码返回的响应
     * @param method
     * @param path
     * @param map
     * @param listener
     * @param error
     */
    public void getPasswordBack(int method, String path, Map<String, String> map, Response.Listener<JSONObject> listener, Response.ErrorListener error)
    {
        HttpWrapper httpWrapper = HttpWrapper.getmHttpWrapper(mContext);
        httpWrapper.httpRequest(method,path,map,listener,error);

    }

    /**
     * 获得评论响应
     * @param method
     * @param path
     * @param map
     * @param listener
     * @param error
     */
    public void getComment(int method, String path, Map<String, String> map, Response.Listener<JSONObject> listener, Response.ErrorListener error)
    {
        HttpWrapper httpWrapper = HttpWrapper.getmHttpWrapper(mContext);
        httpWrapper.httpRequest(method,path,map,listener,error);

    }

    /**
     * 获取手机的 IMEI 值
     * @param context
     * @return
     */
    public static String getIMEI(Context context){
        TelephonyManager telephonyManager= (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

}
