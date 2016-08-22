package entity;

/**
 * Created by Administrator on 2016/7/28.
 */
public class VersionInfo {

    /**
     * pkgName : 包名
     * version : 版本号
     * link : 下载地址
     * md5 : MD5检验值
     */

    private String pkgName;
    private String version;
    private String link;
    private String md5;

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
