package com.cdf.iapp.sys;

public class Global {

	public static String URL_IP = "http://192.168.0.109:8080";
	public static String URL_PRO = "/renren/app";
	public static String URL_MAIN = URL_IP + URL_PRO;
	
	/**登陆**/
	public static String URL_LOGIN = URL_MAIN + "/user/login";
	/**获取当前用户信息**/
	public static String URL_GETCURRENTUSER = URL_MAIN + "/user/getUser";
	/**获取关注列表**/
	public static String URL_GETATTENTIONS = URL_MAIN + "/user/getAttentions";
	/**获取关注者信息**/
	public static String URL_GETATTENTIONBYID = URL_MAIN + "/user/getAttentionById";
	/**获取好友动态列表**/
	public static String URL_GETDYNAMICS = URL_MAIN + "/dynamic/getDynamics";
	/**发表动态**/
	public static String URL_CREATEDYNAMIC = URL_MAIN + "/dynamic/crateDynamic";
}
