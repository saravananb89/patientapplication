package com.example.patientcatalogue.service.document;

import com.example.patientcatalogue.data.document.DocumentDTO;

import java.util.List;

public interface DocumentService {

    void uploadDocument(int patientId, int patientVisitId, byte[] fileContent, String fileName, String fileExtension);

    List<DocumentDTO> getDocuments(int patientId, int patientVisitId);

}
