package com.zeiss.document.service.api;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public interface Document {

    void setPatientId(Integer patientId);

    void setPatientVisitId(Integer patientVisitId);

    void setFileName(String fileName);

    void setFileContent(byte[] fileContent);

    void setFileExtension(String fileExtension);

    Integer getPatientId();

    IntegerProperty patientIdProperty();

    Integer getPatientVisitId();

    IntegerProperty patientVisitIdProperty();

    String getFileName();

    StringProperty fileNameProperty();

    byte[] getFileContent();

    ObjectProperty<byte[]> fileContentProperty();


    String getFileExtension();

    StringProperty fileExtensionProperty();
}
