package entity;

import java.io.Serializable;
import java.util.List;

/**
 * 服务器返回用户中心数据
 * Created by Administrator on 2016/7/26.
 */
public class UserCenterInfo implements Serializable{
/*
    “uid”:用户名
    “portrait”:用户图标
    “integration”:用户积分票总数
    “comnum”:评论总数
    “loginlog”: */
    private  String uid;
    private  String portrait;
    private  String integration;
    private  String comnum;
    private List<UserCenterDetailInfo> loginlog;

    public List<UserCenterDetailInfo> getLoginlog() {
        return loginlog;
    }

    public void setLoginlog(List<UserCenterDetailInfo> loginlog) {
        this.loginlog = loginlog;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getIntegration() {
        return integration;
    }

    public void setIntegration(String integration) {
        this.integration = integration;
    }

    public String getComnum() {
        return comnum;
    }

    public void setComnum(String comnum) {
        this.comnum = comnum;
    }


}
