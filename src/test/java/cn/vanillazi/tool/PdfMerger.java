package cn.vanillazi.tool;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class PdfMerger {

    public static void main(String[] args) throws IOException {
        var file = Path.of("D:\\workspaces\\me\\notes\\docs\\record\\2023\\08\\18","滴滴电子发票.pdf").toFile();
        var file1 = Path.of("D:\\workspaces\\me\\notes\\docs\\record\\2023\\08\\18","滴滴出行行程报销单.pdf").toFile();
        PDDocument doc=PDDocument.load(file);
        doc.getPage(0).setRotation(90);
        var tf=File.createTempFile("test",".pdf");
        doc.save(tf);
        PDFMergerUtility PDFmerger = new PDFMergerUtility();
        PDFmerger.setDestinationFileName("target/merged.pdf");
        PDFmerger.addSource(file1);
        PDFmerger.addSource(tf);
        PDFmerger.mergeDocuments();

    }
}

