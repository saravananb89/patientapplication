package com.example.patientcatalogue.data.document;


import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class DocumentDTOServiceTest {

    DocumentDTO documentDTO;

    @Before
    public void load() throws IOException {

        documentDTO = new DocumentDTO();

        InputStream resourceAsStream = this.getClass().getResourceAsStream("Retina.jpg");

        documentDTO.setFileContent(IOUtils.toByteArray(resourceAsStream));
        documentDTO.setPatientId(001);
        documentDTO.setPatientVisitId(0001);
        documentDTO.setFileName("Retina");

    }

    @Test
    public void testUploadDocument() {

        DocumentDTORepository documentDTORepository = new DocumentDTORepository();

        documentDTORepository.uploadDocument(documentDTO);

        Assert.assertNotNull(documentDTORepository.getDocuments(documentDTO.getPatientId(),documentDTO.getPatientVisitId()));

    }
}
