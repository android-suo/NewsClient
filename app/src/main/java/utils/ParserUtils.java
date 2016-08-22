package utils;

import android.support.v4.view.PagerAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import com.google.gson.JsonObject;


import org.json.JSONObject;

import java.util.List;

import entity.CommentSendInfo;
import entity.CommentShowInfo;
import entity.LoginInfo;
import entity.News;
import entity.NewsEntity;
import entity.NewsType;
import entity.PwdInfo;
import entity.RegisterInfo;
import entity.SubType;
import entity.UserCenterInfo;
import entity.VersionInfo;

/**
 * Created by Administrator on 2016/7/22.
 */
public class ParserUtils {

    private static ParserUtils mParserUtils;
    private ParserUtils() {

    }
    public static ParserUtils getmParserUtils() {
        if (mParserUtils==null) {
            mParserUtils=new ParserUtils();
            return mParserUtils;
        }
        return mParserUtils;
    }

    /**
     * 解析新闻列表信息
     * @param obj
     * @return
     */
    public NewsEntity<List<News>> parseNewsList(JSONObject obj) {
        Gson gson = new Gson();
        return gson.fromJson(obj.toString(), new TypeToken<NewsEntity<List<News>>>() {
        }.getType());
    }

    /**
     * 解析登录信息
     * @param obj
     * @return
     */
    public NewsEntity<LoginInfo> parseLogin(JSONObject obj) {
        Gson gson = new Gson();
        return gson.fromJson(obj.toString(), new TypeToken<NewsEntity<LoginInfo>>() {
        }.getType());
    }

    /**
     * 解析注册信息
     * @param obj
     * @return
     */
    public NewsEntity<RegisterInfo> parseRegister(JSONObject obj) {
        Gson gson = new Gson();
        return gson.fromJson(obj.toString(), new TypeToken<NewsEntity<RegisterInfo>>() {
        }.getType());
    }

    /**
     * 解析用户中心数据
     * @param obj
     * @return
     */
    public NewsEntity<UserCenterInfo> parseUserMessage(JSONObject obj) {
        Gson gson = new Gson();
        return gson.fromJson(obj.toString(), new TypeToken<NewsEntity<UserCenterInfo>>() {
        }.getType());
    }

    /**
     * 解析找回密码返回信息
     * @param obj
     * @return
     */
    public NewsEntity<PwdInfo> parsePassword(JSONObject obj) {
        Gson gson = new Gson();
        return gson.fromJson(obj.toString(), new TypeToken<NewsEntity<PwdInfo>>() {
        }.getType());
    }

    /**
     * 评论显示
     * @param obj
     * @return
     */
    public NewsEntity<List<CommentShowInfo>> parseCommentShow(JSONObject obj) {
        Gson gson = new Gson();
        return gson.fromJson(obj.toString(), new TypeToken<NewsEntity<List<CommentShowInfo>>>() {
        }.getType());
    }

    /**
     * 评论发布
     * @param obj
     * @return
     */
    public NewsEntity<CommentSendInfo> parseCommentSend(JSONObject obj) {
        Gson gson = new Gson();
        return gson.fromJson(obj.toString(), new TypeToken<NewsEntity<CommentSendInfo>>() {
        }.getType());
    }

    /**
     * 解析评论数量数据
     * @param obj
     * @return
     */
    public NewsEntity<String> parseCommentNum(JSONObject obj) {
        Gson gson = new Gson();
        return gson.fromJson(obj.toString(), new TypeToken<NewsEntity<String>>() {
        }.getType());
    }

    /**
     * 解析版本信息
     * @param obj
     * @return
     */
    public VersionInfo parseVersion(JSONObject obj) {
        Gson gson = new Gson();
        return gson.fromJson(obj.toString(), new TypeToken<VersionInfo>() {
        }.getType());
    }

    /**
     * 解析新闻类型数据
     * @param obj
     * @return
     */
    public NewsEntity<List<NewsType>> parseNewsType(JSONObject obj) {
        Gson gson = new Gson();
        return gson.fromJson(obj.toString(), new TypeToken<NewsEntity<List<NewsType>>>() {
        }.getType());
    }
}
