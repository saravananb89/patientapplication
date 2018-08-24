package com.zeiss.document.service.api;

import java.util.List;

public interface DocumentService {

    List<Document> getDocuments(String patientId);

    void uploadDocument(int patientId, int patientVisitId, byte[] fileContent, String fileName,String fileExtension );

    List<Document> getDocuments(int patientId, int patientVisitId);
}
