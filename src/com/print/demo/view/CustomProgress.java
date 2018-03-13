package com.print.demo.view;

import com.print.demorego.R;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

/** 
 * ���罻��ʱ��ʾ״̬��
 * 
 * @author yxx
 *
 * @date 2016-2-23 ����2:59:47
 * 
 */
public class CustomProgress extends Dialog {
	
	private static CustomProgress dialog;
	
	public CustomProgress(Context context) {
		super(context);
	}

	public CustomProgress(Context context, int theme) {
		super(context, theme);
	}

	/**
	 * �����ڽ���ı�ʱ����
	 */
	public void onWindowFocusChanged(boolean hasFocus) {
		ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
		// ��ȡImageView�ϵĶ�������
		AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
		// ��ʼ����
		spinner.start();
	}

	/**
	 * ��Dialog������ʾ��Ϣ
	 * 
	 * @param message
	 */
	public void setMessage(CharSequence message) {
		if (message != null && message.length() > 0) {
			findViewById(R.id.message).setVisibility(View.VISIBLE);
			TextView txt = (TextView) findViewById(R.id.message);
			txt.setText(message);
			txt.invalidate();
		}
	}

	/**
	 * �����Զ���ProgressDialog
	 * 
	 * @param context
	 *            ������
	 * @param message
	 *            ��ʾ
	 * @param cancelable
	 *            �Ƿ񰴷��ؼ�ȡ��
	 * @param cancelListener
	 *            ���·��ؼ�����
	 * @return
	 */
	public static CustomProgress showDialog(Context context, CharSequence message, boolean cancelable, OnCancelListener cancelListener) {
		
		if(context == null){
			return null;
		}
		
		if(dialog != null){
			dialog.dismiss();
		}
		
		dialog = new CustomProgress(context, R.style.Custom_Progress);
	
		dialog.setTitle("");
		dialog.setContentView(R.layout.progress_custom);
		if (message == null || message.length() == 0) {
			dialog.findViewById(R.id.message).setVisibility(View.GONE);
		} else {
			TextView txt = (TextView) dialog.findViewById(R.id.message);
			txt.setText(message);
		}
		// �����ؼ��Ƿ�ȡ��
		dialog.setCancelable(cancelable);
		// �������ؼ�����
		dialog.setOnCancelListener(cancelListener);
		// ���þ���
		dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		// ���ñ�����͸����
		lp.dimAmount = 0.2f;
		dialog.getWindow().setAttributes(lp);
		// dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		
		try{
			dialog.show();
		}catch(Exception e){
			e.printStackTrace();
		}
		return dialog;
	}
	
	/**
	 * �رմ���
	 */
	public static void dissDialog(){
		
		if(dialog != null){
			
			try{
				dialog.dismiss();
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}
}
