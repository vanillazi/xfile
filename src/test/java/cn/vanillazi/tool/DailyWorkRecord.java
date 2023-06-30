package cn.vanillazi.tool;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileEqualsFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DailyWorkRecord {

    public static void main(String[] args) throws IOException {
        var days= Arrays.asList(
                LocalDate.of(2023,5,23),
                LocalDate.of(2023,5,24),
                LocalDate.of(2023,5,25),
                LocalDate.of(2023,5,29),
                LocalDate.of(2023,5,31),
                LocalDate.of(2023,6,1),
                LocalDate.of(2023,6,5),
                LocalDate.of(2023,6,6),
                LocalDate.of(2023,6,7),
                LocalDate.of(2023,6,14),
                LocalDate.of(2023,6,15),
                LocalDate.of(2023,6,16),
                LocalDate.of(2023,6,19),
                LocalDate.of(2023,6,20),
                LocalDate.of(2023,6,25)
        );
        var root="D:\\workspaces\\me\\notes\\blog";
        var files=FileUtils.listFiles(new File(root),new String[]{"md"},true);
        var name2files= files.stream()
                .collect(Collectors.toMap(File::getName, Function.identity()));
        var sdf= DateTimeFormatter.ofPattern("yyyyMMdd");
        var sb=new StringBuilder("日期,内容\n");
        days.stream()
                .map(d->sdf.format(d)+"工作日志.md")
                .map(name-> name2files.get(name))
                .forEach(f->{
                    var content=readLogContent(f);
                    sb.append(content.title.substring(0,content.title.length()-4)).append(",").append(content.content)
                            .append("\n");
                });
        System.out.println(sb.toString());
        Files.writeString(Path.of("target","log.csv"),sb.toString(), StandardCharsets.UTF_8);

    }
    record DailyWorkLog(String title,String content){}
    private static DailyWorkLog readLogContent(File f) {
        try {
            var contentLines=new ArrayList<String>();
            var sb=new StringBuilder();
            var lines=Files.readAllLines(f.toPath());
            var hd=0;
            for(int i=0;i< lines.size();i++){
                var line=lines.get(i).trim();
                if(line.equals("---")){
                    hd++;
                    continue;
                }
                if(!line.isEmpty() && hd==2){
                    contentLines.add(line);
                }
            }
            var title=contentLines.get(0).replace("#","").trim();
            var content= contentLines.stream().skip(1).collect(Collectors.joining(";")).replaceAll(",",".");
            return new DailyWorkLog(title,content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
