package cn.vanillazi.tool;

import org.apache.commons.io.FileUtils;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.xslf.usermodel.*;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PptxModifier {

    public static void main(String[] args) throws IOException {
        var root=Path.of("C:\\Users\\tqd\\Downloads\\【整书课件】数学人教四（上）-最新教材版\\");
        var dst=new File("target"+File.separator+"test");
        if(dst.exists()){
            FileUtils.deleteDirectory(dst);
        }
        dst.mkdirs();
        FileUtils.copyDirectory(root.toFile(),dst);
        var files=FileUtils.listFiles(dst,new String[]{"pptx"},true);

        for(var file:files){
           try(var in=Files.newInputStream(file.toPath())) {
               XMLSlideShow ppt = new XMLSlideShow(in);
               var width = ppt.getPageSize().getWidth();
               var height = ppt.getPageSize().getHeight();
               for (var slide : ppt.getSlides()) {
                   var pictureData = new File("test1.png");
                   XSLFPictureData pictureIndex = ppt.addPicture(pictureData, PictureData.PictureType.PNG);
                   XSLFPictureShape picture = slide.createPicture(pictureIndex);
                   picture.setAnchor(new Rectangle(590, 0, 130, 30));
               }
               ppt.removeSlide(1);
               ppt.removeSlide(ppt.getSlides().size()-1);
               ppt.removeSlide(ppt.getSlides().size()-1);
               ppt.removeSlide(ppt.getSlides().size()-1);
               // 保存PPT文件
               FileOutputStream out = new FileOutputStream(file);
               ppt.write(out);
               out.close();
               ppt.close();

           }catch (Throwable e){
               System.out.println(file.getAbsolutePath());
               e.printStackTrace();

           }
        }



    }


    public static void test() throws IOException {
        var path= Path.of("C:\\Users\\tqd\\Downloads\\【整书课件】数学人教四（上）-最新教材版\\数学人教四（上） 9  总复习\\练习二十一.pptx");
        XMLSlideShow ppt = new XMLSlideShow(Files.newInputStream(path));
        var width=ppt.getPageSize().getWidth();
        var height=ppt.getPageSize().getHeight();
        for(var slide : ppt.getSlides()){
            var pictureData=new File("test1.png");
            int pictureType=0;
            XSLFPictureData pictureIndex = ppt.addPicture(pictureData, PictureData.PictureType.PNG);
            XSLFPictureShape picture = slide.createPicture(pictureIndex);
            picture.setAnchor(new Rectangle(590,0,130,30));
        }

        var file=new File("presentation.pptx");
        // 保存PPT文件
        FileOutputStream out = new FileOutputStream(file);
        ppt.write(out);
        out.close();

        ppt.close();
        Desktop.getDesktop().open(file);
    }
}
