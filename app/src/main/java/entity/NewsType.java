package entity;

import java.util.List;

/**
 * Created by Administrator on 2016/7/29.
 */
public class NewsType {
    /**
     * gid: int
     -group: String
     -subgrp: List<SubType>
     */
    private int gid;
    private String group;
    private List<SubType> subgrp;

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<SubType> getSubgrp() {
        return subgrp;
    }

    public void setSubgrp(List<SubType> subgrp) {
        this.subgrp = subgrp;
    }
}
