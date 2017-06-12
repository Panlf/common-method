package com.plf.excel;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

/**
 * Java POI技术操作Excel
 * @author plf 2017年6月12日下午9:31:21
 *
 */
public class ExcelExample {
	
	@Test
	public void ExportExcel(){
		//声明一个工作部
		@SuppressWarnings("resource")
		HSSFWorkbook wb=new HSSFWorkbook();
		//声明一个sheet并命名
		HSSFSheet hs=wb.createSheet("技能信息表");
		//设置表格默认宽度
		hs.setDefaultColumnWidth(15);
		//生成格式
		HSSFCellStyle style=wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		style.setFillBackgroundColor((short) 13);//设置背景色
		//生成字体格式
		HSSFFont font = wb.createFont(); 
		font.setBold(true);
		font.setFontHeight((short)15);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		//应用字体
		style.setFont(font);
		
		
		//生成格式
		HSSFCellStyle anotherstyle=wb.createCellStyle();
		anotherstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		anotherstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		anotherstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		anotherstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		anotherstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		//生成字体格式
		HSSFFont anotherfont = wb.createFont(); 
		anotherfont.setFontHeight((short)12);
		//应用字体
		anotherstyle.setFont(anotherfont);
		
		
		//设置第一行
		HSSFRow row = hs.createRow(0);
		HSSFCell cell=row.createCell(3);
		cell.setCellValue("技能信息表");
		cell.setCellStyle(style);
		
		//设置第二行
		row = hs.createRow(1);
		cell=row.createCell(0);
		cell.setCellValue("公司名");
		cell.setCellStyle(anotherstyle);
		cell=row.createCell(1);
		cell.setCellValue("年限");
		cell.setCellStyle(anotherstyle);
		cell=row.createCell(2);
		cell.setCellValue("信息");
		cell.setCellStyle(anotherstyle);
		
		List<SkillInfo> data=new ArrayList<SkillInfo>();
		data.add(new SkillInfo("xx1","1年","基础"));
		data.add(new SkillInfo("xx2","3年","框架"));
		data.add(new SkillInfo("xx3","5年","架构"));
		
		for (int i=0;i<data.size();i++) {
			row=hs.createRow(i+2);
			row.createCell(0).setCellValue(data.get(i).getCompany());
			row.createCell(1).setCellValue(data.get(i).getYear());
			row.createCell(2).setCellValue(data.get(i).getInfo());
		}
		
		try {
			FileOutputStream out = new FileOutputStream("E://技能信息表.xls");
			wb.write(out);
	        out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
}
