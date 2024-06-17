package com.plf.solr;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import com.plf.fastjson.FastJsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SolrExample {

	private final static String SOLR_URL = "http://localhost:8983/solr/test_solr2";
	
	HttpSolrClient solr = null;
	 
	@BeforeEach
	public void createSolrServer() {
	    solr = new HttpSolrClient.Builder(SOLR_URL)
	    		.withConnectionTimeout(10000)
	    		.withSocketTimeout(60000)
	    		.build();
	}
	
	@Test
	public void addDoc() throws SolrServerException, IOException {
	    SolrInputDocument document = new SolrInputDocument();
	    document.addField("workid", "20190730A82");
	    document.addField("position", "前端工程师");
	    document.addField("salary", 8000);
	    document.addField("description", "我热爱前端");
	    solr.add(document);
	    solr.commit();
	    solr.close();
	    System.out.println("添加成功");
	}
	
	@Test
	public void deleteDocById() throws SolrServerException, IOException{
	    //server.deleteById("39b070b4-c1f6-4f2b-899c-b9f8916ebecc");
		solr.deleteByQuery("id:*");
		solr.commit();
		solr.close();
	}
	
	@Test
	public void querySolr() throws Exception {

	    SolrQuery query = new SolrQuery();

	    //下面设置solr查询参数

	    //query.set("q", "*:*");// 参数q  查询所有   
	    //query.set("q", "position:*工程*");//模糊查询

	    //参数fq, 给query增加过滤查询条件 
	    //query.addFacetQuery("salary:[6000 TO 9000]");
	    //query.addFilterQuery("position:数据库*"); //

	    //参数df,给query设置默认搜索域，从哪个字段上查找
	    query.set("df", "position"); 

	    //参数sort,设置返回结果的排序规则
	    query.setSort("salary",SolrQuery.ORDER.desc);

	    //设置分页参数
	    query.setStart(0);
	    query.setRows(10);

	    //设置高亮显示以及结果的样式
	    query.setHighlight(true);
	    query.addHighlightField("salary");  
	    query.setHighlightSimplePre("<font color='red'>");  
	    query.setHighlightSimplePost("</font>"); 
	
	    //执行查询
	    QueryResponse response = solr.query(query);

	    //获取实体对象形式
	    List<Worker> worker = response.getBeans(Worker.class);

		worker.forEach(System.out::println);

		//获取返回结果
	    SolrDocumentList resultList = response.getResults();
	    
	    System.out.println(FastJsonUtils.toJsonString(resultList));
	}
}
