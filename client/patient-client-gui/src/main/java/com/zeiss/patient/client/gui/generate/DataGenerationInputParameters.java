package com.zeiss.patient.client.gui.generate;

import java.io.File;
import java.time.LocalDate;

public class DataGenerationInputParameters {

    private File csvFile;
    private LocalDate dobFrom;
    private LocalDate dobUntil;
    private String emailPattern;
    private boolean isGenerateVisits;
    private int visitCount;
    private LocalDate visitDateFrom;
    private LocalDate getVisitDateUntil;

    public DataGenerationInputParameters() {
    }

    public DataGenerationInputParameters(File csvFile, LocalDate dobFrom, LocalDate dobUntil, String emailPattern, boolean isGenerateVisits, int visitCount, LocalDate visitDateFrom, LocalDate getVisitDateUntil) {
        this.csvFile = csvFile;
        this.dobFrom = dobFrom;
        this.dobUntil = dobUntil;
        this.emailPattern = emailPattern;
        this.isGenerateVisits = isGenerateVisits;
        this.visitCount = visitCount;
        this.visitDateFrom = visitDateFrom;
        this.getVisitDateUntil = getVisitDateUntil;
    }

    public File getCsvFile() {
        return csvFile;
    }

    public void setCsvFile(File csvFile) {
        this.csvFile = csvFile;
    }

    public LocalDate getDobFrom() {
        return dobFrom;
    }

    public void setDobFrom(LocalDate dobFrom) {
        this.dobFrom = dobFrom;
    }

    public LocalDate getDobUntil() {
        return dobUntil;
    }

    public void setDobUntil(LocalDate dobUntil) {
        this.dobUntil = dobUntil;
    }

    public String getEmailPattern() {
        return emailPattern;
    }

    public void setEmailPattern(String emailPattern) {
        this.emailPattern = emailPattern;
    }

    public boolean isGenerateVisits() {
        return isGenerateVisits;
    }

    public void setGenerateVisits(boolean generateVisits) {
        isGenerateVisits = generateVisits;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public LocalDate getVisitDateFrom() {
        return visitDateFrom;
    }

    public void setVisitDateFrom(LocalDate visitDateFrom) {
        this.visitDateFrom = visitDateFrom;
    }

    public LocalDate getGetVisitDateUntil() {
        return getVisitDateUntil;
    }

    public void setGetVisitDateUntil(LocalDate getVisitDateUntil) {
        this.getVisitDateUntil = getVisitDateUntil;
    }
}
