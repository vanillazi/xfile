package cn.vanillazi.tool;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;

public class DailyWorkRecord {

    public static void main(String[] args) throws IOException {
        var days= Arrays.asList(
                LocalDate.of(2023,4,18),
                LocalDate.of(2023,4,19),
                LocalDate.of(2023,4,20),
                LocalDate.of(2023,4,21),
                LocalDate.of(2023,4,24),
                LocalDate.of(2023,4,26),
                LocalDate.of(2023,4,28),
                LocalDate.of(2023,5,8),
                LocalDate.of(2023,5,9),
                LocalDate.of(2023,5,10),
                LocalDate.of(2023,5,11),
                LocalDate.of(2023,5,15),
                LocalDate.of(2023,5,16),
                LocalDate.of(2023,5,17)
        );

        PDFMergerUtility PDFmerger = new PDFMergerUtility();
        PDFmerger.setDestinationFileName("./merged.pdf");
        var sdf= DateTimeFormatter.ofPattern("yyyyMMdd");
        days.stream()
                .map(d->"./data/"+sdf.format(d)+"工作日志.pdf")
                .map(name-> Path.of(name))
                .forEach(a->{
                    if(!a.toFile().exists()) {
                        System.out.println(a.toFile().getName());

                    }else{
                        try {
                            PDFmerger.addSource(a.toFile());
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

        PDFmerger.mergeDocuments();
    }
}
