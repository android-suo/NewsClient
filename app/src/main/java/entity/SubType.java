package entity;

/**
 * Created by Administrator on 2016/7/29.
 */
public class SubType {
    /**
     * -subid: int
     -subgroup: String
     */
    private int subid;
    private  String subgroup;

    public SubType(int subid, String subgroup) {
        this.subid = subid;
        this.subgroup = subgroup;
    }

    public int getSubid() {
        return subid;
    }

    public void setSubid(int subid) {
        this.subid = subid;
    }

    public String getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(String subgroup) {
        this.subgroup = subgroup;
    }
}
