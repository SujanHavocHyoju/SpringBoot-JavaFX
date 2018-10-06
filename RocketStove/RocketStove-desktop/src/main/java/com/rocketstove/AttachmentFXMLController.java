/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.rocketstove.domain.AggregateForm;
import com.rocketstove.utils.ImageUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import org.apache.commons.io.FileUtils;

/**
 * FXML Controller class
 *
 * @author SUJAN
 */
public class AttachmentFXMLController implements Initializable {

    private final List<String> filePaths = new ArrayList<>();

    private String pathDirName;

    HostServices hs;
    private AggregateForm aggregateForm;
    @FXML
    private Label filePathLabel;

    @FXML
    private Label fileNameLabel;
    @FXML
    private TextArea remarksText;

    @FXML
    private ImageView imageViewId;

    private File stockIdFiletoUpload;

    private File stoveHandOverToUpload;

    private File subsidyFileToUpload;

    private File citizenFileToUpload;

    public void GetFileName(String fileName) {
        fileNameLabel.setText(fileName);
    }

    public void GetFilePath(String filePath) {
        filePathLabel.setText(filePath);
    }

    public void getPathValueFromPreviousController(AggregateForm aggregateForm) throws IOException, DocumentException, InterruptedException {
        this.aggregateForm =aggregateForm;
        populateAttachments();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void populateAttachments() throws IOException, DocumentException, InterruptedException {
        List<File> subsidyFiles = new ArrayList<>();
        List<File> citizenFiles = new ArrayList<>();
        String citizenFromtImg = aggregateForm.getStarterForm().getCitizenFrontImg();
        String citizenBackImg = aggregateForm.getStarterForm().getCitizenBackImg();
        String formFirstImg = aggregateForm.getStarterForm().getsAFormFirstImg();
        String formSecondImg = aggregateForm.getStarterForm().getsAFormSecondImg();
        String stoveIdImg = aggregateForm.getStarterForm().getStoveIDImg();
        String stoveHandoverImg = aggregateForm.getStarterForm().getStoveHandOverImg();
        String rocketId = aggregateForm.getStarterForm().getRocketId();
        String pathToUpload = System.getProperty("img_folder");
        String preFixForm1 = "subsidy_first_form_";
        String extension = ".jpg";
        File subsidyFrontFile = writeFile(pathToUpload, preFixForm1 + rocketId + extension,
                 formFirstImg);
        subsidyFiles.add(subsidyFrontFile);
        String preFixForm2 = "subsidy_second_form_";
        File subsidyBackFile = writeFile(pathToUpload, preFixForm2 + rocketId
                + extension, formSecondImg);
        subsidyFiles.add(subsidyBackFile);
        String citizenFrontPrefix = "citizen_front_";
        String citizenBackPrefix = "citizen_back_";
        String stoveIdPrefix = "stove_id_";
        String stoveHandOverPrefix = "stove_handover_";
        File citizenFront = writeFile(pathToUpload, citizenFrontPrefix + rocketId + extension, citizenFromtImg);
        File citizenBack = writeFile(pathToUpload, citizenBackPrefix + rocketId + extension, citizenBackImg);
        citizenFiles.add(citizenFront);
        citizenFiles.add(citizenBack);
        //File stockIdFiletoUpload = writeFile(pathToUpload, stoveHandOverPrefix + rocketId + extension, stoveHandoverImg);
        stockIdFiletoUpload = writeFile(pathToUpload, stoveHandOverPrefix + rocketId + extension, stoveHandoverImg);
        stoveHandOverToUpload = writeFile(pathToUpload, stoveIdPrefix + rocketId + extension, stoveIdImg);
        subsidyFileToUpload = showPdfPreview("Application_", rocketId, subsidyFiles);
        citizenFileToUpload = showPdfPreview("citizenship_", rocketId, citizenFiles);

    }

    private static File writeFile(String pathToUpload, String fileName, String encodedImg) throws IOException {
        File file
                = new File(pathToUpload, fileName);
        InputStream stoveIdImgInputStream = ImageUtils.convertEncodeStringIntoInputstream(
                Optional.of(encodedImg)
        ).get();
        FileUtils.copyInputStreamToFile(stoveIdImgInputStream, file);
        return file;
    }

    public void stoveIdBtnClick(ActionEvent event) throws MalformedURLException {
        String imagePath = stockIdFiletoUpload.toURI().toURL().toString();
        MainApp.hs.showDocument(imagePath);
    }

    public void stoveHandoverBtnClick(ActionEvent event) throws MalformedURLException {
        String imagePath = stoveHandOverToUpload.toURI().toURL().toString();
        MainApp.hs.showDocument(imagePath);
    }

    public void subsidyPdfBtnClick(ActionEvent event) throws MalformedURLException {
        String pdfPath = subsidyFileToUpload.toURI().toURL().toString();
        MainApp.hs.showDocument(pdfPath);
    }

    public void citizenPdfBtnClick(ActionEvent event) throws MalformedURLException {
        String pdfPath = citizenFileToUpload.toURI().toURL().toString();
        MainApp.hs.showDocument(pdfPath);
    }

    public void confirmAction(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public void rejectAction(ActionEvent event) {
        aggregateForm.setMessage(remarksText.getText());
        aggregateForm.setFailedToAutomated(true);
        //FileProcessing.updateFile(aggregateForm);
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public static File showPdfPreview(String prefixProperty, String rocketId, List<File> imageFilesToMerge) throws IOException, DocumentException {
        //String extension = System.getProperty("pdf_extension");
        String extension = ".pdf";
        String fileName = prefixProperty + rocketId + extension;
        String pdfLocation = System.getProperty("pdf_folder");
        //File pdfFile = new File(System.getProperty("pdf_folder"), fileName);
        File pdfFile = new File(pdfLocation, fileName);
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
        document.open();
        for (File file : imageFilesToMerge) {
            document.newPage();
            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(file.getAbsolutePath());
            image.setCompressionLevel(Integer.MIN_VALUE);
            image.setAbsolutePosition(0, 0);
            image.setBorderWidth(0);
            image.scaleAbsolute(PageSize.A4);
            document.add(image);
        }
        document.close();
        return pdfFile;
    }

}
