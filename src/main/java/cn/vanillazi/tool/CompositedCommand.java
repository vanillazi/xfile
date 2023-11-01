package cn.vanillazi.tool;

import cn.vanillazi.tool.command.Jiaoxi51Command;
import cn.vanillazi.tool.command.Png2IconCommand;
import cn.vanillazi.tool.command.JavaSourceCodeCollector;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "xfile",
        subcommands = {Png2IconCommand.class, Jiaoxi51Command.class, JavaSourceCodeCollector.class},
        usageHelpAutoWidth = true)
public class CompositedCommand implements Callable<Integer> {

    @Override
    public Integer call() {
        new CommandLine(new CompositedCommand()).usage(System.out);
        return 0;
    }
}
