package android.app.sms.entity;

import java.io.Serializable;

public class SMSInfo implements Serializable {
	
	private static final long serialVersionUID = -5005104202130085652L;
	
	private int id;
	private String pn;
	private String type;
	private String body;
	private String time;
	private String url;
	
	public SMSInfo(int id, String pn, String type, String body, String url, String time) {
		this.id = id;
		this.pn = pn;
		this.url = url;
		this.type = type;
		this.body = body;
		this.time = time;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPn() {
		return pn;
	}
	public void setPn(String pn) {
		this.pn = pn;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
