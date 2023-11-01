package cn.vanillazi.tool.command;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "source",description = "java source code collector")
public class JavaSourceCodeCollector implements Callable<Integer> {

    @CommandLine.Parameters(index = "0",description = "the file to save source code ")
    private File target;

    @CommandLine.Parameters(index = "1..*",description = "the source roots")
    private File[] sourceRoots;

    @Override
    public Integer call() throws Exception {
        collectSourceCode(sourceRoots,target.toPath());
        return 0;
    }


    public static void collectSourceCode(File[] sourceRoots,Path target){
        var files= Arrays.stream(sourceRoots).flatMap(sourceRoot->{
            return FileUtils.listFiles(sourceRoot,new String[]{"java"},true).stream();
        }).toList();

        try(var output= Files.newOutputStream(target, target.toFile().exists()? StandardOpenOption.CREATE:StandardOpenOption.CREATE_NEW)) {
            for (File file : files) {
                if(file.getAbsolutePath().contains("uid")){
                    continue;
                }
                try(var in=Files.newInputStream(file.toPath(),StandardOpenOption.READ)) {
                    Scanner scanner=new Scanner(in);
                    while (scanner.hasNextLine()){
                        var line=scanner.nextLine();
                        if(StringUtils.isBlank(line)){
                            continue;
                        }
                        var tl=line.trim();
                        if(tl.startsWith("/**") || tl.startsWith("*") || tl.startsWith("import ")){
                            continue;
                        }
                        IOUtils.write(line, output);
                        IOUtils.write(System.lineSeparator(), output);
                    }
                    output.flush();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
