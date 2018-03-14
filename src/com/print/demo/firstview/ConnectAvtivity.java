package com.print.demo.firstview;

import utils.LinkContactActivity;
import com.print.demorego.R;
import java.util.ArrayList;
import com.print.demo.ApplicationContext;
import com.print.demo.PrinterActivity;
import com.print.demo.secondview.PrintModeActivity;
import com.print.demo.util.ToolsUtil;
import com.print.demo.view.CustomProgress;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class ConnectAvtivity extends Activity {

	public int state;
	public String PrintName;
	public Button con;
	public Button btnScan;
	public Spinner type;
	public Spinner usb;
	public Spinner com;
	public Spinner combaud;
	public Spinner porttype;
	public Spinner printway;
	public Spinner btName;
	private ArrayAdapter<String> mAdapter;
	public LinearLayout show;
	public View view1;
	public TextView link;
	public Button linktest;
	public EditText wifiport;
	public EditText wifiip;
	public EditText Adress;
	public ApplicationContext context;
	public String error;
	public boolean mBconnect = false;
	ArrayList<String> getbtName = new ArrayList<String>();
	ArrayList<String> getbtNM = new ArrayList<String>();
	ArrayList<String> getbtMax = new ArrayList<String>();
	int positionName=0;
	int positionPort=0;
	String restoredwifi="192.168.1.1" ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//if (android.os.Build.VERSION.SDK_INT > 9) {  
		//	  StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();  
		//	   StrictMode.setThreadPolicy(policy);  
		//	    }
		setContentView(R.layout.activity_connect);

		// ���ҳ��֮�����ݹ���
		context = (ApplicationContext) getApplicationContext();
		context.setObject();

		linktest = (Button) findViewById(R.id.TextView_linktest);
		link = (TextView) findViewById(R.id.TextView_link);
		type = (Spinner) findViewById(R.id.spinner_type);
		porttype = (Spinner) findViewById(R.id.spinner_porttype);
		printway = (Spinner) findViewById(R.id.spinner_printway);
		show = (LinearLayout) findViewById(R.id.linearLayout0);

		ArrayList<String> printName = new ArrayList<String>();
		printName = (ArrayList<String>) context.getObject()
				.CON_GetSupportPrinters();

		mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, printName);
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		type.setAdapter(mAdapter);

		String[] printInterface = context.getObject().CON_GetSupportPageMode();
		mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, printInterface);
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		printway.setAdapter(mAdapter);

		type.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				PrintName = parent.getItemAtPosition(position).toString();
				positionName=position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		linktest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mBconnect) {
					Intent intent = new Intent(ConnectAvtivity.this,
							PrintModeActivity.class);

					startActivity(intent);
				} else {
					Toast.makeText(ConnectAvtivity.this, R.string.mes_nextpage,
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		porttype.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				positionPort=position;
				switch (position) {
				case 0:// bt
					view1 = getLayoutInflater().inflate(
							R.layout.activity_bluetooth, null); // �°벿�ָı���Layout
					show.removeAllViews();
					show.addView(view1);

					Adress = (EditText) findViewById(R.id.edit_btmax);
					btName = (Spinner) findViewById(R.id.spinner_btname);

					getbtName.clear();
					mAdapter = new ArrayAdapter<String>(ConnectAvtivity.this,
							android.R.layout.simple_spinner_item, getbtName);
					btName.setAdapter(mAdapter);

					getbtNM = (ArrayList<String>) context.getObject()
							.CON_GetWirelessDevices(0);
					// �Ի�õ�������ַ�����ƽ��в���Զ��Ž��в��
					for (int i = 0; i < getbtNM.size(); i++) {
						getbtName.add(getbtNM.get(i).split(",")[0]);
						getbtMax.add(getbtNM.get(i).split(",")[1].substring(0,
								17));
					}

					mAdapter = new ArrayAdapter<String>(ConnectAvtivity.this,
							android.R.layout.simple_spinner_item, getbtName);
					mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					btName.setAdapter(mAdapter);
					btName.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							// TODO Auto-generated method stub
							Adress.setText(getbtMax.get(arg2));
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
						}
					});

					// btName����Ҫ��Ӽ����¼���������
					con = (Button) findViewById(R.id.button_btcon);
					con.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							CustomProgress.showDialog(ConnectAvtivity.this, "������...", true, null);
							connect(Adress.getText().toString());
						}
					});

					btnScan = (Button) findViewById(R.id.button_scan);
					btnScan.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub

							if(mBconnect == false){
								ToolsUtil.showToast("�������Ӵ�ӡ��");
								return;
							}

							Intent intent = new Intent(ConnectAvtivity.this, PrinterActivity.class);
							context.setState(state);
							context.setName(PrintName);
							context.setPrintway(printway.getSelectedItemPosition());
							startActivity(intent);
						}
					});

					break;
				case 1:// wifi
					view1 = getLayoutInflater().inflate(R.layout.activity_wifi,
							null); // �°벿�ָı���Layout
					show.removeAllViews();
					show.addView(view1);
					wifiport = (EditText) findViewById(R.id.edit_wifiport);
					wifiport.setText("9100");
					wifiip = (EditText) findViewById(R.id.edit_wifiip);
					wifiip.setText(restoredwifi);
					con = (Button) findViewById(R.id.button_wificon);
					con.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							connect(wifiip.getText().toString() + ":"
									+ wifiport.getText().toString());
							//+ wifiport.getText().toString()+";435C5E2C6E42594142535E5B");
						}
					});
					break;
				case 2:// usb
					view1 = getLayoutInflater().inflate(R.layout.activity_usb,
							null); // �°벿�ָı���Layout
					show.removeAllViews();
					show.addView(view1);
					con = (Button) findViewById(R.id.button_usbcon);
					usb = (Spinner) findViewById(R.id.spinner_usb);
					con.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							connect("usb");
						}
					});
					break;
				case 3:// com
					view1 = getLayoutInflater().inflate(R.layout.activity_com,
							null); // �°벿�ָı���Layout
					show.removeAllViews();
					show.addView(view1);
					con = (Button) findViewById(R.id.button_comcon);
					com = (Spinner) findViewById(R.id.spinner_com);
					combaud=(Spinner) findViewById(R.id.spinner_Baud);

					con.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// connect(com.getSelectedItem().toString());
							connect(com.getSelectedItem().toString()+":"+combaud.getSelectedItem().toString()+":1:0");//+":1:1"
						}
					});
					break;
				}

			}

			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});

		link.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ConnectAvtivity.this, LinkContactActivity.class);
				startActivity(intent);
			}
		});
		getEnvironmentDirectories();
	}
	private static String LOG_TAG = "LIB_DATA";
	@SuppressLint("NewApi")
	public static void getEnvironmentDirectories() {
		Log.d(LOG_TAG, "getRootDirectory(): "
				+ Environment.getRootDirectory().toString());
		Log.d(LOG_TAG, "getDataDirectory(): "
				+ Environment.getDataDirectory().toString());
		Log.d(LOG_TAG, "getDownloadCacheDirectory(): "
				+ Environment.getDownloadCacheDirectory().toString());
		Log.d(LOG_TAG, "getExternalStorageDirectory(): "
				+ Environment.getExternalStorageDirectory().toString());

		Log.d(
				LOG_TAG,
				"getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES): "
						+ Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString());

		//        LogUtils.i(
		//                LOG_TAG,
		//                "isExternalStorageEmulated(): "
		//                        + Environment.isExternalStorageEmulated());
		//
		//        LogUtils.i(
		//                LOG_TAG,
		//                "isExternalStorageRemovable(): "
		//                        + Environment.isExternalStorageRemovable());

	}
	public void connect(String port) {

		if (mBconnect) {
			context.getObject().CON_CloseDevices(context.getState());

			con.setText(R.string.button_btcon);// "����"
			mBconnect = false;
			CustomProgress.dissDialog();
		} else {

			state = context.getObject().CON_ConnectDevices(PrintName, port, 200);

			if (state > 0) {

				ToolsUtil.showToast("��ӡ�����ӳɹ�");
				mBconnect = true;
				con.setText(R.string.TextView_close);// "�ر�"
				//				Intent intent = new Intent(ConnectAvtivity.this, PrintModeActivity.class);
			} else {
				ToolsUtil.showToast("��ӡ������ʧ��");
				mBconnect = false;
				con.setText(R.string.button_btcon);// "����"
			}
			CustomProgress.dissDialog();
		}
		
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		SharedPreferences prefs = getPreferences(1);
		int restoredText = prefs.getInt("positionName", 0);
		int restoredText1 = prefs.getInt("positionPort", 0);

		porttype.setSelection(restoredText1, true);
		type.setSelection(restoredText, true);
		if(restoredText1==1){
			restoredwifi = prefs.getString("wifi", "192.168.1.1");
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		SharedPreferences.Editor editor = getPreferences(1).edit();
		editor.putInt("positionName", positionName);
		editor.putInt("positionPort", positionPort);
		if(positionPort==1){
			editor.putString("wifi", wifiip.getText().toString());
		}
		editor.commit();
	}

	public void back(View v){

		finish();
	}

}
