package com.print.demo.printview;

import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.print.demo.ApplicationContext;
import com.print.demorego.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class TextFirstActivity extends Activity {
	public AutoCompleteTextView text;
	public Button PrintText;
	public Button PrinterState;
	public Button Printercut;

	// 文字设置
	public CheckBox dwight;
	public CheckBox dhight;
	public CheckBox dthick;
	public CheckBox underline;
	public CheckBox small;
	public CheckBox OppositeColor;
	// 标签设置
	public CheckBox label;
	public EditText wight;
	public EditText hight;
	public EditText X0;
	public EditText Y0;
	// 对齐方式
	public RadioGroup align;
	public RadioButton left;
	public RadioButton center;
	public RadioButton right;
	public int state = 1;
	public ApplicationContext context;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_general);
		// 标签设置
		wight = (EditText) findViewById(R.id.editText01);
		wight.setText("20");
		/*
		 * hight = (EditText) findViewById(R.id.editText02);
		 * hight.setText("40");
		 */
		// 文字设置
		dwight = (CheckBox) findViewById(R.id.checkBox01);
		dhight = (CheckBox) findViewById(R.id.checkBox02);
		dthick = (CheckBox) findViewById(R.id.checkBox03);
		underline = (CheckBox) findViewById(R.id.checkBox04);
		small = (CheckBox) findViewById(R.id.checkBox05);
		OppositeColor = (CheckBox) findViewById(R.id.checkBox07);
		Printercut = (Button) findViewById(R.id.button_cut);
		// 对齐方式
		align = (RadioGroup) findViewById(R.id.radioGroup1);
		left = (RadioButton) findViewById(R.id.radio0);
		center = (RadioButton) findViewById(R.id.radio1);
		right = (RadioButton) findViewById(R.id.radio2);
		text = (AutoCompleteTextView) findViewById(R.id.autoCompleteText_text);
		PrintText = (Button) findViewById(R.id.button_printtext);
		PrinterState = (Button) findViewById(R.id.Button_state);
		label = (CheckBox) findViewById(R.id.checkBox06);
		context = (ApplicationContext) getApplicationContext();
		label.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					@Override
					public void run() {
						while (label.isChecked()) {
							try {
								Thread.sleep(Integer.parseInt(wight.getText()
										.toString()));
								printText();
								context.getObject().CON_PageEnd(
										context.getState(),
										context.getPrintway());
							} catch (InterruptedException e) {
								// e.printStackTrace();
								Toast.makeText(TextFirstActivity.this,
										R.string.mes_stateNet,
										Toast.LENGTH_SHORT).show();
							}
						}
					}
				}).start();
			}
		});
		PrinterState.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(context.getName().equalsIgnoreCase("DS-1920")){  
					//1:序列号 2：打印机状态 3：色带灰度检测 4：断针检测 6：设置断针补偿
				state =context.getObject().CON_QueryStatus2(context.getState(),2);
				if (state == 0) {
					Toast.makeText(TextFirstActivity.this,
							R.string.mes_stateOK, Toast.LENGTH_SHORT).show();
				} else if (state == 2) {//缺纸
					Toast.makeText(TextFirstActivity.this,
							R.string.mes_statePE, Toast.LENGTH_SHORT).show();
				} else if (state == 3) {//
					Toast.makeText(TextFirstActivity.this,
							R.string.mes_stateOffline, Toast.LENGTH_SHORT).show();
				} else if (state == 5) {
					Toast.makeText(TextFirstActivity.this,
							R.string.mes_stateReset, Toast.LENGTH_SHORT).show();
				} else if (state == 6) {
					Toast.makeText(TextFirstActivity.this,
							R.string.mes_statePaperjam, Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(TextFirstActivity.this,
							R.string.mes_stateNet, Toast.LENGTH_SHORT).show();
				} 
				
				/*			state = context.getObject().CON_QueryStatus(context.getState(),4);
							Log.d("read",state+"-----state");
							else {
								Toast.makeText(TextFirstActivity.this,
										R.string.mes_stateNet, Toast.LENGTH_SHORT).show();
							}
							Toast.makeText(TextFirstActivity.this,
									state+"", Toast.LENGTH_SHORT).show();*/
						//	context.getObject().CON_PageEnd(context.getState(),
						//			context.getPrintway());
				
				
				
				}else{
				state = context.getObject().CON_QueryStatus(context.getState());
				if (state == 0) {
					Toast.makeText(TextFirstActivity.this,
							R.string.mes_stateOK, Toast.LENGTH_SHORT).show();
				} else if (state == 3) {
					Toast.makeText(TextFirstActivity.this,
							R.string.mes_statePE, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(TextFirstActivity.this,
							R.string.mes_stateNet, Toast.LENGTH_SHORT).show();
				}
			//	Toast.makeText(TextFirstActivity.this,
			//			state+"", Toast.LENGTH_SHORT).show();
			}
			}
		});
		PrintText.setOnClickListener(new OnClickListener() {
			int len = 0;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				printText();
				context.getObject().ASCII_CtrlFeedLines(context.getState(), 1);
				context.getObject().ASCII_CtrlPrintCRLF(context.getState(), 1);
				context.getObject().CON_PageEnd(context.getState(),
						context.getPrintway());
				


			}
		});
		Printercut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				context.getObject().CON_PageStart(context.getState(), false, 0,
						0);
				context.getObject()
						.ASCII_CtrlCutPaper(context.getState(), 0, 0);
				context.getObject().CON_PageEnd(context.getState(),
						context.getPrintway());
			}
		});
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		SharedPreferences prefs = getPreferences(0);
		String restoredText = prefs.getString("text", null);
		if (restoredText != null) {
			text.setText(restoredText, TextView.BufferType.EDITABLE);
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		SharedPreferences.Editor editor = getPreferences(0).edit();
		editor.putString("text", text.getText().toString());
		editor.commit();
	}

	private void printText() {
		context.getObject().CON_PageStart(context.getState(), false, 0, 0);
		int alignType = 0;
		// 对齐方式
		if (center.isChecked()) {
			alignType = 1;
		}
		if (right.isChecked()) {
			alignType = 2;
		}
		context.getObject().ASCII_CtrlOppositeColor(context.getState(),
				OppositeColor.isChecked());
		context.getObject().ASCII_CtrlAlignType(context.getState(), alignType);
		context.getObject().ASCII_PrintString(context.getState(),
				dwight.isChecked() ? 1 : 0, dhight.isChecked() ? 1 : 0,
				dthick.isChecked() ? 1 : 0, underline.isChecked() ? 1 : 0,
				small.isChecked() ? 1 : 0, text.getText().toString(), "gb2312");
		//context.getObject().ASCII_PrintString(context.getState(),text.getText().toString(), "gb2312");
		/*
		 * context.getObject().ASCII_CtrlFeedLines(context.getState(), 1);
		 * context.getObject().ASCII_CtrlPrintCRLF(context.getState(), 1);
		 * 
		 * context.getObject().CON_PageEnd(context.getState(),
		 * context.getPrintway());
		 */
	}
}