package entity;

import java.io.Serializable;

/**
 * ʵ����
 * ���ŵ�������Ϣ��
 * @author Administrator
 *
 * @param <T>
 */
public class NewsEntity<T> implements Serializable{
	
	private String message;
	private int status;
	private T data;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
