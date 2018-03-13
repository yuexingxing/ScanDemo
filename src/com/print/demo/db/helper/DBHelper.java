package com.print.demo.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 
 *
 * @author yxx
 *
 * 2015-9-21
 */
public final class DBHelper {

	public static SQLiteDBOpenHelper SQLiteDBHelper;
	public static final String DATABASE_NAME = "ScanDemo.db";// 数据库名

	/**
	 * 数据库版本号
	 * 每次修改表中字段时，该版本号自动+1
	 * 避免PDA上程序卸载重装
	 * 修改后表被删除(包括表中数据)，创建一个新表
	 * */
	public static final int DATABASE_VERSION = 1;// 数据库版本号

	public static final String SCANDATA_TABLE = "ScanData";// 所有扫描保存表

	/**
	 * 扫描表
	 */
	public static final String KEY_CacheID = "CacheId";
	public static final String KEY_BillCode = "BillCode";
	public static final String KEY_GoodsName = "GoodsName";
	public static final String KEY_ScanTime = "ScanTime";

	private static final String CREATE_SCAN_TABLE = "create table "
			+ SCANDATA_TABLE
			+ " ("
			+ KEY_CacheID + " nvarchar(100) not null default(''), "
			+ KEY_BillCode + " nvarchar(50) not null default(''), "
			+ KEY_GoodsName + " nvarchar(50) not null default(''), "
			+ KEY_ScanTime + " nvarchar(50) not null default('') "
			+ ");";

	public DBHelper(Context context) {
		SQLiteDBHelper = new SQLiteDBOpenHelper(context);
	}

	public class SQLiteDBOpenHelper extends SQLiteOpenHelper {
		public SQLiteDBOpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			Log.v("upload", "onCreate");

			db.execSQL(CREATE_SCAN_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			Log.v("upload", "onUpgrade");
			try {

				db.execSQL("DROP TABLE IF EXISTS " + SCANDATA_TABLE);
				onCreate(db);

				if(oldVersion != newVersion) {
					Log.v("zd", "数据库升级成功！");
				} else {
					Log.v("zd", "数据库升级失败！");
				}

			} catch(Exception e) {
				e.printStackTrace();
				Log.v("zd", "数据库升级失败！");
			}



		}
	}
}
