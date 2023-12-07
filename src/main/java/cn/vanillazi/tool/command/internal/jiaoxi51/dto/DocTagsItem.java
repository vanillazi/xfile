package cn.vanillazi.tool.command.internal.jiaoxi51.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DocTagsItem{

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private int id;

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}
}