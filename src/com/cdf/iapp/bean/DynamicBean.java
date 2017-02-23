package com.cdf.iapp.bean;

import java.util.ArrayList;
import java.util.List;

public class DynamicBean {

	
	private String username;	//发布者
	private String content;
	private String picture;
	private String createTime;
	private String auid;//发布者Id
	
	private ArrayList<PraiseBean> praises = new ArrayList<PraiseBean>();//点赞人s
	private ArrayList<CommentBean> mCommentList=new ArrayList<CommentBean>();//评论集合
	
	
	
	public String getAuid() {
		return auid;
	}
	public void setAuid(String auid) {
		this.auid = auid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public ArrayList<PraiseBean> getPraises() {
		return praises;
	}
	public void setPraises(ArrayList<PraiseBean> praises) {
		this.praises = praises;
	}
	public ArrayList<CommentBean> getmCommentList() {
		return mCommentList;
	}
	public void setmCommentList(ArrayList<CommentBean> mCommentList) {
		this.mCommentList = mCommentList;
	}
	

	
	
	
}
