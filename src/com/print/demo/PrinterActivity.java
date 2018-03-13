package com.print.demo;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import com.print.demo.adapter.CommonAdapter;
import com.print.demo.adapter.ViewHolder;
import com.print.demo.data.PrintInfo;
import com.print.demo.util.PrintUtil;
import com.print.demo.util.ToolsUtil;
import com.print.demorego.R;

/** 
 * ɨ�衢��ӡģ��
 * 
 * @author yxx
 *
 * @date 2018-3-12 ����9:15:23
 * 
 */
public class PrinterActivity extends Activity {

	private List<PrintInfo> dataList = new ArrayList<PrintInfo>();
	private CommonAdapter<PrintInfo> commonAdapter;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_printer);

		findViewById();
		ApplicationContext.getEventBus().register(this);
	}

	private void findViewById(){

		listView = (ListView) findViewById(R.id.lv_public);
		commonAdapter = new CommonAdapter<PrintInfo>(this, dataList, R.layout.item_scan) {

			@Override
			public void convert(ViewHolder helper, PrintInfo item) {
				// TODO Auto-generated method stub

				helper.setText(R.id.item_scan_tv1, (commonAdapter.getPosition() + 1) + "");
				helper.setText(R.id.item_scan_tv2, item.getBillcode());
				helper.setText(R.id.item_scan_tv4, item.getTime());
			}
		};

		listView.setAdapter(commonAdapter);
	}

	public void onEventMainThread(Message msg) {

		if(msg.what == ApplicationContext.PDA_SCAN_DATA){

			String billcode = (String) msg.obj;
			printData(billcode);
		}
	}

	/**
	 * ��ӡ����
	 */
	private void printData(String billcode){

		//		PrintInfo info = checkData(billcode);
		//		if(info == null){
		//			ToolsUtil.showToast("δ�ҵ���Ӧ����");
		//			return;
		//		}

		PrintInfo info = new PrintInfo();
		info.setBillcode(billcode);
		info.setTime(ToolsUtil.getTime());

		dataList.add(info);
		commonAdapter.notifyDataSetChanged();

		PrintUtil.printLabel(info);
	}

	/**
	 * ���������ȡ��Ʒ����
	 * @param billcode
	 * @return
	 */
	private PrintInfo checkData(String billcode){

		int len = MenuActivity.sourceList.size();
		for(int i=0; i<len; i++){

			PrintInfo info = MenuActivity.sourceList.get(i);
			if(info.getBillcode().equals(billcode)){
				return info;
			}
		}

		return null;
	}

	public void clearData(View v){

		printData("12345678");

		//		dataList.clear();
		//		commonAdapter.notifyDataSetChanged();
		//		ToolsUtil.showToast("�б�����ɹ�");
	}

	public void back(View v){

		dataList.clear();
		dataList = null;
		finish();
	}
}
