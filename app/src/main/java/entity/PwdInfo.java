package entity;

import android.widget.PopupWindow;

/**
 * Created by Administrator on 2016/7/27.
 */
public class PwdInfo {

    /**
     * “result”:0
     “explain”:"已成功发送到邮箱"
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
