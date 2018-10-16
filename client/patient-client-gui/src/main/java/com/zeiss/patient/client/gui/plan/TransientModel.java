package com.zeiss.patient.client.gui.plan;

import com.google.inject.Singleton;
import com.zeiss.plan.service.api.PlanningUnit;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalTime;

@Singleton
public class TransientModel {

    private final ListProperty<PlanningUnitWrapper> unchanged = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ListProperty<PlanningUnitWrapper> newlyCreated = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ListProperty<PlanningUnitWrapper> deletedUnits = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ListProperty<PlanningUnitWrapper> newlyCreatedDeletedUnits = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ListProperty<PlanningUnitWrapper> updatedUnits = new SimpleListProperty<>(FXCollections.observableArrayList());
    private ObjectProperty<PlanningUnitWrapper> selectedPlanningUnit = new SimpleObjectProperty<>();

    private BooleanProperty deletionImPossible = new SimpleBooleanProperty();
    private BooleanProperty updateImPossible = new SimpleBooleanProperty();

    public TransientModel() {
        clearModel();
        deletionImPossible.bind(selectedPlanningUnit.isNull());
        updateImPossible.bind(selectedPlanningUnit.isNull());
    }

    public void clearModel() {
        unchanged.clear();
        newlyCreated.clear();
        deletedUnits.clear();
        updatedUnits.clear();
        newlyCreatedDeletedUnits.clear();
    }

    public void clearSelection() {
        this.selectedPlanningUnit.set(null);
    }

    public PlanningUnitWrapper getSelectedPlanningUnit() {
        return selectedPlanningUnit.get();
    }

    public ObjectProperty<PlanningUnitWrapper> selectedPlanningUnitProperty() {
        return selectedPlanningUnit;
    }

    public void setSelectedPlanningUnit(PlanningUnitWrapper selectedPlanningUnit) {
        this.selectedPlanningUnit.set(selectedPlanningUnit);
    }

    public ObservableList<PlanningUnitWrapper> getUnchanged() {
        return unchanged.get();
    }

    public ListProperty<PlanningUnitWrapper> unchangedProperty() {
        return unchanged;
    }

    public void setUnchanged(ObservableList<PlanningUnitWrapper> unchanged) {
        this.unchanged.set(unchanged);
    }

    public ObservableList<PlanningUnitWrapper> getNewlyCreated() {
        return newlyCreated.get();
    }

    public ListProperty<PlanningUnitWrapper> newlyCreatedProperty() {
        return newlyCreated;
    }

    public void setNewlyCreated(ObservableList<PlanningUnitWrapper> newlyCreated) {
        this.newlyCreated.set(newlyCreated);
    }

    public ObservableList<PlanningUnitWrapper> getDeletedUnits() {
        return deletedUnits.get();
    }

    public ListProperty<PlanningUnitWrapper> deletedUnitsProperty() {
        return deletedUnits;
    }

    public void setDeletedUnits(ObservableList<PlanningUnitWrapper> deletedUnits) {
        this.deletedUnits.set(deletedUnits);
    }

    public ObservableList<PlanningUnitWrapper> getUpdatedUnits() {
        return updatedUnits.get();
    }

    public ListProperty<PlanningUnitWrapper> updatedUnitsProperty() {
        return updatedUnits;
    }

    public void setUpdatedUnits(ObservableList<PlanningUnitWrapper> updatedUnits) {
        this.updatedUnits.set(updatedUnits);
    }

    public boolean isDeletionImPossible() {
        return deletionImPossible.get();
    }

    public BooleanProperty deletionImPossibleProperty() {
        return deletionImPossible;
    }

    public void setDeletionImPossible(boolean deletionImPossible) {
        this.deletionImPossible.set(deletionImPossible);
    }

    public boolean isUpdateImPossible() {
        return updateImPossible.get();
    }

    public BooleanProperty updateImPossibleProperty() {
        return updateImPossible;
    }

    public void setUpdateImPossible(boolean updateImPossible) {
        this.updateImPossible.set(updateImPossible);
    }

    public ObservableList<PlanningUnitWrapper> getNewlyCreatedDeletedUnits() {
        return newlyCreatedDeletedUnits.get();
    }

    public ListProperty<PlanningUnitWrapper> newlyCreatedDeletedUnitsProperty() {
        return newlyCreatedDeletedUnits;
    }

    public void setNewlyCreatedDeletedUnits(ObservableList<PlanningUnitWrapper> newlyCreatedDeletedUnits) {
        this.newlyCreatedDeletedUnits.set(newlyCreatedDeletedUnits);
    }

    public boolean isTimeAvailable(String deviceId, LocalDate date, String time) {


        ObservableList<PlanningUnit> availableUnits = FXCollections.observableArrayList();
        availableUnits.addAll(unchanged);
        availableUnits.addAll(updatedUnits);
        availableUnits.addAll(newlyCreated);

        boolean possible;

        possible = availableUnits.stream().filter(planningUnit -> {
            LocalTime plusMinutes = LocalTime.parse(time).plusMinutes(20);
            LocalTime minusMinutes = LocalTime.parse(time).minusMinutes(20);
            LocalTime localTime = LocalTime.parse(planningUnit.getPlanTime());
            if (planningUnit.getDevice().getDeviceName().equals(deviceId) && planningUnit.getPlanDate().isEqual(date) &&
                    ((plusMinutes.isAfter(localTime) && minusMinutes.isBefore(localTime)))) {
                return true;
            } else return false;
        }).count() > 0;

        return possible;

    }
}
