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
				// ͼ����true
				startActivity(intent);
			}
		});
		mLabel.setOnClickListener(new OnClickListener() {

			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (context.getName().equalsIgnoreCase("RG-MTP80B")) {
					context.getObject().CON_PageStart(context.getState(), true, 576, 248);// ������أ�����=ֽ��*8
																							// 248
																							// 164
					context.getObject().DRAW_PrintPicture(context.getState(),
							context.getResources().openRawResource(R.drawable.test), 0, 0, 160, 160);
					context.getObject().DRAW_PrintText(context.getState(), 240, 10, "�ֿ�:��̻��źų���", 24);
					context.getObject().DRAW_PrintText(context.getState(), 240, 40, "���պ�������Ͽ�", 24);
					context.getObject().DRAW_PrintText(context.getState(), 240, 80, "״̬����Ʒ ������0", 24);
					context.getObject().DRAW_PrintText(context.getState(), 240, 240, "1111111111111", 24);// 120
																											// 230meiwenti
					context.getObject().CON_PageEnd(context.getState(), context.getPrintway());
					context.getObject().ASCII_PrintBuffer(context.getState(), new byte[] { 0x0c }, 1);
				} else if (context.getName().contains("LP")) {
					context.getObject().CPCL_PageStart(context.getState(), 576, 248, 0, 1);// 384,350
					context.getObject().CPCL_Print2DBarcode(context.getState(), 2, 5, 10, 2, 2, 6, "MA,������12345678910",
							"gb2312");
					context.getObject().CPCL_PrintString(context.getState(), 240, 40, 0, 0, 0, 24, "�ֿ�:��̻��źų���",
							"gb2312");
					context.getObject().CPCL_PrintString(context.getState(), 240, 80, 0, 0, 0, 24, "���պ�������Ͽ�",
							"gb2312");
					context.getObject().CPCL_PrintString(context.getState(), 240, 120, 0, 0, 0, 24, "״̬����Ʒ ������0",
							"gb2312");
					context.getObject().CPCL_PrintString(context.getState(), 240, 160, 0, 0, 0, 24, "�ֿ�:��̻��źų���",
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
				// ��ǩ�ĸ߶� ��� ��λ��dot 8dot=1mm
				context.getObject().CON_PageStart(context.getState(), false, 320, 320);

				// ��ӡ�ı� ��ת�ǶȲ���ת�͡��� ��ת�ǶȲ���Ϊ��90 180 270�� ���壨1-2
				// 4-7��GBUNSG16/24��CPF��CTUNMK24��CPF�� ��С��0��1-6 2/7��0-1 4��0-7
				// 5��0-3 �� xλ�� yλ�� ����
				ArrayList<String> param = new ArrayList<String>();
				param.add("");// ��ת�Ƕ� ����Ϊ����ת��90��180 ��270 Ϊ��ת�ĽǶȲ���
				param.add("24");// 24��GBUNSG16����Ҫ�󣬴�ӡ����
				param.add("0");// �ִ�С
				param.add("100");// x����ʼλ��
				param.add("10");// y����ʼλ��
				param.add("Invoice");// �ı�����
				context.getObject().ASCII_PrintString(context.getState(), param, "gb2312");
				param.clear();
				param.add("");
				param.add("24");// 24��16����Ҫ��
				param.add("0");
				param.add("10");
				param.add("40");
				param.add("2015-7-1- 9:00");
				context.getObject().ASCII_PrintString(context.getState(), param, "gb2312");
				param.clear();
				param.add("");// ˮƽ��ӡ��������ֱ��V
				param.add("280");// ˮƽx����ʼλ��
				param.add("120");// ��ֱy����ʼλ��
				param.add("2");// �汾ģʽ�Ų�����1-2
				param.add("3");// ģ�鵥Ԫ�Ŀ�ȸ߶Ȳ���1-32��Ĭ��6
				param.add("MA,123445661220");// ��ά������
				context.getObject().ASCII_Print2DBarcode(context.getState(), BarcodeType.BT_QRcode.getValue(), param,
						"gb2312");

				// ���� ����ʼλ��x0 y0����ֹλ��x1 y1 ���
				param.clear();
				param.add("10");// x0
				param.add("60");// y0
				param.add("300");// x1
				param.add("110");// y1
				param.add("1");// �������
				context.getObject().ASCII_PrintRectangle(context.getState(), param);

				param.clear();
				param.add("");
				param.add("16");// 24��16����Ҫ��
				param.add("0");
				param.add("15");
				param.add("65");
				param.add("Name: Ruigong printer");
				context.getObject().ASCII_PrintString(context.getState(), param, "gb2312");

				// ��ӡһά���� ����ˮƽ��ӡ�������� ��ֱ��ӡ ��V �������� UPCA,UPCE,EAN13
				// ,EAN8,39,93,128,CODABAR �����ȣ����ʣ�����߶ȣ� X,Yλ�ã���������
				param.clear();
				param.add("");
				param.add("128");// ��������
				param.add("2");// ���
				param.add("0");// ����
				param.add("40");// ����߶�
				param.add("0");// X
				param.add("120");// Yλ��
				param.add("123445661220");// ��������
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

				// ��һ��
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
				// ��2��
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
				param.add("������Ͷ��");
				context.getObject().ASCII_PrintString(context.getState(), param, "gb2312");
				param.clear();
				param.add("198");
				param.add("122");
				param.add("0");
				param.add("8");
				param.add("1");
				param.add("1");
				param.add("N");
				param.add("���� ����");
				context.getObject().ASCII_PrintString(context.getState(), param, "gb2312");

				param.clear();
				param.add("30");
				param.add("160");
				param.add("0");
				param.add("8");
				param.add("1");
				param.add("1");
				param.add("N");
				param.add("����55��");
				context.getObject().ASCII_PrintString(context.getState(), param, "gb2312");
				param.clear();
				param.add("198");
				param.add("162");
				param.add("0");
				param.add("8");
				param.add("1");
				param.add("1");
				param.add("N");
				param.add("�ַ� ����");
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
				param.add("����2014");
				context.getObject().ASCII_PrintString(context.getState(), param, "gb2312");

				param.clear();
				param.add("303");
				param.add("57");
				param.add("0");
				param.add("8");
				param.add("2");
				param.add("2");
				param.add("N");
				param.add("�ؿ�");
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

				// ��ӡһά���� ����x,y,��ת, ����խ�ȣ������ȣ�����߶ȣ� ����ʾB/N����������
				context.getObject().CON_PageEnd(context.getState(), TransferMode.TM_NONE.getValue());
			}
		});
	}
}
