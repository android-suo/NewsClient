package entity;

import java.io.Serializable;

/**
 * 服务器返回用户中心详细信息
 * Created by Administrator on 2016/7/26.
 */
public class UserCenterDetailInfo implements Serializable{
    /*
    “time”:登录时间
	“address”:北京市朝阳区
	“device”:0
     */
    private  String time;
    private  String address;
    private  int device;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDevice() {
        return device;
    }

    public void setDevice(int device) {
        this.device = device;
    }
}
