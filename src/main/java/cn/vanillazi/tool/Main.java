package cn.vanillazi.tool;

import picocli.CommandLine;

public class Main {

    public static void main(String[] args) {
        var exitCode=new CommandLine(new CompositedCommand())
                .execute(args);
        System.exit(exitCode);
    }
}