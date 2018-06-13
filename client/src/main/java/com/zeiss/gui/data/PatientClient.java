package com.zeiss.gui.data;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface PatientClient {
    @RequestLine("GET /{id}")
    Patient getById(@Param("id") String id);

    @RequestLine("GET")
    List<Patient> getAll();

    @RequestLine("POST")
    @Headers("Content-Type: application/json")
    void create(Patient patient);

    @RequestLine("DELETE /{id}")
    boolean delete(@Param("id") String id);

    @RequestLine("PUT /{id}")
    @Headers("Content-Type: application/json")
    void update(@Param("id") String id, Patient patient);

    @RequestLine("GET /search/{lastName}")
    List<Patient> getPatientsByLastNAme(@Param("lastName") String lastName);

    @RequestLine("GET /search/{firstName}/{lastName}")
    List<Patient> getPatientsByFirstNameAndLastNAme(@Param("firstName") String firstName, @Param("lastName") String lastName);

}
