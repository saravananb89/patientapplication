package com.zeiss.patient.service.impl;

import com.zeiss.patient.service.api.PatientVisit;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface PatientVisitClient {

    @RequestLine("GET /visit/{id}")
    PatientVisitImpl getById(@Param("id") String id);

    @RequestLine("GET /visit")
    List<PatientVisitImpl> getAll();

    @RequestLine("POST /visit")
    @Headers("Content-Type: application/json")
    void create(PatientVisit patientVisit);

    @RequestLine("DELETE /visit/{id}")
    boolean delete(@Param("id") String id);

    @RequestLine("PUT /visit/{id}")
    @Headers("Content-Type: application/json")
    void update(@Param("id") String id, PatientVisit patientVisit);

    @RequestLine("GET /visit/search/{lastName}")
    List<PatientVisitImpl> getPatientsVisitByLastNAme(@Param("lastName") String lastName);

    @RequestLine("GET /visit/search/{firstName}/{lastName}")
    List<PatientVisitImpl> getVisitPatientsByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);


    @RequestLine("DELETE /visit")
    void clearVisit();

}
