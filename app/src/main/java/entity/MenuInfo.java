package entity;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/7/20.
 */
public class MenuInfo {
    private String title;
    private String engTitle;

    public String getEngTitle() {
        return engTitle;
    }

    public void setEngTitle(String engTitle) {
        this.engTitle = engTitle;
    }

    private int icon;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
