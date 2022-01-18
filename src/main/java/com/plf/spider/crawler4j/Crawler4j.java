package com.plf.spider.crawler4j;

import java.util.Set;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * Crawler4j官方案例修改
 * @author Panlf 2017年7月3日下午8:44:14
 *
 */
public class Crawler4j extends WebCrawler {
	 private final static Pattern FILTERS = Pattern.compile(".*(\\.(png|jpeg|gif|jpg))$");
	 
	 public boolean shouldVisit(Page referringPage, WebURL url) {
         String href = url.getURL().toLowerCase();
         return !FILTERS.matcher(href).matches()
                && href.startsWith("https://www.2717.com/ent/meinvtupian/");
     }
	 
	 public void visit(Page page) {
         String url = page.getWebURL().getURL();
         System.out.println("URL: " + url);

         if (page.getParseData() instanceof HtmlParseData) {
             HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
             String text = htmlParseData.getText();
             String html = htmlParseData.getHtml();
             Set<WebURL> links = htmlParseData.getOutgoingUrls();

             System.out.println("Text length: " + text.length());
             System.out.println("Html length: " + html.length());
             System.out.println("Number of outgoing links: " + links.size());
         }
    }
}
