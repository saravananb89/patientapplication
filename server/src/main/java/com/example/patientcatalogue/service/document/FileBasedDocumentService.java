package com.example.patientcatalogue.service.document;

import com.example.patientcatalogue.data.document.DocumentDTO;
import com.example.patientcatalogue.data.document.DocumentDTORepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class FileBasedDocumentService implements DocumentService {

    @Autowired
    private DocumentDTORepositoryInterface documentDTORepository;

    @Override
    public void uploadDocument(int patientId, int patientVisitId, byte[] fileContent, String fileName, String fileExtension ) {
        documentDTORepository.uploadDocument(new DocumentDTO(patientId, patientVisitId, fileContent, fileName, fileExtension));
    }

    @Override
    public List<DocumentDTO> getDocuments(int patientId, int patientVisitId) {
        return documentDTORepository.getDocuments(patientId, patientVisitId);
    }

}
