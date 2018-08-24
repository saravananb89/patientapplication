package com.zeiss.document.service.impl;

import com.zeiss.document.service.api.Document;
import com.zeiss.document.service.api.DocumentService;

import javax.inject.Inject;
import java.util.List;

public class DefaultDocumentService implements DocumentService {

    private DocumentClient documentClient;

    @Inject
    public DefaultDocumentService(DocumentClient documentClient) {
        this.documentClient = documentClient;
    }

    @Override
    public List<Document> getDocuments(String patientId) {
        return documentClient.getDocuments(patientId);
    }

    @Override
    public void uploadDocument(int patientId, int patientVisitId, byte[] fileContent, String fileName,
                               String fileExtension) {
        Document doc = new DocumentImpl();
        doc.setPatientId(patientId);
        doc.setPatientVisitId(patientVisitId);
        doc.setFileContent(fileContent);
        doc.setFileName(fileName);
        doc.setFileExtension(fileExtension);
        documentClient.upload(doc);
    }

    @Override
    public List<Document> getDocuments(int patientId, int patientVisitId) {
        return documentClient.getDocuments(patientId, patientVisitId);
    }
}
