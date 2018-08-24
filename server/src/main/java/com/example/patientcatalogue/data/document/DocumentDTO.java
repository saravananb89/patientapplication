package com.example.patientcatalogue.data.document;


import java.util.Objects;

public class DocumentDTO {
    private Integer patientId;
    private Integer patientVisitId;
    private String fileExtension;
    private String fileName;
    private byte[] fileContent;

    public DocumentDTO() {
    }

    public DocumentDTO(Integer patientId, Integer patientVisitId, byte[] fileContent, String fileName,String fileExtension ) {
        this.patientId = patientId;
        this.patientVisitId = patientVisitId;
        this.fileContent = fileContent;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getPatientVisitId() {
        return patientVisitId;
    }

    public void setPatientVisitId(Integer patientVisitId) {
        this.patientVisitId = patientVisitId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentDTO that = (DocumentDTO) o;
        return Objects.equals(patientId, that.patientId) &&
                Objects.equals(patientVisitId, that.patientVisitId) &&
                Objects.equals(fileExtension, that.fileExtension) &&
                Objects.equals(fileName, that.fileName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(patientId, patientVisitId, fileExtension, fileName);
    }

    @Override
    public String toString() {
        return "DocumentDTO{" +
                "patientId=" + patientId +
                ", patientVisitId=" + patientVisitId +
                ", fileExtension='" + fileExtension + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
