package cn.vanillazi.tool.command.internal.jiaoxi51;

import cn.vanillazi.tool.command.internal.jiaoxi51.dto.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.VisibleForTesting;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import java.util.concurrent.TimeUnit;

@Slf4j
public class Jiaoxi51Crawler {

    public static boolean download(String url, Path output) {
        Throwable ex=null;
        for(int i=0;i<3;i++) {
            try {
                if(doDownload(url,output)) {
                    ex=null;
                    break;
                }
            } catch (Throwable e) {
                ex=e;
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if(ex!=null){
            log.error("download doc from {} failed",url,ex);
            return false;
        }else{
            return true;
        }

    }

    public static HttpRequest.Builder baseBuilder(){

        return HttpRequest.newBuilder().header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36 Edg/118.0.2088.76");
    }

    private static boolean doDownload(String url, Path output) throws IOException, InterruptedException {

        var hc= HttpClient.newBuilder()
                .build();
        var resp=hc.send(baseBuilder().GET().uri(URI.create(url)).build(),
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if(resp.statusCode()!=200){
            return false;
        }
        var om=new ObjectMapper();
        var result=om.reader().readValue(resp.body(), Response.class);
        var subsets=result.getData().get(0).getFormatSubsets();
        for(int i=0;i<subsets.size();i++) {
            var doc =subsets.get(i);
            var files = doc.getPreviewFiles();
            var title = doc.getTitle();
            var path = output.resolve(title);
            if (path.toFile().exists()) {
                FileUtils.deleteDirectory(path.toFile());
            }
            path.toFile().mkdirs();
            var targetNamePrefix=i+"";
            files.forEach(f -> {
                var fUrl = f.getUrl();
                var targetName = targetNamePrefix+FilenameUtils.getName(fUrl);
                var targetPath = path.resolve(targetName);
                try {
                    var imageUrl = "https:" + fUrl;
                    var re = hc.send(baseBuilder().GET().uri(URI.create(imageUrl)).build(),
                            HttpResponse.BodyHandlers.ofFile(targetPath));
                    if (re.statusCode() != 200) {
                        throw new RuntimeException("download image from " + imageUrl + " failed!");
                    }
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return true;
    }
}
