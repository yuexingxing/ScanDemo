package com.print.demo;

import com.print.demo.db.helper.DBHelper;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import de.greenrobot.event.EventBus;
import demo.printlib.export.demoPrinter;
import utils.preDefiniation;
import utils.preDefiniation.TransferMode;

public class ApplicationContext extends Application {

	public static ApplicationContext instance;

	private demoPrinter printer;
	private int myState = 0;
	private String printName="RG-MTP58B";

	private TransferMode printmode = TransferMode.TM_NONE;
	private boolean labelmark = true;

	public static final String SCN_CUST_ACTION_SCODE = "com.android.server.scannerservice.broadcast";
	public static final String SCN_CUST_EX_SCODE = "scannerdata";

	private static EventBus eventBus;
	public static final int PDA_SCAN_DATA = 0x9999;

	public void onCreate() {
		super.onCreate();

//		new DBHelper(this);
//		DBHelper.SQLiteDBHelper.getWritableDatabase();
		instance = ApplicationContext.this;
		if (eventBus == null) {
			eventBus = EventBus.getDefault();
			eventBus.register(this);
		}

		// Register receiver
		IntentFilter intentFilter = new IntentFilter(SCN_CUST_ACTION_SCODE);
		registerReceiver(mSamDataReceiver, intentFilter);
	}

	public static EventBus getEventBus() {

		if (eventBus == null) {
			eventBus.register(ApplicationContext.getInstance());
		}

		return eventBus;
	}

	// 不能删，否则扫描有问题
	public void onEventMainThread(Message msg) {

	}

	private BroadcastReceiver mSamDataReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			if (intent.getAction().equals(SCN_CUST_ACTION_SCODE)) {

				String billcode;
				try {
					billcode = intent.getStringExtra(SCN_CUST_EX_SCODE).toString();

					Message message = new Message();
					message.what = PDA_SCAN_DATA;
					message.obj = billcode;
					eventBus.post(message);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					Log.e("in", e.toString());
				}
			}
		}
	};

	/**
	 * 获取单例
	 * 
	 * @return
	 */
	public static ApplicationContext getInstance() {

		return instance;
	}

	public demoPrinter getObject() {
		return printer;
	}

	public void setObject() {
		printer = new demoPrinter(this);
	}

	public String getName() {
		return printName;
	}

	public void setName(String name) {
		printName = name;
	}
	public void setState(int state) {
		myState = state;
	}

	public int getState() {
		return myState;
	}

	public void setPrintway(int printway) {

		switch (printway) {
		case 0:
			printmode = TransferMode.TM_NONE;
			break;
		case 1:
			printmode = TransferMode.TM_DT_V1;
			break;
		default:
			printmode = TransferMode.TM_DT_V2;
			break;
		}

	}

	public int getPrintway() {
		return printmode.getValue();
	}

	public boolean getlabel() {
		return labelmark;
	}

	public void setlabel(boolean labelprint) {
		labelmark = labelprint;
	}

}
