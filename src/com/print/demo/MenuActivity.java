package com.print.demo;

import java.util.ArrayList;
import java.util.List;
import com.print.demo.data.PrintInfo;
import com.print.demo.db.dao.ScanDataDao;
import com.print.demo.firstview.ConnectAvtivity;
import com.print.demo.util.ExcelUtil;
import com.print.demo.util.ExcelUtil.CallBack;
import com.print.demo.util.ToolsUtil;
import com.print.demorego.R;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

/** 
 * 主界面
 * 
 * @author yxx
 *
 * @date 2018-3-12 下午7:08:07
 * 
 */
public class MenuActivity extends Activity {

	private TextView tvVersion;
	private TextView tvCount;
	public static List<PrintInfo> sourceList = new ArrayList<PrintInfo>();//本地SD卡Excel表里面的数据
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		tvCount = (TextView) findViewById(R.id.activity_menu_count);
		tvVersion = (TextView) findViewById(R.id.activity_menu_version);
		tvVersion.setText("v" + ToolsUtil.getVersionName());
//		initData();
	}

	public void onResume(){
		super.onResume();

		//增加失效时间，在规定时间点之前可以使用
		String limitTime = getResources().getString(R.string.limitTime);
		if(limitTime.compareTo(ToolsUtil.getTime()) < 0){
//			finish();
		}
	}

	private void initData(){

		new ScanDataDao().selectAllData(sourceList);
		tvCount.setText(sourceList.size() + "条");
	}

	/**
	 * 蓝牙设置
	 * @param v
	 */
	public void bluetoothSetting(View v){

		Intent intent = new Intent(MenuActivity.this, ConnectAvtivity.class);
		startActivity(intent);
	}
	
	/**
	 * 开始扫描
	 * @param v
	 */
	public void startScan(View v){
		
//		if(mBconnect == false){
//			ToolsUtil.showToast("请先连接打印机");
//			return;
//		}
		
		Intent intent = new Intent(MenuActivity.this, PrinterActivity.class);
		startActivity(intent);
	}

	/**
	 * 导入数据
	 * @param v
	 */
	public void inputExcel(View v){

		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(intent,1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == Activity.RESULT_OK) {  
			Uri uri = data.getData();  
			if ("file".equalsIgnoreCase(uri.getScheme())){//使用第三方应用打开  

				if(uri.getPath().endsWith(".xls") || uri.getPath().endsWith(".xlsx")){

					new ExcelUtil().readExcel(uri.getPath(), sourceList, new CallBack() {

						@Override
						public void callback(int pos) {
							// TODO Auto-generated method stub
							if(pos == 0 && sourceList.size() > 0){
								ToolsUtil.showToast("数据导入成功");
							}else{
								ToolsUtil.showToast("数据导入失败，请查看Excel数据格式是否正确");
							}

							initData();
						}
					});

				}else{
					ToolsUtil.showToast("请选择正确的Excel文件");
				}
				return;  
			}  

			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后  
				String path = getPath(this, uri);  
				//				Toast.makeText(this,path.toString(),Toast.LENGTH_SHORT).show();  
			} else {//4.4一下系统调用方法  
				//				Toast.makeText(MenuActivity.this, getRealPathFromURI(uri)+"222222", Toast.LENGTH_SHORT).show();  
			}  
		}  
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) { // 获取 back键

			exit();
		}

		return false;
	}

	public void exit(){

		new AlertDialog.Builder(this)
		.setTitle("提示")
		.setMessage("确认退出程序？")
		.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				finish();
			}
		}).setNegativeButton("取消", null).show();
	}

	public String getRealPathFromURI(Uri contentUri) {  
		String res = null;  
		String[] proj = { MediaStore.Images.Media.DATA };  
		Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);  
		if(null!=cursor&&cursor.moveToFirst()){;  
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);  
		res = cursor.getString(column_index);  
		cursor.close();  
		}  
		return res;  
	}  

	/**  
	 * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使  
	 */  
	@SuppressLint("NewApi")  
	public String getPath(final Context context, final Uri uri) {  

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;  

		// DocumentProvider  
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {  
			// ExternalStorageProvider  
			if (isExternalStorageDocument(uri)) {  
				final String docId = DocumentsContract.getDocumentId(uri);  
				final String[] split = docId.split(":");  
				final String type = split[0];  

				if ("primary".equalsIgnoreCase(type)) {  
					return Environment.getExternalStorageDirectory() + "/" + split[1];  
				}  
			}  
			// DownloadsProvider  
			else if (isDownloadsDocument(uri)) {  

				final String id = DocumentsContract.getDocumentId(uri);  
				final Uri contentUri = ContentUris.withAppendedId(  
						Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));  

				return getDataColumn(context, contentUri, null, null);  
			}  
			// MediaProvider  
			else if (isMediaDocument(uri)) {  
				final String docId = DocumentsContract.getDocumentId(uri);  
				final String[] split = docId.split(":");  
				final String type = split[0];  

				Uri contentUri = null;  
				if ("image".equals(type)) {  
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;  
				} else if ("video".equals(type)) {  
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;  
				} else if ("audio".equals(type)) {  
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;  
				}  

				final String selection = "_id=?";  
				final String[] selectionArgs = new String[]{split[1]};  

				return getDataColumn(context, contentUri, selection, selectionArgs);  
			}  
		}  
		// MediaStore (and general)  
		else if ("content".equalsIgnoreCase(uri.getScheme())) {  
			return getDataColumn(context, uri, null, null);  
		}  
		// File  
		else if ("file".equalsIgnoreCase(uri.getScheme())) {  
			return uri.getPath();  
		}  
		return null;  
	}  

	/** 
	 * Get the value of the data column for this Uri. This is useful for 
	 * MediaStore Uris, and other file-based ContentProviders. 
	 * 
	 * @param context       The context. 
	 * @param uri           The Uri to query. 
	 * @param selection     (Optional) Filter used in the query. 
	 * @param selectionArgs (Optional) Selection arguments used in the query. 
	 * @return The value of the _data column, which is typically a file path. 
	 */  
	public String getDataColumn(Context context, Uri uri, String selection,  
			String[] selectionArgs) {  

		Cursor cursor = null;  
		final String column = "_data";  
		final String[] projection = {column};  

		try {  
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,  
					null);  
			if (cursor != null && cursor.moveToFirst()) {  
				final int column_index = cursor.getColumnIndexOrThrow(column);  
				return cursor.getString(column_index);  
			}  
		} finally {  
			if (cursor != null)  
				cursor.close();  
		}  
		return null;  
	}  

	/** 
	 * @param uri The Uri to check. 
	 * @return Whether the Uri authority is ExternalStorageProvider. 
	 */  
	public boolean isExternalStorageDocument(Uri uri) {  
		return "com.android.externalstorage.documents".equals(uri.getAuthority());  
	}  

	/** 
	 * @param uri The Uri to check. 
	 * @return Whether the Uri authority is DownloadsProvider. 
	 */  
	public boolean isDownloadsDocument(Uri uri) {  
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());  
	}  

	/** 
	 * @param uri The Uri to check. 
	 * @return Whether the Uri authority is MediaProvider. 
	 */  
	public boolean isMediaDocument(Uri uri) {  
		return "com.android.providers.media.documents".equals(uri.getAuthority());  
	}  
}
