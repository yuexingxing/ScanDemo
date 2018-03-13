package com.print.demo.db.dao;

import java.util.List;
import com.print.demo.data.PrintInfo;
import com.print.demo.db.helper.DBHelper;
import com.print.demo.util.ToolsUtil;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class ScanDataDao{
	
	private SQLiteDatabase db = null;

	public ScanDataDao() {
		
	}

	public boolean addScanData(PrintInfo info) {

		db = DBHelper.SQLiteDBHelper.getWritableDatabase();
		boolean flag = false;
		db.beginTransaction();
		try {

			ContentValues cv = new ContentValues();

			cv.put(DBHelper.KEY_CacheID, info.getId());
			cv.put(DBHelper.KEY_BillCode, info.getBillcode());
			cv.put(DBHelper.KEY_GoodsName, info.getName());
			cv.put(DBHelper.KEY_ScanTime, ToolsUtil.getTime());

			long newRowId = db.insert(DBHelper.SCANDATA_TABLE, null, cv);
			if (newRowId > 0) {
				flag = true;
			}

			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}

		return flag;
	}

	/**
	 * 查询所有数据
	 * @return
	 */
	public void selectAllData(List<PrintInfo> list) {

		db = DBHelper.SQLiteDBHelper.getReadableDatabase();
		String sql = "select * from " + DBHelper.SCANDATA_TABLE;
		Cursor cursor = db.rawQuery(sql, null);

		while (cursor.moveToNext()) {

			PrintInfo info = new PrintInfo();
			info.setId(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_CacheID)));
			info.setBillcode(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_BillCode)));
			info.setName(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_GoodsName)));
			info.setTime(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_ScanTime)));

			if(TextUtils.isEmpty(info.getBillcode())){
				continue;
			}
			
			list.add(info);
		}

		cursor.close();
	}

	public boolean selectScanData(String billcode, PrintInfo scanData) {

		boolean flag = false;
		db = DBHelper.SQLiteDBHelper.getReadableDatabase();
		String sql = "select * from " + DBHelper.SCANDATA_TABLE + " where "
				+ DBHelper.KEY_BillCode + " ='" + billcode + "'";
		Cursor cursor = db.rawQuery(sql, null);

		if (cursor.moveToNext()) {
			flag = true;
		}

		cursor.close();
		return flag;
	}

	public void clearScanData() {

		db = DBHelper.SQLiteDBHelper.getWritableDatabase();

		String sql = "delete from " + DBHelper.SCANDATA_TABLE;
		db.execSQL(sql);
	}

	public boolean deleteScanData(PrintInfo info) {

		boolean flag = false;
		//		String whereClause = "ScanHawb = ? and ScanType = ? and Supply = ? and UploadStatus = ?";
		//		String[] whereArgs = { scanData.getScanHawb(), scanData.getScanType(), scanData.getSupply(), 0 + "" };
		//
		//		for(int i=0; i<whereArgs.length; i++){
		//
		//			Log.v("zd", whereArgs[i]);
		//		}
		//
		//		db = DBHelper.SQLiteDBHelper.getWritableDatabase();
		//		try {
		//			db.beginTransaction();
		//			int a = db.delete(DBHelper.SCANDATA_TABLE, whereClause, whereArgs);
		//			if (a != 0) {
		//				flag = true;
		//			}
		//			db.setTransactionSuccessful();
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		} finally {
		//			db.endTransaction();
		//		}
		return flag;
	}


}
