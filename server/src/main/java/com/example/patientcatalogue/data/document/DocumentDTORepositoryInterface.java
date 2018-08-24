package com.example.patientcatalogue.data.document;

import java.util.List;

public interface DocumentDTORepositoryInterface {

    void uploadDocument(DocumentDTO documentDTO);

    List<DocumentDTO> getDocuments(int patientId, int patientVisitId);
}
