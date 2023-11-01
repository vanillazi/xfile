package cn.vanillazi.tool.command;

import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.PixelDensity;
import org.apache.commons.imaging.formats.ico.IcoImageParser;
import org.apache.commons.imaging.formats.ico.IcoImagingParameters;
import picocli.CommandLine;

import javax.imageio.ImageIO;
import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Callable;

@Slf4j
@CommandLine.Command(name = "img2icon",description = "convert image to icon file")
public class Png2IconCommand implements Callable<Integer> {

    @CommandLine.Parameters(index = "0",description = "the source image file")
    private File src;

    @CommandLine.Parameters(index = "1",description = "The destination to save the icon image")
    private File dst;

    @Override
    public Integer call() throws Exception {
        return convert(src,dst)?0:1;
    }

    @VisibleForTesting
    protected static boolean convert(File src,File dst) throws IOException, ImageWriteException {
        if(!src.exists()){
            log.error("The source image file {} is not found!",src.getAbsolutePath());
            return false;
        }
        var image= ImageIO.read(src);
        var options=dst.exists()? StandardOpenOption.TRUNCATE_EXISTING:StandardOpenOption.CREATE_NEW;
        try(var out= Files.newOutputStream(dst.toPath(),options)){
            var params=new IcoImagingParameters();
            params.setStrict(false);
            params.setPixelDensity(PixelDensity.createFromPixelsPerInch(128,128));
            new IcoImageParser().writeImage(image,out,params);
            out.flush();
            return true;
        }catch (Throwable e){
            log.error("the convention is failed!",e);
        }
        return false;
    }
}
