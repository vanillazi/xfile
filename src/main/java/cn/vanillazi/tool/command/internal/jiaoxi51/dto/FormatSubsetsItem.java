package cn.vanillazi.tool.command.internal.jiaoxi51.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FormatSubsetsItem{

	@JsonProperty("remains_files_count")
	private int remainsFilesCount;

	@JsonProperty("can_view_file_count")
	private int canViewFileCount;

	@JsonProperty("item_id")
	private int itemId;

	@JsonProperty("file_type")
	private String fileType;

	@JsonProperty("tag_name")
	private String tagName;

	@JsonProperty("preview_file_type")
	private String previewFileType;

	@JsonProperty("total_files_count")
	private int totalFilesCount;

	@JsonProperty("tag_id")
	private int tagId;

	@JsonProperty("vga_capture_url")
	private String vgaCaptureUrl;

	@JsonProperty("title")
	private String title;

	@JsonProperty("preview_files")
	private List<PreviewFilesItem> previewFiles;

	public int getRemainsFilesCount(){
		return remainsFilesCount;
	}

	public int getCanViewFileCount(){
		return canViewFileCount;
	}

	public int getItemId(){
		return itemId;
	}

	public String getFileType(){
		return fileType;
	}

	public String getTagName(){
		return tagName;
	}

	public String getPreviewFileType(){
		return previewFileType;
	}

	public int getTotalFilesCount(){
		return totalFilesCount;
	}

	public int getTagId(){
		return tagId;
	}

	public String getVgaCaptureUrl(){
		return vgaCaptureUrl;
	}

	public String getTitle(){
		return title;
	}

	public List<PreviewFilesItem> getPreviewFiles(){
		return previewFiles;
	}
}