package cn.vanillazi.tool.command.internal.jiaoxi51.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PreviewFilesItem{

	@JsonProperty("file_url")
	private String fileUrl;

	@JsonProperty("file_type")
	private String fileType;

	@JsonProperty("guess_type")
	private String guessType;

	@JsonProperty("url")
	private String url;

	public String getFileUrl(){
		return fileUrl;
	}

	public String getFileType(){
		return fileType;
	}

	public String getGuessType(){
		return guessType;
	}

	public String getUrl(){
		return url;
	}
}