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
 * ������
 * 
 * @author yxx123
 *
 * @date 2018-3-12 ����7:08:07
 * 
 */
public class MenuActivity extends Activity {

	private TextView tvVersion;
	private TextView tvCount;
	public static List<PrintInfo> sourceList = new ArrayList<PrintInfo>();//����SD��Excel�����������
	
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

		//����ʧЧʱ�䣬�ڹ涨ʱ���֮ǰ����ʹ��
		String limitTime = getResources().getString(R.string.limitTime);
		if(limitTime.compareTo(ToolsUtil.getTime()) < 0){
//			finish();
		}
	}

	private void initData(){

		new ScanDataDao().selectAllData(sourceList);
		tvCount.setText(sourceList.size() + "��");
	}

	/**
	 * ��������
	 * @param v
	 */
	public void bluetoothSetting(View v){

		Intent intent = new Intent(MenuActivity.this, ConnectAvtivity.class);
		startActivity(intent);
	}
	
	/**
	 * ��ʼɨ��
	 * @param v
	 */
	public void startScan(View v){
		
//		if(mBconnect == false){
//			ToolsUtil.showToast("�������Ӵ�ӡ��");
//			return;
//		}
		
		Intent intent = new Intent(MenuActivity.this, PrinterActivity.class);
		startActivity(intent);
	}

	/**
	 * ��������
	 * @param v
	 */
	public void inputExcel(View v){

		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");//�������ͣ����������������ͣ������׺�Ŀ�������д��
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(intent,1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == Activity.RESULT_OK) {  
			Uri uri = data.getData();  
			if ("file".equalsIgnoreCase(uri.getScheme())){//ʹ�õ�����Ӧ�ô�  

				if(uri.getPath().endsWith(".xls") || uri.getPath().endsWith(".xlsx")){

					new ExcelUtil().readExcel(uri.getPath(), sourceList, new CallBack() {

						@Override
						public void callback(int pos) {
							// TODO Auto-generated method stub
							if(pos == 0 && sourceList.size() > 0){
								ToolsUtil.showToast("���ݵ���ɹ�");
							}else{
								ToolsUtil.showToast("���ݵ���ʧ�ܣ���鿴Excel���ݸ�ʽ�Ƿ���ȷ");
							}

							initData();
						}
					});

				}else{
					ToolsUtil.showToast("��ѡ����ȷ��Excel�ļ�");
				}
				return;  
			}  

			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4�Ժ�  
				String path = getPath(this, uri);  
				//				Toast.makeText(this,path.toString(),Toast.LENGTH_SHORT).show();  
			} else {//4.4һ��ϵͳ���÷���  
				//				Toast.makeText(MenuActivity.this, getRealPathFromURI(uri)+"222222", Toast.LENGTH_SHORT).show();  
			}  
		}  
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) { // ��ȡ back��

			exit();
		}

		return false;
	}

	public void exit(){

		new AlertDialog.Builder(this)
		.setTitle("��ʾ")
		.setMessage("ȷ���˳�����")
		.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				finish();
			}
		}).setNegativeButton("ȡ��", null).show();
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
	 * רΪAndroid4.4��ƵĴ�Uri��ȡ�ļ�����·������ǰ�ķ����Ѳ���ʹ  
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
