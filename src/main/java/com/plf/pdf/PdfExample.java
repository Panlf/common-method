package com.plf.pdf;

import java.io.FileNotFoundException;

import org.junit.Test;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

/**
 * itext的使用
 * 官方案例很多也写得很详细清楚，只要自己运行一遍就能学会。
 * 官方案例:http://developers.itextpdf.com/content/itext-7-jump-start-tutorial/examples
 * 官方文档说明:http://developers.itextpdf.com/content/itext-7-jump-start-tutorial
 * @author plf 2017年8月23日下午9:10:39
 *
 */
public class PdfExample {
	
	@Test
	public void createPdf() throws FileNotFoundException{
	    //初始化PDF Writer
		PdfWriter writer = new PdfWriter("E:\\hello_world.pdf");

	    //初始化PDF document
	    PdfDocument pdf = new PdfDocument(writer);

	    //初始化document
	    Document document = new Document(pdf);

	    //将一个段落添加到document中
	    document.add(new Paragraph("Hello World!"));

	    //关闭document
	    document.close();
	}
}
