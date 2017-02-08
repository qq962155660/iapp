package com.cdf.iapp.bean;

import java.util.ArrayList;
import java.util.List;

public class FriendsGroupBean {

	
	private String name;
	private String photoUrl;//图像url
	private String content;
	private String imageUrl;//发表内容url
	private String time;
	
	private ArrayList<PraiseBean> praises = new ArrayList<PraiseBean>();//点赞人s
	private ArrayList<CommentBean> mCommentList=new ArrayList<CommentBean>();//评论集合
	
	public FriendsGroupBean() {
		super();
	}

	public FriendsGroupBean(String name, String photoUrl, String content, String imageUrl, String time,
			ArrayList<PraiseBean> praises, ArrayList<CommentBean> mCommentList) {
		super();
		this.name = name;
		this.photoUrl = photoUrl;
		this.content = content;
		this.imageUrl = imageUrl;
		this.time = time;
		this.praises = praises;
		this.mCommentList = mCommentList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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

	@Override
	public String toString() {
		return "FriendsGroupBean [name=" + name + ", photoUrl=" + photoUrl + ", content=" + content + ", imageUrl="
				+ imageUrl + ", time=" + time + ", praises=" + praises + ", mCommentList=" + mCommentList + "]";
	}




	
	
	
}
