package cn.vanillazi.tool;

import com.sun.jna.Platform;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

    record Component(String name, String jdkVersion, String os, String arch, String version,String url, long size, LocalDateTime releaseDate,String filename){

        public static final String DIGEST_SUFFIX=".sha256";
        public boolean isDigestFile(){
            return filename.endsWith(DIGEST_SUFFIX);
        }

        public boolean isBasePackage(){
            return !isDigestFile() && "graalvm-ce".equals(name);
        }
        public String getDigestFilename(){
            if(isDigestFile()){
                return null;
            }else{
                return filename+DIGEST_SUFFIX;
            }
        }



    }

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
        var components=cs.stream()
                .filter(c->c.arch.equals(arch) && c.os.equals(OS) && c.jdkVersion.equals("java17"))
                .collect(Collectors.toList());
        if(FileUtils.isEmptyDirectory(home.toFile())){
            components.forEach(c->{
                download(c.url,cache.resolve(c.filename));
                System.out.println(c.url);
            });
            if(validatePackages(components,cache)){
                install(components,cache,home);
            }
        }else{
            var versionInfo=extractVersion(home);
            var currentVersion=Version.from(versionInfo.version);
            var lastestVersion=Version.from(components.get(0).version);
            if(currentVersion.isLessThen(lastestVersion)){
                FileUtils.moveDirectoryToDirectory(home.toFile(),home.toFile().getParentFile(),true);
                install(components,cache,home);
            }
        }
    }

    private static void install(List<Component> cs, Path cache, Path home) {
        var basePackage=cs.stream()
                .filter(c->c.isBasePackage())
                .findFirst();
        if(basePackage.isPresent()){
            var asf=new ArchiveStreamFactory();
            try(var in=asf.createArchiveInputStream(new BufferedInputStream(Files.newInputStream(cache.resolve(basePackage.get().filename))))){
                ArchiveEntry entry=null;
                while ((entry=in.getNextEntry())!=null){
                    if(entry.isDirectory()){
                        continue;
                    }
                    var name=entry.getName();
                    System.out.println(name);
                    var size=entry.getSize();
                    var index=name.indexOf('/');
                    File target;
                    if(index<0){
                        target=new File(home.toFile(),name);
                    }else{
                        target=new File(home.toFile(),name.substring(index+1));
                    }
                    FileUtils.forceMkdirParent(target);
                    var options=target.exists()?StandardOpenOption.TRUNCATE_EXISTING:StandardOpenOption.CREATE_NEW;
                    try(var out=Files.newOutputStream(target.toPath(),options)) {
                        if(size>0) {
                            IOUtils.copy(in, out, (int) size);
                        }
                        out.flush();
                    }catch (Throwable e){
                        e.printStackTrace();
                    }
                }
            }catch (Throwable e){
                e.printStackTrace();
            }

            cs.stream()
                    .filter(c->!c.isBasePackage())
                    .filter(c->!c.isDigestFile())
                    .forEach(c->{
                        installComponent(c, cache, home);
                    });

        }
    }

    private static void installComponent(Component c, Path cache, Path home) {
        Process p= null;
        try {
            var gu="gu";
            if(Platform.isWindows()){
                gu=gu+".cmd";
            }
            p = new ProcessBuilder(home.resolve("bin"+File.separator+gu).toFile().getAbsolutePath(),"install","-L",cache.resolve(c.filename).toFile().getAbsolutePath())
                    .start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var scanner=new Scanner(p.getInputStream());
        while (scanner.hasNextLine()){
            System.out.println(scanner.nextLine());
        }

    }

    private static boolean validatePackages(List<Component> cs,Path cache) {
        return cs.stream()
                .filter(c->!c.isDigestFile())
                .filter(c->!validateFile(c,cache))
                .collect(Collectors.toList()).size()==0;
    }

    private static void download(String url, Path target) {
        if(target.toFile().exists()){
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
            var resp=client.send(req,
                    HttpResponse.BodyHandlers.ofInputStream());
            if(resp.statusCode()==200){
                Files.copy(resp.body(),target, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String digest(Path target){
        MessageDigest sha256= null;
        try {
            sha256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        var buffer=new byte[8192];
        try(var in=Files.newInputStream(target,StandardOpenOption.READ)){
            var tmp=-1;
            while ((tmp=in.read(buffer))>0) {
                sha256.update(buffer,0,tmp);
            }
        }catch (IOException e){

        }
        return Hex.encodeHexString(sha256.digest());

    }

    private static boolean validateFile(Component component,Path cache){
        var target=cache.resolve(component.filename);
        var digest=digest(target);
        String digest1= null;
        try {
            digest1 = Files.readString(cache.resolve(component.getDigestFilename()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return digest1.equals(digest);
    }

    record InstallInfo(String jdkVersion,String version){}
    private static InstallInfo extractVersion(Path home) {
        Process p= null;
        try {
            p = new ProcessBuilder(home.resolve("bin"+File.separator+"java").toFile().getAbsolutePath(),"--version")
                    .start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var scanner=new Scanner(p.getInputStream());
        var line=scanner.nextLine();
        //var items=line.split(" ");
        var jdkVersion="java"+line.split(" ")[1].split("\\.")[0];
        line=scanner.nextLine();
        var version=line.split(" ")[5];
        return new InstallInfo(jdkVersion,version);
    }
}
