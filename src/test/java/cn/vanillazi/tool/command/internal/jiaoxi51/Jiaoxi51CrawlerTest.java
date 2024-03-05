package cn.vanillazi.tool.command.internal.jiaoxi51;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class Jiaoxi51CrawlerTest {

    @Test
    public void download( ) throws IOException, InterruptedException {
        var url= "https://www.51jiaoxi.com/api/document/preview?document_id=5684438&all=1";
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

    @Test
    public void downloadBatchOnPage( ) throws IOException, InterruptedException {

        var doc=Jsoup.parse(URI.create("https://www.51jiaoxi.com/album-63302.html").toURL(),3000);
        var docPreviews=doc.getElementsByClass("doc-preview");
        docPreviews.stream()
                .map(div->div.attr("data-id"))
                .map(id->String.format("https://www.51jiaoxi.com/api/document/preview?document_id=%s&all=1",id))
                .forEach(url->{
                    Jiaoxi51Crawler.download(url, Path.of("target"));
                });
    }


}