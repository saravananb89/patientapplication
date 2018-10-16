package com.zeiss.patient.client.gui.delete;

import com.zeiss.patient.client.gui.plan.ChangeType;
import com.zeiss.patient.client.gui.plan.PlanningUnitWrapper;
import com.zeiss.patient.client.gui.plan.TransientModel;

public class PlanDeletion {
    public static void planDeletionDeletionDialog(TransientModel transientModel) {
        PlanningUnitWrapper selectedPlanningUnit = transientModel.getSelectedPlanningUnit();
        selectedPlanningUnit.setChangeType(ChangeType.DELETED);

        if (transientModel.getNewlyCreated().contains(selectedPlanningUnit)) {
            transientModel.getNewlyCreated().remove(selectedPlanningUnit);
            transientModel.getNewlyCreatedDeletedUnits().add(selectedPlanningUnit);
        }
        if (transientModel.getUnchanged().contains(selectedPlanningUnit)) {
            transientModel.getUnchanged().remove(selectedPlanningUnit);
            transientModel.getDeletedUnits().add(selectedPlanningUnit);
        }
        if (transientModel.getUpdatedUnits().contains(selectedPlanningUnit)) {
            transientModel.getUpdatedUnits().remove(selectedPlanningUnit);
            transientModel.getDeletedUnits().add(selectedPlanningUnit);
        }

    }
}
