package com.cdf.iapp.db;


import java.util.ArrayList;



import com.cdf.iapp.bean.PraiseBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;


public class QueryDAO {
	private static QueryDAO dao;
	private Context context;


	private QueryDAO(Context context){
		this.context = context;
	}

	public static synchronized QueryDAO getInstance(Context context){
		if(dao == null){
			dao = new QueryDAO(context);
		}
		return dao;
	}

	private synchronized SQLiteDatabase getConnection() throws SQLiteException {
		return new DBHelper(context).getWritableDatabase();
	}

	/**
	 * 将其属性值存入好友点赞数据表
	 * @param name
	 * @param pwd
	 */
	public synchronized void inPraisesUpdate(String username,String time,String name){
		SQLiteDatabase db=getConnection();
		db.beginTransaction();
		ContentValues cValues=new ContentValues();
		cValues.put("username", username);
		cValues.put("time", time);
		cValues.put("name", name);
		db.insert(DBHelper.SAVE_PRAISES_LISI_TABLE, null, cValues);
		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
	}
	/**
	 * 查询某用户名下某发表人发表的内容下的点赞人
	 */
	public synchronized ArrayList<PraiseBean> queryAskList(String userName,String time){//用户名和发表说说时间
		SQLiteDatabase db=null;
		Cursor cursor=null;
		ArrayList<PraiseBean>list=new ArrayList<PraiseBean>();
		try {
			db=getConnection();
			cursor=db.rawQuery("select * from "+DBHelper.SAVE_PRAISES_LISI_TABLE+ " where username = ? and time = ?", new String[]{userName,time});
			PraiseBean bean;
			while(cursor.moveToNext()){
				bean=new PraiseBean();
				bean.setUsername(cursor.getString(cursor.getColumnIndex("username")));
				bean.setTime(cursor.getString(cursor.getColumnIndex("time")));
				bean.setName(cursor.getString(cursor.getColumnIndex("name")));
				list.add(bean);
			}
		}
		catch (SQLiteException e) {
			e.printStackTrace();
		}
		finally {
			if (null != cursor) {
				cursor.close();
			}
			if (null != db) {
				db.close();
			}
		}
		return list;
	}
	/** 
	 * 方法：检查好友数据表中当前好友是否存在
	 * @param db 
	 * @param tableName 表名 
	 * @param columnName 列名 
	 * @param columnName 字段名
	 * @return 
	 */  
	public boolean checkFriendsColumnExists(String userName,String time,String QueryName) {  
		SQLiteDatabase db=getConnection();
		Cursor cursor = null ;  
		ArrayList<PraiseBean> list = new ArrayList<PraiseBean>();
		try{  
			db=getConnection();
			cursor=db.rawQuery("select * from "+DBHelper.SAVE_PRAISES_LISI_TABLE + " where username = ? and time = ? and name = ?", new String[]{userName,time,QueryName});
			PraiseBean bean;
			while(cursor.moveToNext()){
				bean=new PraiseBean();
				bean.setUsername(cursor.getString(cursor.getColumnIndex("username")));
				bean.setTime(cursor.getString(cursor.getColumnIndex("time")));
				bean.setName(cursor.getString(cursor.getColumnIndex("name")));
				
				list.add(bean);
			}
		}catch (Exception e){  
			Log.e("TAG","checkColumnExists..." + e.getMessage()) ;  
		}finally{  
			if(null != cursor && !cursor.isClosed()){  
				cursor.close() ;  
			}  
		}  
		
		return list.size() > 0 ;  
	}  
}
