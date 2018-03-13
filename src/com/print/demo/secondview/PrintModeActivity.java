package com.print.demo.secondview;

import java.util.ArrayList;

import com.print.demo.ApplicationContext;
import com.print.demo.printview.GraphicTabsActivity;
import com.print.demo.printview.PhotoActivity;
import com.print.demo.printview.TextTabsActivity;
import com.print.demorego.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import utils.preDefiniation.BarcodeType;
import utils.preDefiniation.TransferMode;

public class PrintModeActivity extends Activity {
	public Button text;
	public Button pic;
	public Bundle b;
	public Button mLabel;
	public Button buzzer;
//	public Button picture;
	public EditText qrsize;
	public EditText qrtext;
	public boolean buzz = true;
	private ApplicationContext context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_printmode);
		context = (ApplicationContext) getApplicationContext();
		text = (Button) findViewById(R.id.button_text);
		pic = (Button) findViewById(R.id.button_pic);
		mLabel = (Button) findViewById(R.id.button_label);
		buzzer = (Button) findViewById(R.id.button_buzzer);
		//picture = (Button) findViewById(R.id.button_picture);
		if (context.getName().equalsIgnoreCase("RG-MLP80A")) {
			text.setEnabled(false);
			// pic.setEnabled(false);
		} else {
			text.setEnabled(true);
		}
