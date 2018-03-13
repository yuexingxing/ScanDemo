package com.print.demo.printview;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import utils.PathtoRight;

import com.print.demo.ApplicationContext;
import com.print.demorego.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class PhotoActivity extends Activity {
	private Button choose;
	private Button printer;
	private EditText path;// editText1
	private int threshold = 128;
	private int width = 384;
	private EditText ed_threshold;// editText2
	private EditText ed_width;// editText2
	public static final int CROP_PHOTO = 2;
	private Uri imageUri;
	private Bitmap bitmap;
	private ImageView image;
	private Boolean ROTATE=false;
	ApplicationContext content;
	public File outputImage = new File(
			Environment.getExternalStorageDirectory(), "tempImage.jpg");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		choose = (Button) findViewById(R.id.button_choose);
		printer = (Button) findViewById(R.id.button_printer);
		path = (EditText) findViewById(R.id.editText1);
		ed_threshold = (EditText) findViewById(R.id.editText2);
		ed_width = (EditText) findViewById(R.id.editText3);
		image=(ImageView) findViewById(R.id.imageView_show);
		bitmap = BitmapFactory.decodeStream(this.getResources()
				.openRawResource(R.drawable.ic_launcher));
		content = (ApplicationContext) getApplicationContext();

		printer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				threshold = Integer.parseInt(ed_threshold.getText().toString());
				width = Integer.parseInt(ed_width.getText().toString());
				/*if (path.getText().toString() == null
						| path.getText().toString().equals("")) {

				} else {
					bitmap = BitmapFactory
							.decodeFile(path.getText().toString());
				}*/
				if(content.getName().equalsIgnoreCase("RG-MLP80A")){
					/*context.getObject().CON_PageStart(context.getState(), false,
							Integer.parseInt(wight.getText().toString()),
							Integer.parseInt(hight.getText().toString()));*/
					content.getObject().CPCL_PageStart(content.getState(),width*
							 bitmap.getHeight()/bitmap.getWidth(),width,0, 1);
					}
			  if(ROTATE){
				  content.getObject().CON_PageStart(content.getState(),true,width*
							 bitmap.getHeight()/bitmap.getWidth(),width);
					  content.getObject().DRAW_SetPhotoThreshold(content.getState(),threshold);
					 content.getObject().DRAW_PrintPicture(content.getState(),path.getText().toString(),0,0,width*bitmap.getHeight
					 ()/bitmap.getWidth(), width);
					 content.getObject().DRAW_SetRotate(content.getState(), 90); 
				
			  }else{
				content.getObject().CON_PageStart(content.getState(), true,
						width, width * bitmap.getHeight() / bitmap.getWidth());
				content.getObject().DRAW_SetPhotoThreshold(content.getState(),
						threshold);
				content.getObject().DRAW_PrintPicture(content.getState(),
						bitmap, 0, 0, width,
						width * bitmap.getHeight() / bitmap.getWidth());
				
			}
				 
				 
				content.getObject().CON_PageEnd(content.getState(), 0);
			}
		});
		choose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					if (outputImage.exists()) {
						outputImage.delete();
					}
					outputImage.createNewFile();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				imageUri = Uri.fromFile(outputImage);

				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("*/*");
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				try {
					startActivityForResult(
							Intent.createChooser(intent, "choose"), CROP_PHOTO);
				} catch (android.content.ActivityNotFoundException ex) {
					// Potentially direct the user to the Market with a Dialog
					Toast.makeText(PhotoActivity.this, "choose fail",
							Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		// super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case CROP_PHOTO:
			if (resultCode == RESULT_OK) {
				try {
					Uri fileUrl = data.getData();
					path.setText(PathtoRight.filePath(fileUrl, this));
				} catch (Exception e) {
					if (data == null) {
						path.setText("");
					} else {
						try {
							bitmap = BitmapFactory
									.decodeStream(getContentResolver()
											.openInputStream(data.getData()));
							BitmapFactory.Options options = new BitmapFactory.Options();
							options.inJustDecodeBounds = true;
							FileOutputStream out = new FileOutputStream(
									outputImage);
							bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
							out.flush();
							out.close();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						path.setText(Environment.getExternalStorageDirectory()
								+ "/" + "tempImage.jpg");
					}
				}
				super.onActivityResult(requestCode, resultCode, data);
			}
			break;
		default:
			break;
		}
		if (path.getText().toString() == null
				| path.getText().toString().equals("")) {

		} else {
			bitmap = BitmapFactory
					.decodeFile(path.getText().toString());
			image.setImageBitmap(bitmap);
		}
	}

}
