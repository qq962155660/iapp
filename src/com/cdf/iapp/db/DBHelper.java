package com.cdf.iapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
	public Context context;
	public static final String DATABASE_NAME = "data.db";
	public static final int DATABASE_VERSION = 1;
	public static final String SAVE_PRAISES_LISI_TABLE="SAVE_PRAISES_LISI_TABLE";
	//保存好友点赞name的数据表
	public static final String SAVE_PRAISES_LISI_TABLE_CREATEOR = "create table SAVE_PRAISES_LISI_TABLE (" +
			"username text, " +//用户名
			"time text, "+//时间发表
			"name text)";//点赞人name

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.beginTransaction();
		try {
			db.execSQL(SAVE_PRAISES_LISI_TABLE_CREATEOR);
			db.setTransactionSuccessful();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String CxlsSql = "DROP TABLE IF EXISTS SAVE_PRAISES_LISI_TABLE";
		db.execSQL(CxlsSql);
		onCreate(db);
	}

}
