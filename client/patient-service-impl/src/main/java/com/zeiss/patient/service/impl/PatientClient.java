package com.zeiss.patient.service.impl;

import com.zeiss.patient.service.api.Patient;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface PatientClient {
    @RequestLine("GET /{id}")
    PatientImpl getById(@Param("id") String id);

    @RequestLine("GET")
    List<PatientImpl> getAll();

    @RequestLine("POST")
    @Headers("Content-Type: application/json")
    void create(Patient patient);

    @RequestLine("DELETE /{id}")
    boolean delete(@Param("id") String id);

    @RequestLine("PUT /{id}")
    @Headers("Content-Type: application/json")
    void update(@Param("id") String id, Patient patient);

    @RequestLine("GET /search/{lastName}")
    List<PatientImpl> getPatientsByLastNAme(@Param("lastName") String lastName);

    @RequestLine("GET /search/{firstName}/{lastName}")
    List<PatientImpl> getPatientsByFirstNameAndLastNAme(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @RequestLine("DELETE")
    void clear();
}
