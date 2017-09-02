package com.plf.excel;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 实现将Excel中的字段映射为sql语句(操作.xlsx)
 * @author plf 2017年8月29日下午10:03:31
 *
 */
public class ImportXSSFExcel {

	public static void main(String[] args) {
		List<String> list=readExcel("E:\\test.xlsx","test",3);
		if(list!=null){
			for (String string : list) {
				System.out.println(string);
			}
		}
	}
	public static List<String> readExcel(String path,String table,int num){
		try {
			FileInputStream in=new FileInputStream(new File(path));
			@SuppressWarnings("resource")
			XSSFWorkbook wb=new XSSFWorkbook(in);
			//获取第一个sheet
			XSSFSheet st=wb.getSheetAt(0);
			//获取总行数
			int rowNum=st.getLastRowNum();
			List<String[]> dataList=new ArrayList<String[]>();
			String[] str=new String[num];
			//String column;
			for(int i=0;i<=rowNum;i++){
				XSSFRow hr=st.getRow(i);
				if(i==0){
					for(int j=0;j<num;j++){
						str[j]=hr.getCell(j).getStringCellValue();
					}					
				}else if(i>=1){
					String[] strdata=new String[num];
					for(int k=0;k<num;k++){
						strdata[k]=hr.getCell(k).getStringCellValue();
					}	
					dataList.add(strdata);
				}
			}
			
		List<String> result=new ArrayList<String>();
		if(dataList.size()>0 && dataList!=null){
			for (String[] data : dataList) {
				StringBuilder insert=new StringBuilder();
				insert.append("insert table(").append(str[0]);
				for(int m=1;m<num;m++){
					insert.append(",").append(str[m]);
				}
				insert.append(") values('").append(data[0]);
				for(int m=1;m<num;m++){
					insert.append("','").append(data[m]);
				}
				insert.append("');");
				result.add(insert.toString());
			}
			return result;
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}