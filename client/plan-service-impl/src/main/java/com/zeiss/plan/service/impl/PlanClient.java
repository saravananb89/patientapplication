package com.zeiss.plan.service.impl;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface PlanClient {

    @RequestLine("POST /plan")
    @Headers("Content-Type: application/json")
    void create(PlanningImpl planningImpl);

    @RequestLine("DELETE /plan")
    void clear();

    @RequestLine("DELETE /plan/{id}")
    boolean deletePlan(@Param("id") String id);

    @RequestLine("PUT /plan/{id}")
    @Headers("Content-Type: application/json")
    void update(@Param("id") String id, PlanningImpl planningImpl);

    @RequestLine("GET /plan/search_both/{deviceId}/{date}")
    List<PlanningImpl> getPatientPlanningUnitsByDeviceAndDate(@Param("deviceId") String deviceId,
                                                              @Param("date") String date);
}
