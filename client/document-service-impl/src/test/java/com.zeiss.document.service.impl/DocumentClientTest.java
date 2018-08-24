package com.zeiss.document.service.impl;

import com.zeiss.document.service.api.Document;
import com.zeiss.patient.service.api.PatientService;
import org.apache.commons.io.FilenameUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

public class DocumentClientTest {

    Document document;

    @Inject
    private PatientService patientService;

    @Before
    public void load() throws IOException {

        document = new DocumentImpl();

        URL resource = this.getClass().getClassLoader().getResource("Retina.jpg");
        File file = new File(resource.getPath());

        if (file.exists()) {
            document.setFileContent(Files.readAllBytes(file.toPath()));
        }
        document.setPatientId(6);
        document.setPatientVisitId(7);
        document.setFileName(FilenameUtils.removeExtension(file.getName()));
        System.out.println(document.getFileName());
        document.setFileExtension(FilenameUtils.getExtension(file.getName()));
        System.out.println(document.getFileExtension());


    }

    @Test
    public void testUploadDocument() {

        DocumentClient documentClient = new DocumentClient();

        documentClient.upload(document);

        Assert.assertNotNull(documentClient.getDocuments(document.getPatientId(), document.getPatientVisitId()));

    }

    @Test
    public void testGetDocument() {

        DocumentClient documentClient = new DocumentClient();

        List<Document> documents = documentClient.getDocuments(document.getPatientId(), document.getPatientVisitId());

        Assert.assertNotNull(documentClient.getDocuments(document.getPatientId(), document.getPatientVisitId()));

    }

}