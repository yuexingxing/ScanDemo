package com.print.demo.printview;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.print.demo.ApplicationContext;
import com.print.demo.secondview.PrintModeActivity;
import com.print.demorego.R;

import utils.preDefiniation.AlignType;
import utils.preDefiniation.BarcodeType;
import utils.preDefiniation.ValignType;
import android.app.Activity;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class GraphicThirdActivity extends Activity {
	public int[] fs;
	public int[] at;
	public int[] vat;
	public boolean[] bValid;
	public String[] strCell;
	public Button lab;
	public Button fire;
	public Button move;
	public Button shopping;
	public EditText et1;
	public EditText et2;
	public EditText et3;
	public Button pdf;
	//public Button deskBtn;
	public ApplicationContext context;
int width=576;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picapply);
		lab = (Button) findViewById(R.id.Button_pictable);
		fire = (Button) findViewById(R.id.Button_picfire);
		shopping = (Button) findViewById(R.id.Button_picshopping);
		move = (Button) findViewById(R.id.button_picmovie);
		pdf = (Button) findViewById(R.id.Button_pdf);
	//	deskBtn = (Button) findViewById(R.id.Button_desktop);
		context = (ApplicationContext) getApplicationContext();
		et1=(EditText) findViewById(R.id.editText1);
		et2=(EditText) findViewById(R.id.editText2);
	//	et3=(EditText) findViewById(R.id.editText3);
		//路径
		et1.setText("/sdcard/MLP80A1.pdf");
		move.setEnabled(true);
		fire.setEnabled(true);
		shopping.setEnabled(true);
		lab.setEnabled(false);
		pdf.setEnabled(false);
		RadioGroup radiogrop = (RadioGroup) findViewById(R.id.radioGroup2);
		radiogrop.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.radioButton1:
					move.setEnabled(true);
					fire.setEnabled(true);
					shopping.setEnabled(true);
					lab.setEnabled(false);
					pdf.setEnabled(false);
					break;
				case R.id.radioButton2:
					move.setEnabled(false);
					fire.setEnabled(false);
					shopping.setEnabled(false);
					lab.setEnabled(true);
					pdf.setEnabled(true);
					width=576;
					break;
				default:
					move.setEnabled(false);
					fire.setEnabled(false);
					shopping.setEnabled(false);
					lab.setEnabled(false);
					pdf.setEnabled(true);
					if(context.getName().equalsIgnoreCase("DS-1920")){
						width=2600;
					}else{
						width=1480;
					}
					break;
				}
			}
		});
		lab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(context.getName().equalsIgnoreCase("RG-MLP80A")){
					context.getObject().CON_PageStart(context.getState(), false,
							900,
							576);//384
				}
				context.getObject().CON_PageStart(context.getState(), true,
						900, 576);//384
				int[] hRow = { 50, 50, 50, 50, 50, 50,50,50 };// 6行
				int[] wColumn = { 100, 150, 100, 100, 100, 100, 100, 100 };// 8列
				/*context.getObject().DRAW_Table(context.getState(), 0, 700, 900,
						400, 6, 8, hRow, wColumn, 4);*/
				context.getObject().DRAW_Table(context.getState(), 0, 20, 900,
						570, 8, 8, hRow, wColumn, 4);//x,y,w,h  //0,0,900,380
				bValid = new boolean[8];
				bValid[0] = true;
				bValid[1] = true;
				bValid[2] = true;
				bValid[3] = true;
				bValid[4] = true;
				bValid[5] = true;
				bValid[6] = true;
				bValid[7] = true;
				strCell = new String[8];
				strCell[0] = getResources().getString(R.string.text_lab);
				strCell[1] = getResources().getString(R.string.text_lab1);
				strCell[2] = getResources().getString(R.string.text_lab2);
				strCell[3] = getResources().getString(R.string.text_lab3);
				strCell[4] = getResources().getString(R.string.text_lab4) ;
				strCell[5] = getResources().getString(R.string.text_lab5) ;
				strCell[6] = getResources().getString(R.string.text_lab6);
				strCell[7] = getResources().getString(R.string.text_lab7) ;
				fs = new int[8];
				fs[0] = 20;
				fs[1] = 20;
				fs[2] = 20;
				fs[3] = 20;
				fs[4] = 20;
				fs[5] = 20;
				fs[6] = 20;
				fs[7] = 20;
				at = new int[8];
				at[0] = AlignType.AT_CENTER.getValue();
				at[1] = AlignType.AT_CENTER.getValue();
				at[2] = AlignType.AT_CENTER.getValue();
				at[3] = AlignType.AT_CENTER.getValue();
				at[4] = AlignType.AT_CENTER.getValue();
				at[5] = AlignType.AT_CENTER.getValue();
				at[6] = AlignType.AT_CENTER.getValue();
				at[7] = AlignType.AT_CENTER.getValue();
				vat = new int[8];
				vat[0] = ValignType.VT_MIDDLE.getValue();
				vat[1] = ValignType.VT_MIDDLE.getValue();
				vat[2] = ValignType.VT_MIDDLE.getValue();
				vat[3] = ValignType.VT_MIDDLE.getValue();
				vat[4] = ValignType.VT_MIDDLE.getValue();
				vat[5] = ValignType.VT_MIDDLE.getValue();
				vat[6] = ValignType.VT_MIDDLE.getValue();
				vat[7] = ValignType.VT_MIDDLE.getValue();
				context.getObject().DRAW_TableRow(context.getState(), bValid,
						strCell, fs, at, vat);// 第1行
				strCell[0] = "1";
				strCell[1] = "CG002011";
				strCell[2] = getResources().getString(R.string.text_lab8);
				strCell[3] = "441.00";
				strCell[4] = "0.00";
				strCell[5] = "0.00";
				strCell[6] = "441.00";
				strCell[7] = "0.00";
				context.getObject().DRAW_TableRow(context.getState(), bValid,
						strCell, fs, at, vat);// 第2行
				strCell[0] = "2";
				context.getObject().DRAW_TableRow(context.getState(), bValid,
						strCell, fs, at, vat);// 第3行
				strCell[0] = "3";
				context.getObject().DRAW_TableRow(context.getState(), bValid,
						strCell, fs, at, vat);// 第4行
				strCell[0] = "4";
				context.getObject().DRAW_TableRow(context.getState(), bValid,
						strCell, fs, at, vat);// 第5行
				strCell[0] = "5";
				context.getObject().DRAW_TableRow(context.getState(), bValid,
						strCell, fs, at, vat);// 第6行
				bValid[0] = false;
				bValid[1] = false;
				bValid[2] = false;
				bValid[3] = false;
				bValid[4] = false;
				bValid[5] = false;
				bValid[6] = false;
				bValid[7] = true;
				at = new int[1];
				at[0] = AlignType.AT_LEFT.getValue();
				strCell = new String[1];
				strCell[0] =getResources().getString(R.string.text_lab9);
				context.getObject().DRAW_TableRow(context.getState(), bValid,
						strCell, fs, at, vat);// 第5行
				at[0] = AlignType.AT_LEFT.getValue();
				strCell[0] =getResources().getString(R.string.text_lab10) ;
				context.getObject().DRAW_TableRow(context.getState(), bValid,
						strCell, fs, at, vat);// 第6行
				context.getObject().DRAW_SetRotate(context.getState(), 90);
				context.getObject().CON_PageEnd(context.getState(),
						context.getPrintway());

			}
		});
		fire.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(context.getName().equalsIgnoreCase("RG-MLP80A")){
					context.getObject().CON_PageStart(context.getState(), false,
							384,
							400);
				}
				context.getObject().CON_PageStart(context.getState(), true,
						384, 300);
				context.getObject().DRAW_SetFillMode(false);
				context.getObject().DRAW_SetLineWidth(2);
				context.getObject().DRAW_PrintText(context.getState(), 320, 30,
						getResources().getString(R.string.text_fire7), 14);
				context.getObject().DRAW_PrintCircle(context.getState(), 373,
						33, 15);
				context.getObject().DRAW_PrintText(context.getState(), 364, 30,
						getResources().getString(R.string.text_fire8), 14);
				context.getObject().DRAW_PrintText(context.getState(), 50, 60,
						getResources().getString(R.string.text_fire7), 22);
				context.getObject().DRAW_PrintText(context.getState(), 290, 60,
						getResources().getString(R.string.text_fire9), 22);
				context.getObject().DRAW_PrintText(context.getState(), 175, 50,
						getResources().getString(R.string.text_fire10), 20);
				context.getObject().DRAW_PrintLine(context.getState(), 180, 80,
						240, 80);
				context.getObject().DRAW_PrintLine(context.getState(), 230, 80,
						220, 75);
				context.getObject().DRAW_PrintText(context.getState(), 55, 100,
						"Beijing", 16);
				context.getObject().DRAW_PrintText(context.getState(), 300, 95,
						"Wuhan", 16);
				context.getObject().DRAW_PrintText(context.getState(), 20, 125,
						getResources().getString(R.string.text_fire11), 14);
				context.getObject().DRAW_PrintText(context.getState(), 280,
						125, getResources().getString(R.string.text_fire12), 16);
				context.getObject().DRAW_PrintText(context.getState(), 23, 150,
						getResources().getString(R.string.text_fire13), 22);
				context.getObject().DRAW_PrintText(context.getState(), 270,
						155, getResources().getString(R.string.text_fire14), 16);
				context.getObject().DRAW_PrintText(context.getState(), 20, 185,
						getResources().getString(R.string.text_fire15), 16);

				context.getObject().DRAW_PrintText(context.getState(), 35, 260,
						"123456789012345", 16);
				context.getObject().DRAW_Print1D2DBarcode(context.getState(),
						BarcodeType.BT_QRcode.getValue(), 300, 185, 90, 90,
						getResources().getString(R.string.text_fire16));
				context.getObject().CON_PageEnd(context.getState(),
						context.getPrintway());
			}
		});
		shopping.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(context.getName().equalsIgnoreCase("RG-MLP80A")){
					context.getObject().CON_PageStart(context.getState(), false,
							576,
							600);
				}
				int size = 20;
				context.getObject().CON_PageStart(context.getState(), true,
						576, 600);
				context.getObject().DRAW_SetFillMode(false);
				context.getObject().DRAW_SetLineWidth(2);
				context.getObject().DRAW_PrintText(context.getState(), 100, 5,
						getResources().getString(R.string.text_shop1), 25);
				context.getObject().DRAW_PrintText(context.getState(), 110, 35,
						getResources().getString(R.string.text_shop2), size);

				context.getObject().DRAW_PrintText(context.getState(), 5, 60,
						getResources().getString(R.string.text_shop3), size);
				context.getObject().DRAW_PrintText(context.getState(), 5, 85,
						getResources().getString(R.string.text_shop4), size);
				context.getObject().DRAW_PrintLine(context.getState(), 2, 109,
						300, 109);
				context.getObject().DRAW_PrintText(context.getState(), 5, 125,
						getResources().getString(R.string.text_shop5), 22);

				context.getObject().DRAW_PrintText(context.getState(), 5, 150,
						getResources().getString(R.string.text_shop6),
						size);
				context.getObject().DRAW_PrintLine(context.getState(), 2, 175,
						300, 175);
				context.getObject().DRAW_PrintText(context.getState(), 5, 190,
						getResources().getString(R.string.text_shop7), size);
				context.getObject().DRAW_PrintText(context.getState(), 5, 215,
						getResources().getString(R.string.text_shop8), size);
				context.getObject().DRAW_PrintLine(context.getState(), 2, 239,
						300, 239);
				context.getObject().DRAW_PrintText(context.getState(), 5, 255,
						getResources().getString(R.string.text_shop9), size);

				context.getObject().DRAW_PrintText(context.getState(), 5, 280,
						getResources().getString(R.string.text_shop10), size);
				context.getObject().DRAW_PrintText(context.getState(), 5, 305,
						getResources().getString(R.string.text_shop11), size);

				context.getObject().CON_PageEnd(context.getState(),
						context.getPrintway());
			}
		});
		move.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(context.getName().equalsIgnoreCase("RG-MLP80A")){
					context.getObject().CON_PageStart(context.getState(), false,
							384,
							420);
				}
				context.getObject().CON_PageStart(context.getState(), true,
						384, 420);
				context.getObject().DRAW_SetFillMode(false);
				context.getObject().DRAW_SetLineWidth(4);
				context.getObject().DRAW_PrintText(context.getState(), 95, 25,
						getResources().getString(R.string.text_move), 25);
				context.getObject().DRAW_PrintLine(context.getState(), 95, 65,
						300, 65);
				// context.getObject().DRAW_PrintText(context.getState(),95, 70,
				// "beijng", 20);
				context.getObject().DRAW_SetLineWidth(2);
				context.getObject().DRAW_PrintLine(context.getState(), 0, 90,
						390, 90);
				context.getObject().DRAW_PrintText(context.getState(), 85, 110,
						getResources().getString(R.string.text_move15), 18);
				// 矩形
				context.getObject().DRAW_PrintRectangle(context.getState(), 10,
						165, 185, 235);
				context.getObject().DRAW_PrintRectangle(context.getState(),
						190, 165, 380, 235);
				context.getObject().DRAW_PrintRectangle(context.getState(), 10,
						240, 380, 300);
				context.getObject().DRAW_PrintRectangle(context.getState(), 10,
						305, 185, 365);
				context.getObject().DRAW_PrintRectangle(context.getState(),
						190, 305, 380, 365);//60
				context.getObject().DRAW_PrintRectangle(context.getState(), 10,
						370, 380, 420);//60
				// 文字
				context.getObject().DRAW_PrintText(context.getState(), 17, 175,
						getResources().getString(R.string.text_move6), 22);
				context.getObject().DRAW_PrintText(context.getState(), 195,
						175, getResources().getString(R.string.text_move7), 22);
				context.getObject().DRAW_PrintText(context.getState(), 17, 245,
						getResources().getString(R.string.text_move8), 22);
				context.getObject().DRAW_PrintText(context.getState(), 17, 305,
						getResources().getString(R.string.text_move9), 22);
				context.getObject().DRAW_PrintText(context.getState(), 195,
						305, getResources().getString(R.string.text_move10), 22);
				// 变化文字
				context.getObject().DRAW_PrintText(context.getState(), 57, 194,
						getResources().getString(R.string.text_move11), 22);
				context.getObject().DRAW_PrintText(context.getState(), 225,
						194, getResources().getString(R.string.text_move12), 22);
				context.getObject().DRAW_PrintText(context.getState(), 57, 264,
						getResources().getString(R.string.text_move13), 22);
				context.getObject().DRAW_PrintText(context.getState(), 57, 328,
						getResources().getString(R.string.text_move14), 22);
				context.getObject().DRAW_PrintText(context.getState(), 225,
						328, "80$", 22);
				context.getObject().DRAW_Print1D2DBarcode(context.getState(),
						BarcodeType.BT_UPCA.getValue(), 70, 375, 240, 30,
						"123456789012");

		
				context.getObject().CON_PageEnd(context.getState(),
						context.getPrintway());
			}
		});
		pdf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(context.getName().equalsIgnoreCase("RG-MLP80A")){
					context.getObject().CON_PageStart(context.getState(), false,
						576,
							1000);
				}
			
				int result=0;
				try{
				 File f=new File(et1.getText().toString());
                 if(!f.exists()){
                       // return false;
                	 result=-1;
                 }
                 
         }catch (Exception e) {
                 // TODO: handle exception
        	 result=-1;
         }
				
			if(result==-1){
				Toast.makeText(GraphicThirdActivity.this, "no file", Toast.LENGTH_LONG).show();
			}else{
				
			context.getObject()
				.CON_PageStart(context.getState(), true,width,(int) (width*1.4) );//1480*1.4
			context.getObject().DRAW_PrintPDF(context.getState(),et1.getText().toString(),new RectF(0f,0f,1f,1f),Integer.parseInt(et2.getText().toString()), width,width==1480?0:-1);
			context.getObject().CON_PageEnd(context.getState(),
						context.getPrintway());
			
			
			}
				}
		});

	}
}