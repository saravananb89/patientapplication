package com.example.patientcatalogue.service.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Document {

    private int patientId;
    private int patientVisitId;
    private String fileName;
    private byte[] fileContent;
    private String fileExtension;

}
