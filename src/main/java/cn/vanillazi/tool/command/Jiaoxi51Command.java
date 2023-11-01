package cn.vanillazi.tool.command;

import cn.vanillazi.tool.command.internal.jiaoxi51.Jiaoxi51Crawler;
import picocli.CommandLine;

import java.io.File;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "jiaoxi51",description = "download resource from www.51jiaoxi.com")
public class Jiaoxi51Command implements Callable<Integer> {

    @CommandLine.Parameters(index = "0",description = "the directory to save resource")
    private File output;

    @CommandLine.Parameters(index = "1..*",description = "the resource urls")
    private String[] urls;


    @Override
    public Integer call() throws Exception {
        for(var url:urls){
            Jiaoxi51Crawler.download(url,output.toPath());
        }
        return 0;
    }
}
