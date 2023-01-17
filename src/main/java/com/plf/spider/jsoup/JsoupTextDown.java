package com.plf.spider.jsoup;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

//下载http://www.biqudu.com/
public class JsoupTextDown {

	public static void main(String[] args) throws Exception {
		//Document doc = Jsoup.connect("http://www.biqudu.com/47_47983/").get();
		Document doc = Jsoup.connect("https://www.biqudu.com/3_3607/").get();
		if(doc!=null){
			Elements links=doc.getElementById("list").select("dd>a[href]");
			for (Element link : links) {
				Thread.sleep(2000);
				//String path="http://www.biqudu.com"+link.attr("href");
				String path="https://www.biqudu.com"+link.attr("href");
				String title=link.text();
				System.out.println(path+"---"+title);
//				if(NovelService.selectNovelURl(path)){
//					continue;
//				}
				Map<String,String> content=downText(path);
				//NovelService.insertNovel(content.get("title"),path,content.get("text"));
				System.out.println(content.get("title"));
			}
		}
	}
	
	//获取题目和正文
	public static Map<String,String> downText(String path){
		Map<String,String> map=new Hashtable<String,String>();
		map.put("title", "");
		map.put("text","");
		try{
			Document doc = Jsoup.connect(path)
					.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:49.0) Gecko/20100101 Firefox/49.0")
					.header("Connection", "close")//如果是这种方式，这里务必带上
					.timeout(50000)//超时时间
					.get();
			String text="",title="";
			if(doc!=null){
				title=doc.select("h1").text().toString();
				title=replace(StringEscapeUtils.escapeHtml(title));
				text=doc.getElementById("content").text().toString();
				text=replace(StringEscapeUtils.escapeHtml(text));
				//System.out.println(text.substring(1));
			}
			map.put("title", title);
			map.put("text",text.substring(1));
			return map;	
		}catch(Exception e){
			return map;
		}
	}
	
	 //替换js语句
	//<script>[\s\S]+?</script> 
	//\\s+去除空格   \\s{2} 去除2个空格
	//写入txt文件
//	public static void writeText(List<Novel> novelList,String textName){
//		try {
//			FileWriter fw=new FileWriter("E:\\"+textName,true);
//			BufferedWriter bufw=new BufferedWriter(fw);
//			for (Novel novel : novelList) {
//				bufw.write(replace(novel.getTitle()));
//				bufw.newLine();
//				String text = replace(novel.getText());
//				//Pattern r = Pattern.compile("<script>[\\s\\S]+?</script>");
//				Pattern r = Pattern.compile("\\s+");
//		        Matcher m = r.matcher(text);
//				bufw.write(m.replaceAll("\r\n"));
//				bufw.newLine();
//				bufw.flush();
//			}
//			bufw.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	public static String replace(String str){
		str = str.replace("&ldquo;", "“");
		str = str.replace("ldquo;", "“");
        str = str.replace("&rdquo;", "”");
        str = str.replace("rdquo;", "”");
        str = str.replace("&nbsp;", " ");
        str = str.replace("hellip;", "…");
        str = str.replace("&amp;", "");
        str = str.replace("&", "");
        str = str.replace("&#39;", "'");
        str = str.replace("&lsquo;", "‘");
        str = str.replace("lsquo;", "‘");
        str = str.replace("&rsquo;", "’");
        str = str.replace("rsquo;", "’");
        str = str.replace("&mdash;", "—");
        str = str.replace("mdash;", "—");
        str = str.replace("&ndash;", "–");
        str = str.replace("<br />", "\r\n");
		return str;
	}

	@Test
	public void downText(){
		String path="http://www.silukeke.com/0/9/9601/4939784.html";
		try{
			Document doc = Jsoup.connect(path)
					.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:49.0) Gecko/20100101 Firefox/49.0")
					.header("Connection", "close")//如果是这种方式，这里务必带上
					.timeout(50000)//超时时间
					.get();
			String text="",title="";
			if(doc!=null){
				title=doc.select("h1").text().toString();
				//title=replace(StringEscapeUtils.escapeHtml4(title));
				text=doc.getElementById("content").html().toString();
				//text=replace(StringEscapeUtils.escapeHtml4(text));
				//System.out.println(text.substring(1));
				FileWriter fw=new FileWriter("E:\\aa.txt",true);
				BufferedWriter bufw=new BufferedWriter(fw);
			
				text=replace(text);
				text=text.replace("<br />", "\r\n");
				bufw.write(title);
				bufw.newLine();
				Pattern r = Pattern.compile("<script>[\\s\\S]+?</script>");
		        Matcher m = r.matcher(text);
				bufw.write(m.replaceAll(""));
				bufw.newLine();
				bufw.flush();
				
				bufw.close();
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
