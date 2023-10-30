package cn.vanillazi.tool.sj.jiaoxi51.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DocTagsItem{

    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private Integer id;

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

    @Override
     public String toString(){
        return 
            "DocTagsItem{" + 
            "name = '" + name + '\'' + 
            ",id = '" + id + '\'' + 
            "}";
        }
}