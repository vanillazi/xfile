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

    public void setFileUrl(String fileUrl){
        this.fileUrl = fileUrl;
    }

    public String getFileUrl(){
        return fileUrl;
    }

    public void setFileType(String fileType){
        this.fileType = fileType;
    }

    public String getFileType(){
        return fileType;
    }

    public void setGuessType(String guessType){
        this.guessType = guessType;
    }

    public String getGuessType(){
        return guessType;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }

    @Override
     public String toString(){
        return 
            "PreviewFilesItem{" + 
            "file_url = '" + fileUrl + '\'' + 
            ",file_type = '" + fileType + '\'' + 
            ",guess_type = '" + guessType + '\'' + 
            ",url = '" + url + '\'' + 
            "}";
        }
}