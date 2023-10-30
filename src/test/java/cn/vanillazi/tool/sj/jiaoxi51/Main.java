package cn.vanillazi.tool.sj.jiaoxi51;

import cn.vanillazi.tool.sj.jiaoxi51.dto.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        var urls= Arrays.asList(
                "https://www.51jiaoxi.com/api/document/preview?document_id=13630275&all=1",
                "https://www.51jiaoxi.com/api/document/preview?document_id=13630276&all=1",
                "https://www.51jiaoxi.com/api/document/preview?document_id=13630277&all=1",
                "https://www.51jiaoxi.com/api/document/preview?document_id=13630278&all=1",
                "https://www.51jiaoxi.com/api/document/preview?document_id=13630279&all=1");

        urls.forEach(url->{
            download(url);
        });

    }

    private static void download(String url) {
        for(int i=0;i<3;i++) {
            try {
                doDownload(url);
                break;
            } catch (Throwable e) {
                e.printStackTrace();
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private static void doDownload(String url) throws IOException, InterruptedException {


        var hc= HttpClient.newBuilder().build();
        var resp=hc.send(HttpRequest.newBuilder().GET().uri(URI.create(url)).build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        System.out.println(resp.body());
        var gson=new Gson();
        var om=new ObjectMapper();
        var result=om.reader().readValue(resp.body(), Response.class);
        var doc=result.getData().get(0).getFormatSubsets().get(0);
        var files=doc.getPreviewFiles();
        var title=doc.getTitle();
        var path= Path.of("target",title);
        if(path.toFile().exists()){
            FileUtils.deleteDirectory(path.toFile());
        }
        path.toFile().mkdirs();
        files.forEach(f->{
            var fUrl=f.getUrl();
            var targetName=FilenameUtils.getName(fUrl);
            var targetPath=path.resolve(targetName);
            try {
                var re=hc.send(HttpRequest.newBuilder().GET().uri(URI.create("https:"+fUrl)).build(), HttpResponse.BodyHandlers.ofFile(targetPath));
                System.out.println(re.statusCode());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
