package com.cdf.iapp.bean;
import java.io.Serializable;

@SuppressWarnings("serial")
public class CommentBean implements Serializable{
	
	private String name;//说说发表人
	private String toname;//说说评论人
	private String content;//评论内容
	public CommentBean() {
		super();
	}
	public CommentBean(String name, String toname, String content) {
		super();
		this.name = name;
		this.toname = toname;
		this.content = content;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getToname() {
		return toname;
	}
	public void setToname(String toname) {
		this.toname = toname;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "PingLunBean [name=" + name + ", toname=" + toname + ", content=" + content + "]";
	}
	
	
	
	

}
