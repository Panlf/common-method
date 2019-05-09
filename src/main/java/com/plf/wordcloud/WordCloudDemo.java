package com.plf.wordcloud;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.tokenizers.ChineseWordTokenizer;
import com.kennycason.kumo.palette.ColorPalette;

/**
 * 使用kumo词云
 * @author plf 2019年5月9日下午4:57:40
 *
 */
public class WordCloudDemo {

	@Test
	public void createWordCloud() throws IOException{
		final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
		//生成多少词 -- 词频
		frequencyAnalyzer.setWordFrequenciesToReturn(600);
		//最小词长度
		frequencyAnalyzer.setMinWordLength(2);
		//引入中文解释器
		frequencyAnalyzer.setWordTokenizer(new ChineseWordTokenizer());

		//导入词文本
		final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(new FileInputStream("src/main/resources/chineseword.txt"));
		
		//设置图片分辨率
		final Dimension dimension = new Dimension(800,400);
		
		
		//CollisionMode.RECTANGLE
		//生成词云对象   设置
		final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
		//设置边界
		wordCloud.setPadding(2);
		//设置字体
		Font font = new Font("STSong-Light", 2, 20);  
		wordCloud.setKumoFont(new KumoFont(font));
		//设置背景颜色 为白色
		wordCloud.setBackgroundColor(new Color(255,255,255));
		//设置背景图层为圆形  
		wordCloud.setBackground(new CircleBackground(255));
		//设置背景图片
		//wordCloud.setBackground(new PixelBoundryBackground("backgrounds/whale_small.png"));
		
		//设置词云显示颜色
		wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
		wordCloud.setFontScalar(new SqrtFontScalar(12, 45));
		
		//生成
		wordCloud.build(wordFrequencies);
		
		//输出图片
		wordCloud.writeToFile("E:/temp/whale_wordcloud_small.png");
	}
}
