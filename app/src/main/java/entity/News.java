package entity;

import java.io.Serializable;

/**
 * ʵ����
 * ���������Data���Ӧ�����
 * @author Administrator
 *
 */
public class News implements Serializable{
	private String summary;//����ժҪ
	private String icon;//ͼ���ַ
	private String stamp;//��������
	private String title;//����
	private String nid;//id
	private String link;//����
	private int type;//����

	public News(int type, String nid, String stamp, String icon,String title, String summary,String link ) {
		this.summary = summary;
		this.icon = icon;
		this.stamp = stamp;
		this.title = title;
		this.nid = nid;
		this.link = link;
		this.type = type;
	}

	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getStamp() {
		return stamp;
	}
	public void setStamp(String stamp) {
		this.stamp = stamp;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNid() {
		return nid;
	}
	public void setNid(String nid) {
		this.nid = nid;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
