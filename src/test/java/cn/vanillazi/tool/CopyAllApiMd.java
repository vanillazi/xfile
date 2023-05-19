package cn.vanillazi.tool;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

public class CopyAllApiMd {

    public static void main(String[] args) {
        var root="D:\\workspaces\\hnvmns9000";
        var fs= Path.of(root).toFile().listFiles();
        Arrays.stream(fs).forEach(f->{
            if(isMavenRoot(f)){
                searchDoc(f);
            }
        });
    }

    public static boolean isMavenRoot(File file){
        var pomFile=new File(file,"pom.xml");
        return pomFile.exists() && pomFile.isFile();
    }


    private static void searchDoc(File f) {
        var target=new File(f,"target"+File.separator+"openapi");
        if(target.isDirectory()){
            var fs=FileUtils.listFiles(target,new String[]{"md"},false);
            if(fs.isEmpty()){
                var subModules=f.listFiles();
                Stream.of(subModules).forEach(sf->searchDoc(sf));
            }else {
                fs.forEach(ff -> {
                    System.out.println(ff.getAbsolutePath());
                    try {
                        FileUtils.copyFileToDirectory(ff, new File("target"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }else{
            var subModules=f.listFiles();
            if(subModules != null) {
                Stream.of(subModules).forEach(sf -> searchDoc(sf));
            }
        }

    }
}
