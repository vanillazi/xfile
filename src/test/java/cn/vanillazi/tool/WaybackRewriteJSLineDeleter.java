package cn.vanillazi.tool;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class WaybackRewriteJSLineDeleter {

    public static void main(String[] args) {
        var root= Path.of("D:\\workspaces\\5618cn.com");
        var files= FileUtils.listFiles(root.toFile(),null,true);
        files.stream().forEach(file -> {
            try {
                process(file);
            } catch (IOException e) {}
        });
    }

    private static void process(File file) throws IOException {
        var lines=FileUtils.readLines(file, StandardCharsets.UTF_8);
        int index=-1;
        for(int i=0;i<lines.size();i++){
            var line=lines.get(i);
            if(line.contains("</script> <!-- End Wayback Rewrite JS Include -->")){
                index=i;
                break;
            }
        }
        if(index>0){
            var newLines=new ArrayList<String>(lines.size());
            for(int i=0;i<index-4;i++){
                newLines.add(lines.get(i));
            }
            for(int i=index+1;i<lines.size();i++){
                newLines.add(lines.get(i));
            }
            var data=newLines.stream().collect(Collectors.joining("\r\n"));
            FileUtils.write(file,data,StandardCharsets.UTF_8);
        }
    }
}
