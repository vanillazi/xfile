package cn.vanillazi.tool;

import java.io.IOException;
import java.nio.file.Path;
import java.io.File;
public class GitKeeper {

    public static void main(String[] args) throws IOException {
        var target= Path.of("D:\\workspaces\\hnvmns2023\\hnvmns2023");
        addGitKeep(target.toFile());
    }

    private static void addGitKeep(File target) throws IOException {
        if(target.isFile()){
            return;
        }
        var files=target.listFiles();
        if(files!=null){
            if(files.length==0){
                var gitKeeper=new File(target,".gitkeep");
                gitKeeper.createNewFile();
            }else{
                for(var f:files){
                    addGitKeep(f);
                }
            }
        }
    }
}
