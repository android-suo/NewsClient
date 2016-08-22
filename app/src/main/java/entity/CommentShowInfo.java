package entity;

/**
 * Created by Administrator on 2016/7/27.
 */
public class CommentShowInfo {


    /**
     * cid : 评论编号
     * uid : 评论者名字
     * portrait : 用户头像链接
     * stamp : 评论时间
     * content : 评论内容
     */

    private String cid;
    private String uid;
    private String portrait;
    private String stamp;
    private String content;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
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

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
