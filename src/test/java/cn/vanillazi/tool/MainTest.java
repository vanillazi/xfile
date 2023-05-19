package cn.vanillazi.tool;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

class MainTest {

    @Test
    void main() throws IOException {
        var root= Path.of("D:\\workspaces\\hnvmns9000\\hnvmns-regulations").toFile();
        var fileFilter=new RegexFileFilter(".*Traffic.*\\.java");
        var files=FileUtils.listFiles(root, fileFilter, TrueFileFilter.TRUE);
        files.forEach(f->{
            System.out.println(f.getAbsolutePath());
        });
        var target=Path.of("target",root.getName()).toFile();
        FileUtils.copyDirectory(root,target, FileFilterUtils.or(fileFilter, DirectoryFileFilter.DIRECTORY));
        deleteEmptyDirectory(target);
    }

    public static void deleteEmptyDirectory(File dir){
        if(dir.isFile()){
            return;
        }
        var files=dir.listFiles();
        if(files!=null && files.length>=0){
            for(var f:files){
                deleteEmptyDirectory(f);
            }
        }
        files=dir.listFiles();
        if(files==null || files.length==0){
            dir.delete();
        }
    }

    @Test
    public void createSchema(){
        var s= """
                create schema if not exists %s;
                """;
        var root=Path.of("C:\\Users\\wanazi\\Desktop\\hnvmns9000_common");
        var files=root.toFile().listFiles();
        Stream.of(files).forEach(f->{
            var baseName= FilenameUtils.getBaseName(f.getName());
            System.out.println(String.format(s,baseName));
        });
    }
}