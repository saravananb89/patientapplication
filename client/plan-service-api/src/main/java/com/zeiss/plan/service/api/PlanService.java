package com.zeiss.plan.service.api;

import java.time.LocalDate;
import java.util.List;

public interface PlanService {

    void create(PlanningUnit planningUnit);

    void clear();

    void update(PlanningUnit planningUnit);

    boolean delete(PlanningUnit planningUnit);

    List<? extends PlanningUnit> getPatientPlanningUnitsByDeviceAndDate(String deviceId, LocalDate date);

}
