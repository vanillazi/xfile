package cn.vanillazi.tool;

import cn.vanillazi.tool.command.Jiaoxi51Command;
import cn.vanillazi.tool.command.Png2IconCommand;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "xfile",
        subcommands = {Png2IconCommand.class, Jiaoxi51Command.class},
        usageHelpAutoWidth = true)
public class CompositedCommand implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        new CommandLine(new CompositedCommand()).usage(System.out);
        return 0;
    }
}
