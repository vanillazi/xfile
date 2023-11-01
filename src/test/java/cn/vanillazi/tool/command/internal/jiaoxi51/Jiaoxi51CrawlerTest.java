package cn.vanillazi.tool.command.internal.jiaoxi51;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class Jiaoxi51CrawlerTest {

    @Test
    public void main( ) throws IOException, InterruptedException {
        var url= "https://www.51jiaoxi.com/api/document/preview?document_id=13630275&all=1";
        Jiaoxi51Crawler.download(url, Path.of("target"));
    }
}