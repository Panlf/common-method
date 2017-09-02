package com.plf.word;

import java.util.List;

import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;
import org.junit.Test;

/**
 * word分词器的简单实例
 * 目前很多不是很了解 --慢慢研究
 *  https://github.com/ysc/word(文档网址)
 * @author plf 2017年6月14日下午9:41:54
 *
 */
public class WordExample {

	@Test
	//文本分词
	public void TestSegmenterWord(){
		String text="word分词是一个Java实现的分布式的中文分词组件，提供了多种基于词典的分词算法，并利用ngram模型来消除歧义。"
				+ "能准确识别英文、数字，以及日期、时间等数量词，能识别人名、地名、组织机构名等未登录词。"
				+ "能通过自定义配置文件来改变组件行为，能自定义用户词库、自动检测词库变化、支持大规模分布式环境，能灵活指定多种分词算法，"
				+ "能使用refine功能灵活控制分词结果，还能使用词频统计、词性标注、同义标注、反义标注、拼音标注等功能。提供了10种分词算法，还提供了10种文本相似度算法，同时还无缝和Lucene、Solr、ElasticSearch、Luke集成。";
		List<Word> words = WordSegmenter.seg(text);//移除停用词
		System.out.println(words);
		words = WordSegmenter.segWithStopWords(text);//未移除停用词
		System.out.println(words);
	}
}