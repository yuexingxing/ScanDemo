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
	public static final String DATABASE_NAME = "ScanDemo.db";// ���ݿ���

	/**
	 * ���ݿ�汾��
	 * ÿ���޸ı����ֶ�ʱ���ð汾���Զ�+1
	 * ����PDA�ϳ���ж����װ
	 * �޸ĺ��ɾ��(������������)������һ���±�
	 * */
	public static final int DATABASE_VERSION = 1;// ���ݿ�汾��

	public static final String SCANDATA_TABLE = "ScanData";// ����ɨ�豣���

	/**
	 * ɨ���
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
					Log.v("zd", "���ݿ������ɹ���");
				} else {
					Log.v("zd", "���ݿ�����ʧ�ܣ�");
				}

			} catch(Exception e) {
				e.printStackTrace();
				Log.v("zd", "���ݿ�����ʧ�ܣ�");
			}



		}
	}
}
