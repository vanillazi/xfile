package cn.vanillazi.tool.command.internal.jiaoxi51.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DataItem{

	@JsonProperty("subject_id")
	private int subjectId;

	@JsonProperty("stage_id")
	private int stageId;

	@JsonProperty("doc_tags")
	private List<DocTagsItem> docTags;

	@JsonProperty("document_code")
	private Object documentCode;

	@JsonProperty("document_id")
	private int documentId;

	@JsonProperty("title")
	private String title;

	@JsonProperty("is_beike_vip")
	private boolean isBeikeVip;

	@JsonProperty("preview_end_ad")
	private PreviewEndAd previewEndAd;

	@JsonProperty("is_ppt_document_more")
	private boolean isPptDocumentMore;

	@JsonProperty("can_view_preview")
	private boolean canViewPreview;

	@JsonProperty("file_type")
	private String fileType;

	@JsonProperty("format_subsets")
	private List<FormatSubsetsItem> formatSubsets;

	@JsonProperty("id")
	private int id;

	@JsonProperty("format_subset_count")
	private int formatSubsetCount;

	@JsonProperty("preview_top_ad")
	private Object previewTopAd;

	public int getSubjectId(){
		return subjectId;
	}

	public int getStageId(){
		return stageId;
	}

	public List<DocTagsItem> getDocTags(){
		return docTags;
	}

	public Object getDocumentCode(){
		return documentCode;
	}

	public int getDocumentId(){
		return documentId;
	}

	public String getTitle(){
		return title;
	}

	public boolean isIsBeikeVip(){
		return isBeikeVip;
	}

	public PreviewEndAd getPreviewEndAd(){
		return previewEndAd;
	}

	public boolean isIsPptDocumentMore(){
		return isPptDocumentMore;
	}

	public boolean isCanViewPreview(){
		return canViewPreview;
	}

	public String getFileType(){
		return fileType;
	}

	public List<FormatSubsetsItem> getFormatSubsets(){
		return formatSubsets;
	}

	public int getId(){
		return id;
	}

	public int getFormatSubsetCount(){
		return formatSubsetCount;
	}

	public Object getPreviewTopAd(){
		return previewTopAd;
	}
}