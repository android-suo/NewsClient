package utils;

import android.content.Context;
import android.content.SharedPreferences;

import entity.UserCenterInfo;

/**
 * Created by Administrator on 2016/7/25.
 */
public class SharedPreUtils {
    /**登录信息共享参数的文件名*/
    private static final  String PRE_NAME_LOGIN="login";
    private SharedPreferences mSharedPreferences;
    private static SharedPreUtils mSharedPreUtils;
    private SharedPreUtils(Context context)
    {
        mSharedPreferences=context.getSharedPreferences(PRE_NAME_LOGIN,Context.MODE_PRIVATE);
    }
    public static SharedPreUtils getSharedPreUtils(Context context)
    {
        if (mSharedPreUtils==null) {
            synchronized (SharedPreUtils.class) {
                mSharedPreUtils=new SharedPreUtils(context);
            }
        }
        return mSharedPreUtils;
    }
    /**
     * 保存登录用户的令牌，以便后来使用
     * @param token 用户的令牌
     * @param explain
     */
    public void save(String token,String explain)
    {
        mSharedPreferences.edit().putString("token",token).putString("explain",explain).commit();
    }
    public void saveNewsId(String nid)
    {
        mSharedPreferences.edit().putString("nid",nid).commit();
    }

    /**
     * 保存用户中心的数据
     * @param user
     */
    public void saveUserInfo(UserCenterInfo user)
    {
        mSharedPreferences.edit().putString("uid",  user.getUid()).putString("comnum",  user.getComnum())
                .putString("integration",  user.getIntegration()).putString("portrait",  user.getPortrait()).commit();
    }
    /**
     * 获取令牌信息
     * @return
     */
    public String getToken()
    {
        return mSharedPreferences.getString("token","unkown");
    }

    /**
     * 获取新闻id
     * @return
     */
    public String getNewsId()
    {
        return mSharedPreferences.getString("nid","unkown");
    }

    /**
     * 清理共享参数内容
     */
    public  void clearToken()
    {
        mSharedPreferences.edit().clear().commit();
    }

    /**
     * 获得共享参数对象
     * @return
     */
    public SharedPreferences getSharedPreferences()
    {
        return mSharedPreferences;
    }
}
