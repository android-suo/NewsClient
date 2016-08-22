package utils;

import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import com.android.volley.Network;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import junit.runner.Version;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import base.Constant;
import entity.NewsEntity;
import entity.VersionInfo;
import manager.UserManager;

/**
 * 获取旧版本，下载新版本，更新版本
 * 1、获取旧版本信息
 * 2、通过网络请求获取最新版本，判断是否更新
 * 3、确定更新后，下载最新版本APK，安装
 * Created by Administrator on 2016/7/28.
 */
public class UpdateUtils {
    private static String version;//新版本号
    private static String link;//新版本地址
    private static boolean isUpdate;//是否有新版本

    public static void download(Context context){
        getNewVersion(context);
        if (isUpdate) {
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(link));
            //设置网络类型，手机数据流量和wifi
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);
            String[] str=link.split("/");
            String apkName = str[str.length - 1];
            //设置下载到的位置
            request.setDestinationInExternalFilesDir(context, null, "sdcard/android/data/edu.feicui.news/files/apkName");
            //是否显示通知
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            downloadManager.enqueue(request);
        } else {
            Toast.makeText(context, "当前已是最新版本", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 获取新版本信息
     * @param context
     */
    private static void getNewVersion(final Context context) {
        //        update?imei=唯一识别号&pkg=包名&ver=版本
        String imei = UserManager.getIMEI(context);
        String packageName = context.getPackageName();
        LogWrapper.e("update",packageName+"update   "+imei);
        Map<String, String> map = new HashMap<>();
        map.put("imei", imei);
        map.put("pkg", packageName);
        map.put("ver", "1");
        UserManager.getmUserManager(context)
                .getComment(Constant.METHOD_GET, Constant.PATH_VERSION, map, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        LogWrapper.e("onResponse","onResponse************");
                       VersionInfo versionInfo = ParserUtils.getmParserUtils().parseVersion(jsonObject);
                        version = versionInfo.getVersion();
                        link=versionInfo.getLink();
                        LogWrapper.e("version","version"+version);
                        if (getOldVersion(context)< Integer.parseInt(version)) {
                            isUpdate=true;
                        }
                        isUpdate=false;
                        LogWrapper.e("versionInfo",version+"versionInfo---"+link);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        LogWrapper.e("volleyError","volleyError"+volleyError.networkResponse.statusCode);
                    }
                });

    }

    /**
     * 获取app当前版本
     * @param context
     * @return
     */
    public static int getOldVersion(Context context)
    {
        try {
            LogWrapper.e("packageName","packageName--"+context.getPackageName());
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
