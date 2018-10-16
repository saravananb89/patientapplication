package com.zeiss.patient.client.gui.update;

import com.zeiss.patient.client.gui.plan.PlanningUnitTimePicker;
import com.zeiss.plan.service.api.PlanService;
import com.zeiss.plan.service.api.PlanningUnit;

public class PlanUpdate extends PlanningUnitTimePicker {
    protected void save(PlanService planService, PlanningUnit planningUnit) {
        planService.update(planningUnit);
    }
}
