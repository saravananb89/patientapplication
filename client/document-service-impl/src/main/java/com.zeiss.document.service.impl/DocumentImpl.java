package com.zeiss.document.service.impl;

import com.zeiss.document.service.api.Document;
import javafx.beans.property.*;

import java.util.Objects;

public class DocumentImpl implements Document {

    private final IntegerProperty patientId = new SimpleIntegerProperty();
    private final IntegerProperty patientVisitId = new SimpleIntegerProperty();
    private final StringProperty fileName = new SimpleStringProperty();
    private final ObjectProperty<byte[]> fileContent = new SimpleObjectProperty<>();
    private final StringProperty fileExtension = new SimpleStringProperty();

    public DocumentImpl(Integer patientId, Integer patientVisitId, byte[] fileContent, String fileName, String fileExtension) {
        this.patientId.setValue(patientId);
        this.patientVisitId.setValue(patientVisitId);
        this.fileName.setValue(fileName);
        this.fileContent.setValue(fileContent);
        this.fileExtension.setValue(fileExtension);
    }

    public DocumentImpl() {
    }

    public Integer getPatientId() {
        return patientId.get();
    }

    public IntegerProperty patientIdProperty() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId.set(patientId);
    }

    public void setPatientVisitId(Integer patientVisitId) {
        this.patientVisitId.set(patientVisitId);
    }

    public Integer getPatientVisitId() {
        return patientVisitId.get();
    }

    public IntegerProperty patientVisitIdProperty() {
        return patientVisitId;
    }

    public String getFileName() {
        return fileName.get();
    }

    public StringProperty fileNameProperty() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName.set(fileName);
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent.set(fileContent);
    }

    public byte[] getFileContent() {
        return fileContent.get();
    }

    public ObjectProperty<byte[]> fileContentProperty() {
        return fileContent;
    }

    public String getFileExtension() {
        return fileExtension.get();
    }

    public StringProperty fileExtensionProperty() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension.set(fileExtension);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentImpl document = (DocumentImpl) o;
        return Objects.equals(patientId, document.patientId) &&
                Objects.equals(patientVisitId, document.patientVisitId) &&
                Objects.equals(fileName, document.fileName) &&
                Objects.equals(fileExtension, document.fileExtension);
    }

    @Override
    public int hashCode() {

        return Objects.hash(patientId, patientVisitId, fileName, fileExtension);
    }

    @Override
    public String toString() {
        return "DocumentImpl{" +
                "patientId=" + patientId +
                ", patientVisitId=" + patientVisitId +
                ", fileName=" + fileName +
                ", fileExtension=" + fileExtension +
                '}';
    }
}
