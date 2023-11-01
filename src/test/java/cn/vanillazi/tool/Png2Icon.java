package cn.vanillazi.tool;

import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.PixelDensity;
import org.apache.commons.imaging.formats.ico.IcoImageParser;
import org.apache.commons.imaging.formats.ico.IcoImagingParameters;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Png2Icon {

    public static void main(String[] args) throws IOException, ImageWriteException {
        var image=ImageIO.read(new File("C:\\Users\\wanazi\\Desktop\\logo.png"));
        var out=new FileOutputStream(new File("target/logo.ico"));
        var params=new IcoImagingParameters();
        params.setStrict(false);
        params.setPixelDensity(PixelDensity.createFromPixelsPerInch(128,128));
        new IcoImageParser().writeImage(image,out,params);
    }
}
