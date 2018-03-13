package com.print.demo.printview;

import java.util.ArrayList;
import java.util.Calendar;

import com.print.demo.ApplicationContext;
import com.print.demo.firstview.ConnectAvtivity;
import com.print.demo.secondview.PrintModeActivity;
import com.print.demorego.R;
import utils.preDefiniation.AlignType;
import utils.preDefiniation.BarcodeType;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
public class TextThirdActivity extends Activity {
	public Button fire;
	public Button move;
	public Button shopping;
	public ApplicationContext context;
	public Button customer;
	public Button print91;
	public RadioGroup radiogrop;
	int a=0;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_textapply);
		//fire = (Button) findViewById(R.id.Button_fire);
		 radiogrop = (RadioGroup) findViewById(R.id.radioGroup1);
		move = (Button) findViewById(R.id.button_movie);
		shopping = (Button) findViewById(R.id.Button_shopping);
		print91= (Button) findViewById(R.id.Button_prit91);
		//customer= (Button) findViewById(R.id.Button_customer);
		move.setEnabled(true);
		shopping.setEnabled(false);
		print91.setEnabled(true);
		//radiogrop 监听事件
		radiogrop.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.radioButton1:
					move.setEnabled(true);
					print91.setEnabled(true);
					shopping.setEnabled(false);
					break;
				default:
					move.setEnabled(false);
					print91.setEnabled(false);
					shopping.setEnabled(true);
					break;
				}
			}
		});
		
		context = (ApplicationContext) getApplicationContext();
		
		move.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 context.getObject().CON_PageStart(context.getState(),false,0,0);
				 context.getObject().ASCII_CtrlAlignType(context.getState(),
				 AlignType.AT_CENTER.getValue());
				 context.getObject().ASCII_PrintString(context.getState(),
				 1, 1, 1, 0, 0, getResources().getString(R.string.text_move), "gb2312");//"瑞工国际影城"
				 //context.getObject().ASCII_CtrlFeedLines(context.getState(),1);
				 context.getObject().ASCII_CtrlReset(context.getState());
				 context.getObject().ASCII_PrintString(context.getState(),0,
				 0, 1, 0, 0, getResources().getString(R.string.text_move1), "gb2312");
				// context.getObject().ASCII_CtrlAlignType(context.getState(),
				//		 AlignType.AT_CENTER.getValue());
				 context.getObject().ASCII_PrintString(context.getState(),0,
				 0, 0, 0, 0, getResources().getString(R.string.text_move2),
				 "gb2312");
				 context.getObject().ASCII_CtrlReset(context.getState());
				 context.getObject().ASCII_PrintString(context.getState(),0,
				 0, 1, 0, 0, getResources().getString(R.string.text_move3), "gb2312");
				 context.getObject().ASCII_PrintString(context.getState(),0,
				 0, 0, 0, 0,getResources().getString(R.string.text_move4), "gb2312");
				 context.getObject().ASCII_CtrlReset(context.getState());
				 context.getObject().ASCII_PrintString(context.getState(),0,
				 0, 1, 0, 0, getResources().getString(R.string.text_move5), "gb2312");
				 context.getObject().ASCII_Print1DBarcode(context.getState(),
				 BarcodeType.BT_UPCA.getValue(), 2, 28,
				 utils.preDefiniation.Barcode1DHRI.BH_BLEW.getValue(), "123456789012");
				 context.getObject().ASCII_CtrlFeedLines(context.getState(),1);
				 context.getObject().ASCII_CtrlReset(context.getState());
				 context.getObject().CON_PageEnd(context.getState(),context.getPrintway());
			//打印完一单数据测试
				  
				/* int state;
				 if(a!=1){
		 			a=	context.getObject().CON_QueryPrintStatus(context.getState(),1000,false);//1000000
		 			state=	context.getObject().CON_QueryStatus(context.getState());//1000000
					Log.d("aaa", "=========================a:"+a);	
					Log.d("aaa", "=========================state:"+state);	
			 		}
		 		int i=0;
				while(a==1){
					a=	context.getObject().CON_QueryPrintStatus(context.getState(),1000,true);
					i++;
					state=	context.getObject().CON_QueryStatus(context.getState());//1000000
					Log.d("aaa", "=========================a:"+a);	
					Log.d("aaa", "=========================state:"+state);	
					if(i>50){//a=0;
					break;}
					}	
				Toast.makeText(TextThirdActivity.this, a+"",
							Toast.LENGTH_SHORT).show();*/
			}
		});
		shopping.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int price=0;
				int sum=0;
				int size=1;
				//String monetary="Taka";
				if(getResources().getConfiguration().locale.getCountry().equals("CN")){
					size=2;
				}else{
					size=1;
				}
				String monetary="";
					Calendar c = Calendar.getInstance();  
					 context.getObject().CON_PageStart(context.getState(),false,0,0);
					 context.getObject().ASCII_PrintString(context.getState(),//收银员名称
							 0, 0, 0, 0, 0, getResources().getString(R.string.text_customer)+ c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH)+"\r\n", "gb2312");
					// context.getObject().ASCII_PrintString(context.getState(),
					//		 0, 0, 0, 0, 0,getResources().getString(R.string.text_shop1), "gb2312");
					 int a1=(getResources().getString(R.string.text_name).length()+6/size)*size;
					 int a2=a1+((getResources().getString(R.string.text_quant)).length()+2)*size;
					 int a3=a2+((getResources().getString(R.string.text_unit)).length()+2)*size;
					 byte[] tabCtrl={(byte) a1,(byte) a2,(byte) a3};//(byte) ("NameQuant".length()+4),(byte) ("NameQuantUnit".length()+6)};
					 
					 ArrayList<String> strs = new ArrayList<String>();
						strs.add(getResources().getString(R.string.text_name));
						strs.add(getResources().getString(R.string.text_quant));
						strs.add(getResources().getString(R.string.text_unit));
						strs.add(getResources().getString(R.string.text_amount));
					context.getObject().ASCII_PrintTabString(context.getState(), tabCtrl, strs, "gb2312");
				/*	if(size==1){
						strs.clear();
						strs.add(" ");
						strs.add(getResources().getString(R.string.text_ity));
						strs.add(getResources().getString(R.string.text_price));
						strs.add(" \r\n");
					 context.getObject().ASCII_PrintTabString(context.getState(), tabCtrl, strs, "gb2312");
					}*/
					 context.getObject().ASCII_PrintString(context.getState(),
							 0, 0, 0, 0, 0, getResources().getString(R.string.text_line), "gb2312");
						 strs.clear();
						 strs.add(getResources().getString(R.string.text_goods1));
						 strs.add("1");
						 strs.add(10+monetary);
						 price=1*10;
						 strs.add(price+monetary+"\r\n");
						 context.getObject().ASCII_PrintTabString(context.getState(), tabCtrl, strs, "gb2312");
						 sum+=price;
						 strs.clear();
						 strs.add(getResources().getString(R.string.text_goods2));
						 strs.add("2");
						 strs.add(20+monetary);
						 price=2*20;
						 strs.add(price+monetary+"\r\n");
						 context.getObject().ASCII_PrintTabString(context.getState(), tabCtrl, strs, "gb2312");
						 sum+=price;
						 strs.clear();
						 strs.add(getResources().getString(R.string.text_goods3));
						 strs.add("3");
						 strs.add(30+monetary);
						 price=3*30;
						 strs.add(price+monetary+"\r\n");
						 context.getObject().ASCII_PrintTabString(context.getState(), tabCtrl, strs, "gb2312");
						 sum+=price;
					 	strs.clear();
						strs.add(getResources().getString(R.string.text_total));
						strs.add(" ");
						strs.add(" ");
						strs.add(sum+monetary+"\r\n");
					 context.getObject().ASCII_PrintTabString(context.getState(), tabCtrl, strs, "gb2312");
						strs.clear();
						strs.add(getResources().getString(R.string.text_payment));
						strs.add(" ");
						strs.add(" ");
						strs.add(200+monetary+"\r\n");
					 context.getObject().ASCII_PrintTabString(context.getState(), tabCtrl, strs, "gb2312");
						strs.clear();
						strs.add(getResources().getString(R.string.text_change));
						strs.add(" ");
						strs.add(" ");
						strs.add(200-sum+monetary+"\r\n");//找回零钱
				   	 context.getObject().ASCII_PrintTabString(context.getState(), tabCtrl, strs, "gb2312");
						strs.clear();
						strs.add(getResources().getString(R.string.text_settlement));
						if(size==2){
						strs.add(" ");
						}
						strs.add(" ");
						strs.add(sum+monetary+"\r\n");
					 context.getObject().ASCII_PrintTabString(context.getState(), tabCtrl, strs, "gb2312");
					 context.getObject().ASCII_PrintString(context.getState(),
							 0, 0, 0, 0, 0, getResources().getString(R.string.text_line), "gb2312");
					 context.getObject().ASCII_PrintString(context.getState(),
							 0, 0, 0, 0, 0, getResources().getString(R.string.text_mes), "gb2312");
					 context.getObject().ASCII_CtrlReset(context.getState());
					 context.getObject().CON_PageEnd(context.getState(),context.getPrintway());

			}
		});
		print91.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 context.getObject().CON_PageStart(context.getState(),false,0,0);
				 context.getObject().ASCII_PrintString(context.getState(),0,
				 1, 0, 0, 0, "  BIHAR EXCISE\n\n", "gb2312");//
				 context.getObject().ASCII_PrintString(context.getState(),0,
						 0, 0, 0, 1, 
						"Model No:Eagle-3\n" +
				 		"DeviceID:E3000648\n" +
				 		"CaliDate:01/04/2016\n" +
				 		"Test No:00000001\n" +
				 		"Date:01/01/2013\n" +
				 		"Time:12:22\n" +
				 		"Zer0:0.000\n" +
				 		"Test mode:Passive\n" +
				 		"Test resultBAC:\n", "gb2312");
				
				 context.getObject().ASCII_PrintString(context.getState(),0,
						 1, 0, 0, 0, "597.0mg/100ml\n", "gb2312");
				 context.getObject().ASCII_PrintString(context.getState(),0,
						 0, 0, 0, 1, "Accused Name:\n\n" +
						 		"Mode Of Travel:\n\n" +
						 		"Driver license/ID No:\n" +
						 		"147258369x0\n" +
						 		"Vehicle RegNo/TrainNo:\n" +
						 		"01234567BHNTZYXWVUP8\n" +
						 		"PNR/Ticket No:\n" +
						 		"147258369.0\n" +
						 		"Accused Sign:\n\n" +
						 		"            _ _ _ _ _ _\n" +
						 		"Test Location&Distt:\n\n" +
						 		"Longitude & Latitude:\n\n" +
						 		"Offocer's Name&design:\n\n" +
						 		"Officer's Sign:\n\n" +
						 		"            _ _ _ _ _ _\n" +
						 		".    .    .    .    .  \n\n", "gb2312");
				 context.getObject().CON_PageEnd(context.getState(),context.getPrintway());
			}
		});
		/*fire.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 context.getObject().CON_PageStart(context.getState(),false,0,0);
				 context.getObject().ASCII_CtrlAlignType(context.getState(),
				 AlignType.AT_RIGHT.getValue());
				 context.getObject().ASCII_PrintString(context.getState(),0,
				 0, 0, 0, 0, getResources().getString(R.string.text_fire), "gb2312");
				 context.getObject().ASCII_CtrlPrintCRLF(context.getState(),1);
				 context.getObject().ASCII_CtrlAlignType(context.getState(),
				 AlignType.AT_CENTER.getValue());
				// context.getObject().ASCII_CtrlPrintCRLF(context.getState(),1);
				 context.getObject().ASCII_PrintString(context.getState(),0,
				 0, 1, 0, 0, getResources().getString(R.string.text_fire1), "gb2312");
				 context.getObject().ASCII_CtrlPrintCRLF(context.getState(),1);
				 context.getObject().ASCII_PrintString(context.getState(),0,
				 0, 0, 0, 0, getResources().getString(R.string.text_fire2),
				 "gb2312");
				 context.getObject().ASCII_CtrlPrintCRLF(context.getState(),1);
				 context.getObject().ASCII_PrintString(context.getState(),0,
				 0, 0, 0, 0, getResources().getString(R.string.text_fire3),
				 "gb2312");
				 context.getObject().ASCII_CtrlPrintCRLF(context.getState(),1);
				 context.getObject().ASCII_PrintString(context.getState(),0,
				 0, 0, 0, 0, getResources().getString(R.string.text_fire4),
				 "gb2312");
				 context.getObject().ASCII_CtrlPrintCRLF(context.getState(),1);
				 context.getObject().ASCII_CtrlReset(context.getState());
				 context.getObject().ASCII_PrintString(context.getState(),0,
				 0, 0, 0, 0, getResources().getString(R.string.text_fire5), "gb2312");
				 context.getObject().ASCII_CtrlPrintCRLF(context.getState(),1);
				 context.getObject().ASCII_CtrlAlignType(context.getState(),
						 AlignType.AT_RIGHT.getValue());
				 context.getObject().ASCII_Print2DBarcode(context.getState(),BarcodeType.BT_QRcode.getValue(),"123456789",8,76,2);
				 context.getObject().ASCII_CtrlReset(context.getState());
				 context.getObject().ASCII_PrintString(context.getState(),0,
						 0, 0, 0, 0, "\r\n1234567789012434\r\n", "gb2312");
				 context.getObject().CON_PageEnd(context.getState(),context.getPrintway());
			}
		});*/
	}
}