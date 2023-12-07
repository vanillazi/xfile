package cn.vanillazi.tool.command.internal.jiaoxi51;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class Jiaoxi51CrawlerTest {

    @Test
    public void download( ) throws IOException, InterruptedException {
        var url= "https://www.51jiaoxi.com/api/document/preview?document_id=13572901&all=1";
        Jiaoxi51Crawler.download(url, Path.of("target"));
    }


    @Test
    public void downloadBatch( ) throws IOException, InterruptedException {

        var urls=Arrays.asList(
                "https://www.51jiaoxi.com/api/document/preview?document_id=12403487&all=1",
                "https://www.51jiaoxi.com/api/document/preview?document_id=12403488&all=1",
                "https://www.51jiaoxi.com/api/document/preview?document_id=12403497&all=1",
                "https://www.51jiaoxi.com/api/document/preview?document_id=12403498&all=1",
                "https://www.51jiaoxi.com/api/document/preview?document_id=12403499&all=1",
                "https://www.51jiaoxi.com/api/document/preview?document_id=12403500&all=1",
                "https://www.51jiaoxi.com/api/document/preview?document_id=12403501&all=1",
                "https://www.51jiaoxi.com/api/document/preview?document_id=12403502&all=1"


        );

        urls.forEach(url->{
            Jiaoxi51Crawler.download(url, Path.of("target"));
        });

    }
}