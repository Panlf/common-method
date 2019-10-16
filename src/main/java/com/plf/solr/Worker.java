package com.plf.solr;

import org.apache.solr.client.solrj.beans.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Worker{

	
	@Field("workid")
	private String workid;
	
	@Field("position")
	private String position;
	
	@Field("salary")
	private double salary;
	
	@Field("description")
	private double description;
}