//		picture.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(PrintModeActivity.this, PhotoActivity.class);
//				startActivity(intent);
//			}
//		});
		
		buzzer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				context.getObject().CON_SetBuzzer(context.getState(), buzz);
				if (buzz) {
					buzzer.setText(getResources().getString(R.string.button_buzzer) + " open");
				} else {
					buzzer.setText(getResources().getString(R.string.button_buzzer) + " close");
				}
				buzz = !buzz;
			}
		});
		text.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PrintModeActivity.this, TextTabsActivity.class);
				startActivity(intent);
			}
		});
		pic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PrintModeActivity.this, GraphicTabsActivity.class);
				// 图形是true
				startActivity(intent);
			}
		});
		mLabel.setOnClickListener(new OnClickListener() {

			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (context.getName().equalsIgnoreCase("RG-MTP80B")) {
					context.getObject().CON_PageStart(context.getState(), true, 576, 248);// 宽高像素，像素=纸宽*8
																							// 248
																							// 164
					context.getObject().DRAW_PrintPicture(context.getState(),
							context.getResources().openRawResource(R.drawable.test), 0, 0, 160, 160);
					context.getObject().DRAW_PrintText(context.getState(), 240, 10, "仓库:竹刺花信号车间", 24);
					context.getObject().DRAW_PrintText(context.getState(), 240, 40, "报日胡洁柔材料库", 24);
					context.getObject().DRAW_PrintText(context.getState(), 240, 80, "状态：新品 数量：0", 24);
					context.getObject().DRAW_PrintText(context.getState(), 240, 240, "1111111111111", 24);// 120
																											// 230meiwenti
					context.getObject().CON_PageEnd(context.getState(), context.getPrintway());
					context.getObject().ASCII_PrintBuffer(context.getState(), new byte[] { 0x0c }, 1);
				} else if (context.getName().contains("LP")) {
					context.getObject().CPCL_PageStart(context.getState(), 576, 248, 0, 1);// 384,350
					context.getObject().CPCL_Print2DBarcode(context.getState(), 2, 5, 10, 2, 2, 6, "MA,姓名，12345678910",
							"gb2312");
					context.getObject().CPCL_PrintString(context.getState(), 240, 40, 0, 0, 0, 24, "仓库:竹刺花信号车间",
							"gb2312");
					context.getObject().CPCL_PrintString(context.getState(), 240, 80, 0, 0, 0, 24, "报日胡洁柔材料库",
							"gb2312");
					context.getObject().CPCL_PrintString(context.getState(), 240, 120, 0, 0, 0, 24, "状态：新品 数量：0",
							"gb2312");
					context.getObject().CPCL_PrintString(context.getState(), 240, 160, 0, 0, 0, 24, "仓库:竹刺花信号车间",
							"gb2312");
					context.getObject().CON_PageEnd(context.getState(), context.getPrintway());
				} else if (context.getName().equalsIgnoreCase("RG-GK888")) {
					zeb();
				} else if (context.getName().equalsIgnoreCase("TTP-244Pro")) {
					TSC();
				} else {
					Toast.makeText(PrintModeActivity.this, "no Label", Toast.LENGTH_LONG).show();
				}
			}

			private void TSC() {
				// TODO Auto-generated method stub
				// 标签的高度 宽度 单位是dot 8dot=1mm
				context.getObject().CON_PageStart(context.getState(), false, 320, 320);

				// 打印文本 旋转角度不旋转就“” 旋转角度参数为（90 180 270） 字体（1-2
				// 4-7，GBUNSG16/24。CPF，CTUNMK24。CPF） 大小（0：1-6 2/7：0-1 4：0-7
				// 5：0-3 ） x位置 y位置 内容
				ArrayList<String> param = new ArrayList<String>();
				param.add("");// 旋转角度 “”为不旋转，90，180 ，270 为旋转的角度参数
				param.add("24");// 24比GBUNSG16字体要大，打印中文
				param.add("0");// 字大小
				param.add("100");// x轴起始位置
				param.add("10");// y轴起始位置
				param.add("Invoice");// 文本内容
				context.getObject().ASCII_PrintString(context.getState(), param, "gb2312");
				param.clear();
				param.add("");
				param.add("24");// 24比16字体要大
				param.add("0");
				param.add("10");
				param.add("40");
				param.add("2015-7-1- 9:00");
				context.getObject().ASCII_PrintString(context.getState(), param, "gb2312");
				param.clear();
				param.add("");// 水平打印“”，竖直是V
				param.add("280");// 水平x轴起始位置
				param.add("120");// 竖直y轴起始位置
				param.add("2");// 版本模式号参数是1-2
				param.add("3");// 模块单元的宽度高度参数1-32，默认6
				param.add("MA,123445661220");// 二维码内容
				context.getObject().ASCII_Print2DBarcode(context.getState(), BarcodeType.BT_QRcode.getValue(), param,
						"gb2312");

				// 矩形 ：起始位置x0 y0，终止位置x1 y1 宽度
				param.clear();
				param.add("10");// x0
				param.add("60");// y0
				param.add("300");// x1
				param.add("110");// y1
				param.add("1");// 线条宽度
				context.getObject().ASCII_PrintRectangle(context.getState(), param);

				param.clear();
				param.add("");
				param.add("16");// 24比16字体要大
				param.add("0");
				param.add("15");
				param.add("65");
				param.add("Name: Ruigong printer");
				context.getObject().ASCII_PrintString(context.getState(), param, "gb2312");

				// 打印一维条码 正常水平打印参数“” 垂直打印 ：V 条码类型 UPCA,UPCE,EAN13
				// ,EAN8,39,93,128,CODABAR 条码宽度，倍率，条码高度， X,Y位置，条码内容
				param.clear();
				param.add("");
				param.add("128");// 条码类型
				param.add("2");// 宽度
				param.add("0");// 倍率
				param.add("40");// 条码高度
				param.add("0");// X
				param.add("120");// Y位置
				param.add("123445661220");// 条码内容
				context.getObject().ASCII_Print1DBarcode(context.getState(), param, "gb2312");
				context.getObject().CON_PageEnd(context.getState(), context.getPrintway());
			}

			private void zeb() {
				// TODO Auto-generated method stub
				context.getObject().CON_PageStart(context.getState(), false, 480, 320);

				// context.getObject().CON_PageStart(context.getState(), true,
				// 550, 550);//250,80
				// context.getObject().DRAW_PrintPicture(context.getState(),
				// context.getResources().openRawResource(R.drawable.ems), 0, 0,
				// 250, 80);

				// 第一行
				ArrayList<String> param = new ArrayList<String>();
				param.add("28");
				param.add("30");
				param.add("0");
				param.add("8");
				param.add("1");
				param.add("1");
				param.add("N");
				param.add("10-19 08:27");
				context.getObject().ASCII_PrintString(context.getState(), param, "gb2312");

				param.clear();
				param.add("20");
				param.add("60");
				param.add("190");
				param.add("2");
				context.getObject().ASCII_PrintLine(context.getState(), param);
				// 第2行
				param.clear();
				param.add("20");
				param.add("110");
				param.add("190");
				param.add("2");
				context.getObject().ASCII_PrintLine(context.getState(), param);

				param.clear();
				param.add("30");
				param.add("114");
				param.add("0");
				param.add("8");
				param.add("1");
				param.add("1");
				param.add("N");
				param.add("东街揽投部");
				context.getObject().ASCII_PrintString(context.getState(), param, "gb2312");
				param.clear();
				param.add("198");
				param.add("122");
				param.add("0");
				param.add("8");
				param.add("1");
				param.add("1");
				param.add("N");
				param.add("局收 号码");
				context.getObject().ASCII_PrintString(context.getState(), param, "gb2312");

				param.clear();
				param.add("30");
				param.add("160");
				param.add("0");
				param.add("8");
				param.add("1");
				param.add("1");
				param.add("N");
				param.add("福州55件");
				context.getObject().ASCII_PrintString(context.getState(), param, "gb2312");
				param.clear();
				param.add("198");
				param.add("162");
				param.add("0");
				param.add("8");
				param.add("1");
				param.add("1");
				param.add("N");
				param.add("局发 重量");
				context.getObject().ASCII_PrintString(context.getState(), param, "gb2312");

				param.clear();
				param.add("20");
				param.add("150");
				param.add("190");
				param.add("2");
				context.getObject().ASCII_PrintLine(context.getState(), param);
				param.clear();
				param.add("20");
				param.add("190");
				param.add("190");
				param.add("2");
				context.getObject().ASCII_PrintLine(context.getState(), param);

				param.clear();
				param.add("260");
				param.add("8");
				param.add("0");
				param.add("8");
				param.add("1");
				param.add("2");
				param.add("N");
				param.add("邮特2014");
				context.getObject().ASCII_PrintString(context.getState(), param, "gb2312");

				param.clear();
				param.add("303");
				param.add("57");
				param.add("0");
				param.add("8");
				param.add("2");
				param.add("2");
				param.add("N");
				param.add("特快");
				context.getObject().ASCII_PrintString(context.getState(), param, "gb2312");

				param.clear();
				param.add("347");
				param.add("118");
				param.add("0");
				param.add("8");
				param.add("1");
				param.add("1");
				param.add("N");
				param.add("AAAA");
				context.getObject().ASCII_PrintString(context.getState(), param, "gb2312");

				param.clear();
				param.add("345");
				param.add("150");
				param.add("120");
				param.add("2");
				context.getObject().ASCII_PrintLine(context.getState(), param);

				param.clear();
				param.add("347");
				param.add("160");
				param.add("0");
				param.add("8");
				param.add("1");
				param.add("1");
				param.add("N");
				param.add("2.00");
				context.getObject().ASCII_PrintString(context.getState(), param, "gb2312");

				param.clear();
				param.add("345");
				param.add("190");
				param.add("120");
				param.add("2");
				context.getObject().ASCII_PrintLine(context.getState(), param);

				param.clear();
				param.add("20");
				param.add("205");
				param.add("0");
				param.add("1");
				param.add("2");
				param.add("9");
				param.add("70");
				param.add("B");
				param.add("10803-100-2-121-AA");
				context.getObject().ASCII_Print1DBarcode(context.getState(), param, "gb2312");
				param.clear();
				param.add("303");
				param.add("57");
				param.add("s4");
				param.add("123456");
				context.getObject().ASCII_Print2DBarcode(context.getState(), BarcodeType.BT_QRcode.getValue(), param,
						"gb2312");

				// 打印一维条码 条码x,y,旋转, 条码窄度，条码宽度，条码高度， 可显示B/N，条码内容
				context.getObject().CON_PageEnd(context.getState(), TransferMode.TM_NONE.getValue());
			}
		});
	}
}
