package com.zeiss.patient.service.impl;

import com.zeiss.patient.service.api.Patient;
import com.zeiss.patient.service.api.PatientService;
import com.zeiss.patient.service.api.PatientVisit;
import feign.Feign;
import feign.httpclient.ApacheHttpClient;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

import java.util.List;

public class DefaultPatientService implements PatientService {

    private PatientClient patientClient;

    private PatientVisitClient patientVisitClient;

    public DefaultPatientService() {
        patientClient = Feign.builder().client(new ApacheHttpClient())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(PatientClient.class, "http://localhost:1234");

        patientVisitClient = Feign.builder().client(new ApacheHttpClient())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(PatientVisitClient.class, "http://localhost:1234");
    }


    public List<? extends Patient> getPatients() {

        return patientClient.getAll();
    }

    public List<? extends PatientVisit> getPatientVisits(){
        return patientVisitClient.getAll();
    }

    public boolean delete(Patient patient) {
        System.out.println("Patient wurde gelöscht " + patient);
        return patientClient.delete(patient.getId());
    }

    public boolean deleteVisit(PatientVisit patientVisit)
    {
        System.out.println("Patient wurde gelöscht " + patientVisit);

       return patientVisitClient.delete(patientVisit.getId());

    }

    public void create(Patient patient) {
        System.out.println("Patient is created " + patient);
        patientClient.create(patient);
    }

    public void createVisit(PatientVisit patientVisit)
    {
        System.out.println("Patient is created " + patientVisit);
        patientVisitClient.create(patientVisit);
    }

    public void update(Patient patient) {
        System.out.println("Patient is updated " + patient);
        patientClient.update(patient.getId(), patient);
    }

    public void updateVisit(PatientVisit patientVisit) {
        System.out.println("Patient is updated " + patientVisit);
        patientVisitClient.update(patientVisit.getId(), patientVisit);
    }

    public List<? extends Patient> getPatientsByLastName(String lastName) {

        return patientClient.getPatientsByLastNAme(lastName);
    }

    public List<? extends PatientVisit> getVisitPatientsByLastName(String lastName) {

        return patientVisitClient.getPatientsVisitByLastNAme(lastName);
    }

    public List<? extends Patient> getPatientsByFirstNameAndLastName(String firstName, String lastName) {

        return patientClient.getPatientsByFirstNameAndLastNAme(firstName,lastName);
    }

    public List<? extends PatientVisit> getVisitPatientsByFirstNameAndLastName(String firstName, String lastName) {

        return patientVisitClient.getVisitPatientsByFirstNameAndLastName(firstName,lastName);
    }

    @Override
    public void clear() {
        patientClient.clear();
    }

    @Override
    public void clearVisit() {
        patientVisitClient.clearVisit();
    }
}
