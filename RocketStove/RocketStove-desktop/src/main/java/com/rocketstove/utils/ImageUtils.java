/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.rocketstove.exception.RocketStoveFileNotFoundException;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.imgscalr.Scalr;

/**
 *
 * @author SUJAN
 */
public class ImageUtils {

    public static Optional<ByteArrayInputStream> convertEncodeStringIntoInputstream(Optional<String> encodedString) {
        if (encodedString.isPresent()) {
            return Optional.ofNullable(
                    new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(encodedString.get()))
            );
        }
        return Optional.empty();
    }

    public static File toRotateImage(File file) throws IOException, DocumentException {
        BufferedImage bimg = ImageIO.read(file);
        int width = bimg.getWidth();
        int height = bimg.getHeight();
        BufferedImage biFlip = new BufferedImage(height, width, bimg.getType());
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                biFlip.setRGB(height - 1 - j, i, bimg.getRGB(i, j));
            }
        }
        ImageIO.write(biFlip, "jpg", file);
        return file;
    }

    public static File toPdf(String pdfFolder, String prefixProperty, String rocketIdFromWeb, String rocketId, List<File> imageFilesToMerge) throws IOException, DocumentException {
        String extension = ".pdf";
        String fileName = prefixProperty + rocketId + extension;
        File dir = new File(pdfFolder + File.separator + rocketId);
        if (!dir.exists()) {
            dir.mkdir();
        }
        double size = 0;
        for (File file : imageFilesToMerge) {
            size += com.rocketstove.utils.FileUtils.calculateDirectoryInMegaBytes(file);
        }
        int compressionLevel = size >= 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        File pdfFile = new File(dir, fileName);
        Font f2 = FontFactory.getFont(FontFactory.TIMES, 25);
        f2.setColor(BaseColor.BLACK);
        f2.setStyle(f2.BOLD);
        String data = spaceBetweenEachLetter(rocketIdFromWeb);
        Paragraph p
                = new Paragraph(data, f2);

        p.setAlignment(Element.ALIGN_RIGHT);
        p.setIndentationRight(0);
        p.setPaddingTop(0);
        p.setLeading(0, 1.2f);

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
        writer.setSpaceCharRatio(PdfWriter.SPACE);
        document.open();
        for (File file : imageFilesToMerge) {
            if (imageFilesToMerge.indexOf(file) == 0) {
//                if (size > 1.5) {
//                    decreaseImageQuality(file);
//                }
                Image image = Image.getInstance(file.getAbsolutePath());
                document.setPageSize(image);
                image.setCompressionLevel(compressionLevel);
                image.setAbsolutePosition(0, 0);
                image.setBorderWidth(0);
                image.scalePercent(100);
                document.newPage();
                document.add(p);
                document.add(image);
            } else {
//                if (size > 1.5) {
//                    decreaseImageQuality(file);
//                }
                Image image = Image.getInstance(file.getAbsolutePath());

                document.setPageSize(image);
                image.setCompressionLevel(compressionLevel);
                image.setAbsolutePosition(0, 0);
                image.setBorderWidth(0);
                image.scalePercent(100);
                document.newPage();
                document.add(image);
            }
        }
        document.close();
        return pdfFile;
    }

    public static File toPlainPdf(String pdfFolder, String prefixProperty, String rocketId, List<File> imageFilesToMerge) throws IOException, DocumentException {
        String extension = ".pdf";
        String fileName = prefixProperty + rocketId + extension;
        File dir = new File(pdfFolder + File.separator + rocketId);
        if (!dir.exists()) {
            dir.mkdir();
        }
        double size = 0;

        for (File file : imageFilesToMerge) {
            size += com.rocketstove.utils.FileUtils.calculateDirectoryInMegaBytes(file);
        }
        File pdfFile = new File(dir, fileName);
        int compressionLevel = size >= 2 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
        document.open();
        for (File file : imageFilesToMerge) {
//            if (size > 1.5) {
//                decreaseImageQuality(file);
//            }
            Image image = Image.getInstance(file.getAbsolutePath());

            document.setPageSize(image);
            image.setCompressionLevel(compressionLevel);
            image.setAbsolutePosition(0, 0);
            image.setBorderWidth(0);
            //image.scaleAbsolute(PageSize.A4);
            image.scalePercent(100);
            document.newPage();
            document.add(image);
        }
        document.close();
        return pdfFile;
    }

    public static File writeImageEncodeStringFile(String pathToUpload, String fileName, String encodedImg) throws IOException {
        File file
                = new File(pathToUpload, fileName);
        InputStream stoveIdImgInputStream = ImageUtils.convertEncodeStringIntoInputstream(
                Optional.of(encodedImg)
        ).get();
        FileUtils.copyInputStreamToFile(stoveIdImgInputStream, file);
        return file;
    }

    public static File writeImageEncodeStringFileRotate(String pathToUpload, String fileName, String encodedImg) throws IOException {
        File file
                = new File(pathToUpload, fileName);
        InputStream stoveIdImgInputStream = ImageUtils.convertEncodeStringIntoInputstream(
                Optional.of(encodedImg)
        ).get();
        FileUtils.copyInputStreamToFile(stoveIdImgInputStream, file);
        BufferedImage bimg = ImageIO.read(file);
        int width = bimg.getWidth();
        int height = bimg.getHeight();
        if (width > height) {
            BufferedImage newImage = new BufferedImage(height, width, bimg.getType());
            AffineTransform transform = new AffineTransform();
            transform.rotate(Math.PI / 2, width / 2, height / 2);
            double offset = (width - height) / 2;
            transform.translate(offset, offset);
            AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
            op.filter(bimg, newImage);
            ImageIO.write(newImage, "jpg", file);
        }
        return file;
    }

    public static String spaceBetweenEachLetter(String rocketIdFromWeb) {
        String solarWebId = rocketIdFromWeb;
        StringBuilder str = new StringBuilder("\n\n\n\n");
        for (char c : solarWebId.trim().toCharArray()) {
            str.append(c).append(" ");
        }
        return str.append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
                .toString();
    }

    public static File toTestPdf(String pdfFolder, String prefixProperty, String rocketIdFromWeb, String rocketId, List<File> imageFilesToMerge) throws IOException, DocumentException {
        String extension = ".pdf";
        String fileName = prefixProperty + rocketId + extension;
        File dir = new File(pdfFolder + File.separator + rocketId);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File pdfFile = new File(dir, fileName);

        Font f2 = FontFactory.getFont(FontFactory.TIMES, 20);
        f2.setColor(BaseColor.BLUE);
        f2.setStyle(Font.BOLD);

        Paragraph p
                = new Paragraph(spaceBetweenEachLetter(rocketIdFromWeb), f2);

        p.setAlignment(Element.ALIGN_RIGHT);
        p.setIndentationRight(0);
        p.setPaddingTop(0);
        p.setLeading(0, 1.2f);
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
        writer.setSpaceCharRatio(PdfWriter.SPACE);

        document.open();
        for (File file : imageFilesToMerge) {
            if (imageFilesToMerge.indexOf(file) == 0) {
                Image image = Image.getInstance(file.getAbsolutePath());
                document.setPageSize(image);
                image.setCompressionLevel(Integer.MIN_VALUE);
                image.setAbsolutePosition(0, 0);
                image.setBorderWidth(0);
                image.scalePercent(100);
                document.newPage();
                document.add(p);
                document.add(image);
            } else {
                Image image = Image.getInstance(file.getAbsolutePath());
                document.setPageSize(image);
                image.setCompressionLevel(Integer.MIN_VALUE);
                image.setAbsolutePosition(0, 0);
                image.setBorderWidth(0);
                image.scalePercent(100);
                document.newPage();
                document.add(image);
            }
        }
        document.close();
        return pdfFile;
    }

    private static void decreaseImageQuality(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            image = Scalr.resize(image, 1366, 768);
            FileUtils.deleteQuietly(file);
            ImageIO.write(image, "jpg", file);
        } catch (IOException ex) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String imageBase64(File file) throws IOException {
        byte[] byteArr = loadFile(file);
        return Base64.encodeBase64String(byteArr);
    }

    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            throw new RocketStoveFileNotFoundException("Big file");
        }
        byte[] bytes = new byte[(int) length];
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new RocketStoveFileNotFoundException("Could not completely read file " + file.getName());
        }

        is.close();
        return bytes;
    }
}
