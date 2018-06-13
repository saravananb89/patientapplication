package com.zeiss.gui.data;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface PatientVisitClient {

    @RequestLine("GET /visit/{id}")
    PatientVisit getById(@Param("id") String id);

    @RequestLine("GET /visit")
    List<PatientVisit> getAll();

    @RequestLine("POST /visit")
    @Headers("Content-Type: application/json")
    void create(PatientVisit patientVisit);

    @RequestLine("DELETE /visit/{id}")
    boolean delete(@Param("id") String id);

    @RequestLine("PUT /visit/{id}")
    @Headers("Content-Type: application/json")
    void update(@Param("id") String id, PatientVisit patientVisit);

    @RequestLine("GET /visit/search/{lastName}")
    List<PatientVisit> getPatientsVisitByLastNAme(@Param("lastName") String lastName);

}
