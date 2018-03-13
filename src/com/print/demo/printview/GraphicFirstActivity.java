package com.print.demo.printview;

import com.print.demo.ApplicationContext;
import com.print.demorego.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class GraphicFirstActivity extends Activity {
	public AutoCompleteTextView languageText;
	public Button printLan;
	// ��ǩ����
	public CheckBox label;
	public EditText wight;
	public EditText hight;
	public EditText X0;
	public EditText Y0;
	public ApplicationContext context;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_language);
		// ��ǩ����
		label = (CheckBox) findViewById(R.id.checkBox_lanlab);
		wight = (EditText) findViewById(R.id.editText_lanwide);
		hight = (EditText) findViewById(R.id.editText_lanhight);

		printLan = (Button) findViewById(R.id.button_printlan);
		context = (ApplicationContext) getApplicationContext();
		languageText = (AutoCompleteTextView) findViewById(R.id.autoCompleteText_lan);
		printLan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(label.isChecked()){
					if(context.getName().equalsIgnoreCase("RG-MLP80A")){
					/*context.getObject().CON_PageStart(context.getState(), false,
							Integer.parseInt(wight.getText().toString()),
							Integer.parseInt(hight.getText().toString()));*/
						context.getObject().CPCL_PageStart(context.getState(), Integer.parseInt(wight.getText().toString()),
								Integer.parseInt(hight.getText().toString()), 96, 1);
					}
				}
				context.getObject().CON_PageStart(context.getState(), true,
						Integer.parseInt(wight.getText().toString()),
						Integer.parseInt(hight.getText().toString()));
				context.getObject().DRAW_SetFillMode(false);
				// �Զ��������ݽ��д���һ��һ�д���
				int y = 0;
				String str[] = languageText.getText().toString().split("\n");
				for (int i = 0; i < str.length; i++) {
					y += 25;
					context.getObject().DRAW_PrintText(context.getState(), 20,
							y, str[i], 24);
				}
				context.getObject().CON_PageEnd(context.getState(),
						context.getPrintway());
				
				context.getObject().CON_QueryPrintStatus( context.getState(), 10 * 1000 , false );
			}
		});
	}
}