package com.print.demo.printview;

import java.io.InputStream;

import com.print.demo.ApplicationContext;
import com.print.demorego.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import demo.printlib.export.demoPrinter;
import utils.preDefiniation.BarcodeType;

public class GraphicSecondActivity extends Activity {
	private ApplicationContext context;
	private Button pridraw;
	// 标签设置
	public CheckBox label;
	public EditText wight;
	public EditText hight;
	
	/**
	 * 打印机控制类
	 */
	private demoPrinter mobileprinter;
	private int iObjectCode;
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picdraw);
		pridraw = (Button) findViewById(R.id.button_pridraw);
		// 标签设置
		label = (CheckBox) findViewById(R.id.checkBox_pic);//
		wight = (EditText) findViewById(R.id.editText_picwide);
		hight = (EditText) findViewById(R.id.editText_pichight);
		final InputStream bitmap = this.getResources().openRawResource(
				R.drawable.star);
		final InputStream bitmaptwo = this.getResources().openRawResource(
					R.drawable.printico);
		context = (ApplicationContext) getApplicationContext();
		
		mobileprinter = context.getObject();
		iObjectCode = context.getState();
		
		pridraw.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(label.isChecked()){
					if(context.getName().equalsIgnoreCase("RG-MLP80A")){
					context.getObject().CON_PageStart(context.getState(), false,
							Integer.parseInt(wight.getText().toString()),
							Integer.parseInt(hight.getText().toString()));
				}
				}
				context.getObject().CON_PageStart(context.getState(), true,
						Integer.parseInt(wight.getText().toString()),
						Integer.parseInt(hight.getText().toString()));
				//context.getObject().ASCII_CtrlReset(context.getState());
				context.getObject().DRAW_SetFillMode(false);
				context.getObject().DRAW_SetLineWidth(4);
				context.getObject().DRAW_PrintText(context.getState(), 10, 10,
						"REGO", 60);
				context.getObject().DRAW_PrintLine(context.getState(), 162, 7,
						162, 70);
				context.getObject().DRAW_PrintText(context.getState(), 164, 10,
						"瑞工", 60);
				context.getObject().DRAW_PrintText(context.getState(), 90, 80,
						"专业微型打印产品设计、生产、销售", 18);
				context.getObject().DRAW_PrintRectangle(context.getState(), 10,
						100, 320, 160);
				context.getObject().DRAW_PrintLine(context.getState(), 25, 110,
						25, 140);
				context.getObject().DRAW_PrintLine(context.getState(), 25, 140,
						57, 140);
				context.getObject().DRAW_PrintLine(context.getState(), 25, 110,
						57, 140);
				context.getObject().DRAW_PrintCircle(context.getState(), 90,
						125, 15);
				context.getObject().DRAW_PrintOval(context.getState(), 120,
						110, 160, 140);
				context.getObject().DRAW_PrintPicture(context.getState(),
						bitmap, 165, 102, 53, 44);
				context.getObject().DRAW_PrintRectangle(context.getState(),
						225, 110, 250, 140);
				context.getObject().DRAW_Print1D2DBarcode(context.getState(),
						BarcodeType.BT_QRcode.getValue(), 257, 100, 58, 58,
						"12345678");
				context.getObject().DRAW_PrintPicture(context.getState(),
						bitmaptwo, 0, 170, 268, 176);
				context.getObject().DRAW_SetRotate(context.getState(), 90);
		
			/*	context.getObject().DRAW_PrintText(context.getState(), 95, 25,
						"瑞工国际影城", 25);
				context.getObject().DRAW_PrintLine(context.getState(), 95, 65,
						300, 65);*/
				// context.getObject().DRAW_PrintText(context.getState(),95, 70,
				// "beijng", 20);
			/*	context.getObject().DRAW_SetLineWidth(2);
				context.getObject().DRAW_PrintLine(context.getState(), 0, 90,
						390, 90);*/
					context.getObject().CON_PageEnd(context.getState(),
						context.getPrintway());
			}
		});
	}
}
