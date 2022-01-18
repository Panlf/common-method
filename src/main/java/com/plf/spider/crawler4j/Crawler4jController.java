package com.plf.spider.crawler4j;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Crawler4jController {

	public static void main(String[] args) throws Exception {
		String crawlStorageFolder = "E://temp//beauty//";
        int numberOfCrawlers = 7;
        
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        
        controller.addSeed("https://www.2717.com/ent/meinvtupian/");
        controller.addSeed("https://www.2717.com/tag/441.html");
        
        controller.start(Crawler4j.class, numberOfCrawlers);
	}

}
