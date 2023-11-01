package cn.vanillazi.tool.command.internal.jiaoxi51.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DataItem{

    @JsonProperty("subject_id")
    private Integer subjectId;

    @JsonProperty("stage_id")
    private Integer stageId;

    @JsonProperty("doc_tags")
    private List<DocTagsItem> docTags;

    @JsonProperty("document_code")
    private Object documentCode;

    @JsonProperty("document_id")
    private Integer documentId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("is_beike_vip")
    private Boolean isBeikeVip;

    @JsonProperty("preview_end_ad")
    private PreviewEndAd previewEndAd;

    @JsonProperty("is_ppt_document_more")
    private Boolean isPptDocumentMore;

    @JsonProperty("can_view_preview")
    private Boolean canViewPreview;

    @JsonProperty("file_type")
    private String fileType;

    @JsonProperty("format_subsets")
    private List<FormatSubsetsItem> formatSubsets;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("format_subset_count")
    private Integer formatSubsetCount;

    @JsonProperty("preview_top_ad")
    private Object previewTopAd;

    public void setSubjectId(Integer subjectId){
        this.subjectId = subjectId;
    }

    public Integer getSubjectId(){
        return subjectId;
    }

    public void setStageId(Integer stageId){
        this.stageId = stageId;
    }

    public Integer getStageId(){
        return stageId;
    }

    public void setDocTags(List<DocTagsItem> docTags){
        this.docTags = docTags;
    }

    public List<DocTagsItem> getDocTags(){
        return docTags;
    }

    public void setDocumentCode(Object documentCode){
        this.documentCode = documentCode;
    }

    public Object getDocumentCode(){
        return documentCode;
    }

    public void setDocumentId(Integer documentId){
        this.documentId = documentId;
    }

    public Integer getDocumentId(){
        return documentId;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setIsBeikeVip(Boolean isBeikeVip){
        this.isBeikeVip = isBeikeVip;
    }

    public Boolean isIsBeikeVip(){
        return isBeikeVip;
    }

    public void setPreviewEndAd(PreviewEndAd previewEndAd){
        this.previewEndAd = previewEndAd;
    }

    public PreviewEndAd getPreviewEndAd(){
        return previewEndAd;
    }

    public void setIsPptDocumentMore(Boolean isPptDocumentMore){
        this.isPptDocumentMore = isPptDocumentMore;
    }

    public Boolean isIsPptDocumentMore(){
        return isPptDocumentMore;
    }

    public void setCanViewPreview(Boolean canViewPreview){
        this.canViewPreview = canViewPreview;
    }

    public Boolean isCanViewPreview(){
        return canViewPreview;
    }

    public void setFileType(String fileType){
        this.fileType = fileType;
    }

    public String getFileType(){
        return fileType;
    }

    public void setFormatSubsets(List<FormatSubsetsItem> formatSubsets){
        this.formatSubsets = formatSubsets;
    }

    public List<FormatSubsetsItem> getFormatSubsets(){
        return formatSubsets;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    public void setFormatSubsetCount(Integer formatSubsetCount){
        this.formatSubsetCount = formatSubsetCount;
    }

    public Integer getFormatSubsetCount(){
        return formatSubsetCount;
    }

    public void setPreviewTopAd(Object previewTopAd){
        this.previewTopAd = previewTopAd;
    }

    public Object getPreviewTopAd(){
        return previewTopAd;
    }

    @Override
     public String toString(){
        return 
            "DataItem{" + 
            "subject_id = '" + subjectId + '\'' + 
            ",stage_id = '" + stageId + '\'' + 
            ",doc_tags = '" + docTags + '\'' + 
            ",document_code = '" + documentCode + '\'' + 
            ",document_id = '" + documentId + '\'' + 
            ",title = '" + title + '\'' + 
            ",is_beike_vip = '" + isBeikeVip + '\'' + 
            ",preview_end_ad = '" + previewEndAd + '\'' + 
            ",is_ppt_document_more = '" + isPptDocumentMore + '\'' + 
            ",can_view_preview = '" + canViewPreview + '\'' + 
            ",file_type = '" + fileType + '\'' + 
            ",format_subsets = '" + formatSubsets + '\'' + 
            ",id = '" + id + '\'' + 
            ",format_subset_count = '" + formatSubsetCount + '\'' + 
            ",preview_top_ad = '" + previewTopAd + '\'' + 
            "}";
        }
}