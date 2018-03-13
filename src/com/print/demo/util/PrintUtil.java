package com.print.demo.util;

import com.print.demo.ApplicationContext;
import com.print.demo.data.PrintInfo;
import com.print.demorego.R;

import utils.preDefiniation.AlignType;
import utils.preDefiniation.BarcodeType;

/** 
 * ��ӡ��
 * 
 * @author yxx
 *
 * @date 2018-3-12 ����5:52:03
 * 
 */
public class PrintUtil {

	public static ApplicationContext context = ApplicationContext.getInstance();

	public static void printLabelTest(PrintInfo info){
		//
		context.getObject().CON_PageStart(context.getState(),false, 0, 0);//0,0
		context.getObject().ASCII_CtrlAlignType(context.getState(),
				AlignType.AT_CENTER.getValue());

		context.getObject().ASCII_CtrlFeedLines(context.getState(), 0);
		context.getObject().ASCII_PrintString(context.getState(), 0, 0, 1, 0, 0, "1\r\n", "gb2312");
		context.getObject().ASCII_PrintString(context.getState(), 0, 0, 0, 0, 0, "2\r\n", "gb2312");
		context.getObject().ASCII_PrintString(context.getState(), 0, 0, 0, 0, 0, "3\r\n", "gb2312");
		context.getObject().ASCII_PrintString(context.getState(), 0, 0, 0, 0, 0, "4\r\n", "gb2312");
		context.getObject().ASCII_PrintString(context.getState(), 0, 0, 0, 0, 0, "5\r\n", "gb2312");
		context.getObject().ASCII_PrintString(context.getState(), 0, 0, 0, 0, 0, "6\r\n", "gb2312");
		context.getObject().ASCII_PrintString(context.getState(), 0, 0, 0, 0, 0, "7\r\n", "gb2312");
		context.getObject().ASCII_PrintString(context.getState(), 0, 0, 0, 0, 0, "8\r\n", "gb2312");
		context.getObject().ASCII_PrintString(context.getState(), 0, 0, 0, 0, 0, "9\r\n", "gb2312");
		context.getObject().ASCII_PrintString(context.getState(), 0, 0, 0, 0, 0, "10\r\n", "gb2312");
		context.getObject().ASCII_PrintString(context.getState(), 0, 0, 0, 0, 0, "11\r\n", "gb2312");
		context.getObject().ASCII_PrintString(context.getState(), 0, 0, 0, 0, 0, "12\r\n", "gb2312");
		context.getObject().ASCII_CtrlFeedLines(context.getState(), 1);

		//		context.getObject().ASCII_CtrlReset(context.getState());
		//		context.getObject().ASCII_Print1DBarcode(context.getState(),BarcodeType.BT_CODE39.getValue(), 2, 32,
		//				utils.preDefiniation.Barcode1DHRI.BH_BLEW.getValue(), info.getBillcode());

		context.getObject().CON_PageEnd(context.getState(), context.getPrintway());
	}

	/**
	 * ����һ��Ҫ��Ӧ
	 * @param info
	 */
	public static void printLabel(PrintInfo info){

		String spaceStr = "     ";//������������

		context.getObject().CON_PageStart(context.getState(),false, 0, 0);//0,0
		context.getObject().ASCII_CtrlAlignType(context.getState(),
				AlignType.AT_LEFT.getValue());

		context.getObject().ASCII_CtrlFeedLines(context.getState(), 0);
		context.getObject().ASCII_PrintString(context.getState(), 0, 0, 0, 0, 0, "\n", "gb2312");
		context.getObject().ASCII_PrintString(context.getState(), 0, 0, 0, 0, 0, "\n", "gb2312");

		context.getObject().ASCII_CtrlReset(context.getState());//���ø�ʽ��λ��
		context.getObject().ASCII_PrintString(context.getState(), 0, 0, 0, 0, 0, "\r\n", "gb2312");
		context.getObject().ASCII_PrintString(context.getState(), 10, 0, 1, 0, 0, spaceStr + "��Ʒ����: " + info.getName() + "\n", "gb2312");
		context.getObject().ASCII_PrintString(context.getState(), 0, 0, 0, 0, 0, "\r\n", "gb2312");
		context.getObject().ASCII_PrintString(context.getState(), 10, 0, 1, 0, 0, spaceStr + "��ӡʱ��: " + info.getTime() + "\n", "gb2312");
		context.getObject().ASCII_PrintString(context.getState(), 0, 0, 0, 0, 0, "\r\n", "gb2312");
		//		context.getObject().ASCII_PrintString(context.getState(), 0, 0, 0, 0, 0, "\r\n", "gb2312");
		//		context.getObject().ASCII_PrintString(context.getState(), 0, 0, 0, 0, 0, "\r\n", "gb2312");

		context.getObject().ASCII_CtrlReset(context.getState());
		context.getObject().ASCII_CtrlAlignType(context.getState(), AlignType.AT_CENTER.getValue());//���������ʾ

		context.getObject().ASCII_Print1DBarcode(
				context.getState(),
				BarcodeType.BT_CODE39.getValue(),
				3,
				72,
				0, info.getBillcode());

		context.getObject().ASCII_CtrlReset(context.getState());
		context.getObject().ASCII_CtrlAlignType(context.getState(), AlignType.AT_CENTER.getValue());//���������ʾ
		context.getObject().ASCII_PrintString(context.getState(), 0, 0, 1, 0, 0, info.getBillcode(), "gb2312");

		context.getObject().ASCII_PrintString(context.getState(), 0, 0, 0, 0, 0, "\n", "gb2312");
		context.getObject().ASCII_PrintString(context.getState(), 0, 0, 0, 0, 0, "\n", "gb2312");
		context.getObject().ASCII_CtrlFeedLines(context.getState(), 1);

		context.getObject().CON_PageEnd(context.getState(), context.getPrintway());
	}
}
