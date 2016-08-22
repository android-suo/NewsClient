package entity;

/**
 * Created by Administrator on 2016/7/28.
 */
public class CommentSendInfo {
    /**
        “result”:0,
        “explain”：“发布成功”
    */

    private int result;
    private String explain;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
