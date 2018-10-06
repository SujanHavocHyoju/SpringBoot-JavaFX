/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.utils;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author SUJAN
 */
public class ImageToPdfUtils {
    public static void main(String[] args) throws FileNotFoundException, DocumentException, BadElementException, IOException {
        File root = new File("C:\\Users\\SUJAN\\Desktop\\pics");
        String outputFile = "output9.pdf";
        List<String> files = new ArrayList<String>();
        files.add("page1.jpg");
        files.add("page2.jpg");

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(new File(root, outputFile)));
        document.open();
        Font f2 = FontFactory.getFont(FontFactory.TIMES, 16);
        f2.setColor(BaseColor.BLUE);
        f2.setStyle(f2.BOLD);
        Paragraph p
                = new Paragraph("Rocket Stove ID: JMC00100454", f2);

        p.setAlignment(Element.ALIGN_RIGHT);
        p.setIndentationRight(0);
        p.setPaddingTop(0);
        //document.add(p);
        for (String f : files) {
            document.newPage();
            document.add(p);
            Image image = Image.getInstance(new File(root, f).getAbsolutePath());
            image.setCompressionLevel(Integer.MIN_VALUE);
            image.setAbsolutePosition(0, 0);
            image.setBorderWidth(0);
            image.scaleAbsolute(PageSize.A4);
            document.add(image);
        }
        document.close();
        String existingfile = "C:\\Users\\SUJAN\\Desktop\\pics\\output1.pdf";
        PdfReader reader = new PdfReader(new FileInputStream(existingfile));
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("outputC.pdf"));
        int total = reader.getNumberOfPages() + 1;
        for (int i = 1; i < total; i++) {
            reader.setPageContent(i + 1, reader.getPageContent(i + 1));
        }
        stamper.setFullCompression();
        stamper.close();
    }
}
