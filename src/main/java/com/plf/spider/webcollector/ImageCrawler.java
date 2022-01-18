package com.plf.spider.webcollector;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.select.Elements;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;

public class ImageCrawler extends BreadthCrawler {

	static List<Elements> imgList = new ArrayList<>();

	public ImageCrawler(String crawlPath, boolean autoParse) {
		super(crawlPath, autoParse);
		this.addSeedAndReturn("https://tieba.baidu.com/p/5747407776").type("list");
		for (int pageIndex = 1; pageIndex <= 8; pageIndex++) {
			String seedUrl = String.format("https://tieba.baidu.com/p/5747407776?pn=%d", pageIndex);
			this.addSeed(seedUrl, "list");
		}

		setThreads(50);
		getConf().setTopN(100);
	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		String url = page.url();
		// 构造函数中的URL
		System.out.println("url:" + url);
		Elements img = null;
		if (page.matchType("list")) {
			// 一页是当作整个img，比如一页有很多img，所有img都在同一个变量中，所以使用attr只能读取一个
			img = page.select("img[class=BDE_Image]");
			System.out.println("img:" + img);
			imgList.add(img);
		}

	}

	public static void main(String[] args) throws Exception {
		/*
		 * ImageCrawler crawler = new ImageCrawler("crawl", true); crawler.start(2);
		 * System.out.println("imgList:" + imgList); List<String> list =
		 * imgToSrc(imgList); for (String s : list) { downPic(s); }
		 */

		List<String> list = readImgListTxt();
		System.out.println(list);
		List<String> result = getImgSrc(list);
		for (String s : result) {
			downPic(s);
		}
	}

	public static List<String> imgToSrc(List<Elements> imgList) {
		List<String> arrayList = new ArrayList<>();
		for (Elements e : imgList) {
			arrayList.add(e.attr("src"));
		}
		System.out.println("arrayList:" + arrayList);
		return arrayList;
	}

	public static List<String> readImgListTxt() {
		List<String> arrayList = new ArrayList<>();
		URI url;
		try {
			url = ImageCrawler.class.getClassLoader().getResource("imglist.txt").toURI();
			arrayList = Files.readAllLines(Paths.get(url));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrayList;
	}

	
	public static List<String> getImgSrc(List<String> arrayList) {
		
		/**
		 * <img[^>]*src\s*=\s*([\'"]?)([^\'">]*)\1(>)    获取单个img
		 * 
		 *  	(?<=src=\").*\\.jpg  然后再使用下面的获取src
		 * 
		 */
		
		List<String> result = new ArrayList<>();
		//只适合单条匹配
		String pattern = "(?<=src=\").*\\.jpg";
		Pattern p = Pattern.compile(pattern);
		for (String s : arrayList) {
			Matcher m = p.matcher(s);
			if (m.find()) {
				result.add(m.group());
			}
		}
		System.out.println(result);
		return result;
	}

	/**
	 * 根据URL下载
	 * @param path
	 */
	public static void downPic(String path) {
		if (path == null || path.trim().length() == 0) {
			return;
		}
		try {
			URL url = new URL(path);
			String filename = UUID.randomUUID().toString().substring(0, 16);
			URLConnection uc = url.openConnection();
			// uc.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;
			// Windows NT; DigExt)");
			InputStream is = uc.getInputStream();
			byte[] bs = new byte[1024];
			FileOutputStream out = new FileOutputStream("D:\\TempData\\img\\" + filename + ".jpg");
			int i = 0;
			while ((i = is.read(bs)) != -1) {
				out.write(bs, 0, i);
			}
			is.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
