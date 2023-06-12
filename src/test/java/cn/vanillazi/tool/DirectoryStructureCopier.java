package cn.vanillazi.tool;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Path;

public class DirectoryStructureCopier {

    public static void main(String[] args) throws IOException {
        var src= Path.of("D:\\workspaces\\hnvmns9000\\HN-SP-MIS");
        var target=Path.of("D:\\workspaces\\hnvmns2023\\hnvmns2023");
        FileUtils.copyDirectory(src.toFile(), target.toFile(), new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });

    }
}
