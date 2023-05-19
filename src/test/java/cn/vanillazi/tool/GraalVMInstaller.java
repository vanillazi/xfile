package cn.vanillazi.tool;

import com.sun.jna.Platform;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GraalVMInstaller {


    public static final String OS;
    static {
        if(Platform.isWindows()){
            OS="windows";
        }else if(Platform.isLinux()){
            OS="linux";
        }else{
            OS="darwin";
        }
    }

    public static final String HOME="graalvm-ce-java";
    public static final String CACHE=".tmp";
    public static final String BACKUP="backup";

    public static final String BACKUP_FILE_FORMAT=HOME+"-%s-%s";


    public static final Pattern pattern= Pattern.compile("^(.*)-(java\\d+)\\-([a-z]+)\\-([a-zA-Z0-9]+)\\-(\\d+\\.\\d+\\.\\d+)\\..+$");

    record Component(String name, String jdkVersion, String os, String arch, String version,String url, long size, LocalDateTime releaseDate,String filename){}

    public static void main(String[] args) throws IOException {
        var url="https://mirrors.nju.edu.cn/github-release/graalvm/graalvm-ce-builds/LatestRelease/";
        var doc= Jsoup.parse(new URL(url), 3000);
        var list=doc.getElementById("list");
        var items=list.getElementsByTag("tr");
        var format= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        var cs=items.stream().skip(2).map(item->{
            var tds=item.getElementsByTag("td");
            var nameTd=tds.get(0);
            var aName=nameTd.getElementsByTag("a").get(0);
            var mr=pattern.matcher(nameTd.text());
            if(!mr.matches()){
                return null;
            }
            var cUrl=aName.absUrl("href");
            var sizeTd=tds.get(1);
            var size=Long.valueOf(sizeTd.text().trim());
            var date=tds.get(2);
            LocalDateTime releaseDate=LocalDateTime.parse(date.text().trim(),format);
            var c=new Component(mr.group(1),mr.group(2),mr.group(3),mr.group(4),mr.group(5),cUrl,size,releaseDate, nameTd.text().trim());
            return c;
        }).collect(Collectors.toList());

        var arch=System.getProperty("os.arch");
        var target= Path.of("target","test");
        FileUtils.forceMkdir(target.toFile());
        var home=target.resolve(HOME);
        var cache=target.resolve(CACHE);
        FileUtils.forceMkdir(home.toFile());
        FileUtils.forceMkdir(cache.toFile());

        if(FileUtils.isEmptyDirectory(home.toFile())){
            cs.stream()
                    .filter(c->c.arch.equals(arch) && c.os.equals(OS) && c.jdkVersion.equals("java17"))
                    .forEach(c->{
                        download(c.url,cache.resolve(c.filename));
                        System.out.println(c.url);
                    });

        }else{
            var versionInfo=extractVersion(HOME);

        }
    }

    private static void download(String url, Path resolve) {
        if(resolve.toFile().exists()){
            return;
        }
        var client=HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(3))
                .build();
        var req= HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        try {
            client.send(req,
                    HttpResponse.BodyHandlers.ofFileDownload(
                            resolve.getParent(),
                            StandardOpenOption.CREATE_NEW));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    record InstallInfo(String jdkVersion,String version){}
    private static InstallInfo extractVersion(String home) {
        Process p= null;
        try {
            p = new ProcessBuilder(home+ File.separator+"bin"+File.separator+"java","--version")
                    .start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var scanner=new Scanner(p.getInputStream());
        var line=scanner.next();
        var jdkVersion="java"+line.split(" ")[1].split("\\.")[0];
        line=scanner.next();
        var version=line.split(" ")[5];
        return new InstallInfo(jdkVersion,version);
    }
}
