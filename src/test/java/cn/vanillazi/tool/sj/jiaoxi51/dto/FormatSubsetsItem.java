package cn.vanillazi.tool.sj.jiaoxi51.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FormatSubsetsItem{

    @JsonProperty("remains_files_count")
    private Integer remainsFilesCount;

    @JsonProperty("can_view_file_count")
    private Integer canViewFileCount;

    @JsonProperty("item_id")
    private Integer itemId;

    @JsonProperty("file_type")
    private String fileType;

    @JsonProperty("tag_name")
    private String tagName;

    @JsonProperty("preview_file_type")
    private String previewFileType;

    @JsonProperty("total_files_count")
    private Integer totalFilesCount;

    @JsonProperty("tag_id")
    private Integer tagId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("preview_files")
    private List<PreviewFilesItem> previewFiles;

    public void setRemainsFilesCount(Integer remainsFilesCount){
        this.remainsFilesCount = remainsFilesCount;
    }

    public Integer getRemainsFilesCount(){
        return remainsFilesCount;
    }

    public void setCanViewFileCount(Integer canViewFileCount){
        this.canViewFileCount = canViewFileCount;
    }

    public Integer getCanViewFileCount(){
        return canViewFileCount;
    }

    public void setItemId(Integer itemId){
        this.itemId = itemId;
    }

    public Integer getItemId(){
        return itemId;
    }

    public void setFileType(String fileType){
        this.fileType = fileType;
    }

    public String getFileType(){
        return fileType;
    }

    public void setTagName(String tagName){
        this.tagName = tagName;
    }

    public String getTagName(){
        return tagName;
    }

    public void setPreviewFileType(String previewFileType){
        this.previewFileType = previewFileType;
    }

    public String getPreviewFileType(){
        return previewFileType;
    }

    public void setTotalFilesCount(Integer totalFilesCount){
        this.totalFilesCount = totalFilesCount;
    }

    public Integer getTotalFilesCount(){
        return totalFilesCount;
    }

    public void setTagId(Integer tagId){
        this.tagId = tagId;
    }

    public Integer getTagId(){
        return tagId;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setPreviewFiles(List<PreviewFilesItem> previewFiles){
        this.previewFiles = previewFiles;
    }

    public List<PreviewFilesItem> getPreviewFiles(){
        return previewFiles;
    }

    @Override
     public String toString(){
        return 
            "FormatSubsetsItem{" + 
            "remains_files_count = '" + remainsFilesCount + '\'' + 
            ",can_view_file_count = '" + canViewFileCount + '\'' + 
            ",item_id = '" + itemId + '\'' + 
            ",file_type = '" + fileType + '\'' + 
            ",tag_name = '" + tagName + '\'' + 
            ",preview_file_type = '" + previewFileType + '\'' + 
            ",total_files_count = '" + totalFilesCount + '\'' + 
            ",tag_id = '" + tagId + '\'' + 
            ",title = '" + title + '\'' + 
            ",preview_files = '" + previewFiles + '\'' + 
            "}";
        }
}