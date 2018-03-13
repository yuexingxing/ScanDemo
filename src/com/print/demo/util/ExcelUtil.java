package com.print.demo.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.print.demo.ApplicationContext;
import com.print.demo.data.PrintInfo;
import com.print.demo.db.dao.ScanDataDao;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/** 
 * Excel文件读取
 * 
 * @author yxx
 *
 * @date 2017-11-15 上午10:23:30
 * 
 */
public class ExcelUtil {

	public ScanDataDao scanDataDao = new ScanDataDao();

	// 弹窗结果回调函数
	public static abstract class CallBack {
		public abstract void callback(int pos);
	}

	public void readExcel(String path, List<PrintInfo> sourceList, final CallBack callback) {

		if(path.endsWith(".xml")){
			readExcelXML(path, sourceList, callback);
		}else{
			readExcelXLSX(path, sourceList, callback);
		}
	}

	/**
	 * 读取Excel数据，并保存到数据库
	 * @param dataList
	 * @param callback
	 */
	public void readExcelXML(String path, List<PrintInfo> dataList, final CallBack callback) {  

		try {  

			Workbook book = Workbook.getWorkbook(new File(path));  
			book.getNumberOfSheets();  
			// 获得第一个工作表对象  
			Sheet sheet = book.getSheet(0);  
			int Rows = sheet.getRows();  
			int Cols = sheet.getColumns();  

			scanDataDao.clearScanData();
			for (int i = 0; i < Rows; i++) {  

				// 得到第一列第一行的单元格  
				Cell billcode = sheet.getCell(0, i);// 订单号 
				Cell goodsName = sheet.getCell(1, i);//商品名称 

				//每一行进行操作
				PrintInfo info = new PrintInfo();
				info.setBillcode(billcode.getContents());
				info.setName(goodsName.getContents());

				scanDataDao.addScanData(info);
				dataList.add(info);
			}  

			book.close();  
		} catch (Exception e) {  
			e.printStackTrace();
			System.out.println(e);  
			callback.callback(-1);
		}  

		callback.callback(0);
	}  

	public void readExcelXLSX(String path, List<PrintInfo> sourceList, final CallBack callback) {

		String str = "";
		String v = null;
		boolean flat = false;
		List<String> ls = new ArrayList<String>();
		try {
			ZipFile xlsxFile = new ZipFile(new File(path));
			ZipEntry sharedStringXML = xlsxFile
					.getEntry("xl/sharedStrings.xml");
			InputStream inputStream = xlsxFile.getInputStream(sharedStringXML);
			XmlPullParser xmlParser = Xml.newPullParser();
			xmlParser.setInput(inputStream, "utf-8");
			int evtType = xmlParser.getEventType();
			while (evtType != XmlPullParser.END_DOCUMENT) {
				switch (evtType) {
				case XmlPullParser.START_TAG:
					String tag = xmlParser.getName();
					if (tag.equalsIgnoreCase("t")) {
						ls.add(xmlParser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				default:
					break;
				}
				evtType = xmlParser.next();
			}
			ZipEntry sheetXML = xlsxFile.getEntry("xl/worksheets/sheet1.xml");
			InputStream inputStreamsheet = xlsxFile.getInputStream(sheetXML);
			XmlPullParser xmlParsersheet = Xml.newPullParser();
			xmlParsersheet.setInput(inputStreamsheet, "utf-8");
			int evtTypesheet = xmlParsersheet.getEventType();
			while (evtTypesheet != XmlPullParser.END_DOCUMENT) {

				str = "";
				switch (evtTypesheet) {
				case XmlPullParser.START_TAG:
					String tag = xmlParsersheet.getName();
					if (tag.equalsIgnoreCase("row")) {

					} else if (tag.equalsIgnoreCase("c")) {
						String t = xmlParsersheet.getAttributeValue(null, "t");
						if (t != null) {
							flat = true;
							System.out.println(flat + "有");
						} else {
							System.out.println(flat + "没有");
							flat = false;
						}
					} else if (tag.equalsIgnoreCase("v")) {
						v = xmlParsersheet.nextText();
						if (v != null) {
							if (flat) {
								str += ls.get(Integer.parseInt(v)) + ",";
							} else {
								str += v + ",";
							}
						}

					}
					break;
				case XmlPullParser.END_TAG:
					if (xmlParsersheet.getName().equalsIgnoreCase("row") && v != null) {
						str += "\n";
					}
					break;
				}

				String[] strArr = str.split(",");
				if(strArr != null && strArr.length > 1){//一行读取结束

					PrintInfo info = new PrintInfo();
					info.setBillcode(strArr[0]);
					info.setName(strArr[1]);

					sourceList.add(info);
				}

				evtTypesheet = xmlParsersheet.next();
			}

		} catch (ZipException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}

		if(sourceList == null || sourceList.size() == 0){
			callback.callback(-1);
		}else{
			callback.callback(0);
		}
	}
}
