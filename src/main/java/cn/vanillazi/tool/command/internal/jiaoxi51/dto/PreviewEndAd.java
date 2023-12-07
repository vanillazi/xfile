package cn.vanillazi.tool.command.internal.jiaoxi51.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PreviewEndAd{

	@JsonProperty("bg_color")
	private String bgColor;

	@JsonProperty("cdn_url")
	private String cdnUrl;

	@JsonProperty("image_url")
	private String imageUrl;

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private int id;

	@JsonProperty("url")
	private String url;

	@JsonProperty("redirec_url")
	private String redirecUrl;

	public String getBgColor(){
		return bgColor;
	}

	public String getCdnUrl(){
		return cdnUrl;
	}

	public String getImageUrl(){
		return imageUrl;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public String getUrl(){
		return url;
	}

	public String getRedirecUrl(){
		return redirecUrl;
	}
}