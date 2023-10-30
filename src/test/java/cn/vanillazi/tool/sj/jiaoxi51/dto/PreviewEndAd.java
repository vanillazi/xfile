package cn.vanillazi.tool.sj.jiaoxi51.dto;

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
    private Integer id;

    @JsonProperty("url")
    private String url;

    @JsonProperty("redirec_url")
    private String redirecUrl;

    public void setBgColor(String bgColor){
        this.bgColor = bgColor;
    }

    public String getBgColor(){
        return bgColor;
    }

    public void setCdnUrl(String cdnUrl){
        this.cdnUrl = cdnUrl;
    }

    public String getCdnUrl(){
        return cdnUrl;
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }

    public void setRedirecUrl(String redirecUrl){
        this.redirecUrl = redirecUrl;
    }

    public String getRedirecUrl(){
        return redirecUrl;
    }

    @Override
     public String toString(){
        return 
            "PreviewEndAd{" + 
            "bg_color = '" + bgColor + '\'' + 
            ",cdn_url = '" + cdnUrl + '\'' + 
            ",image_url = '" + imageUrl + '\'' + 
            ",name = '" + name + '\'' + 
            ",id = '" + id + '\'' + 
            ",url = '" + url + '\'' + 
            ",redirec_url = '" + redirecUrl + '\'' + 
            "}";
        }
}