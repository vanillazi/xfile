package cn.vanillazi.tool.command.internal.jiaoxi51.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Response{

	@JsonProperty("data")
	private List<DataItem> data;

	public List<DataItem> getData(){
		return data;
	}
}