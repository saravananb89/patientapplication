package com.example.patientcatalogue.data.patientplan;

import com.example.patientcatalogue.service.patientplan.PatientPlan;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "planingunittable")
public class PlaningUnitDTO {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "patientId")
    private String patientId;
    @Column(name = "deviceId")
    private String deviceId;
    @Column(name = "planDate")
    private LocalDate planDate;
    @Column(name = "planTime")
    private String planTime;

    public PlaningUnitDTO(Integer id, String patientId, String deviceId, LocalDate planDate, String planTime) {
        this.id = id;
        this.patientId = patientId;
        this.deviceId = deviceId;
        this.planDate = planDate;
        this.planTime = planTime;
    }

    public PlaningUnitDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public LocalDate getPlanDate() {
        return planDate;
    }

    public void setPlanDate(LocalDate planDate) {
        this.planDate = planDate;
    }

    public String getPlanTime() {
        return planTime;
    }

    public void setPlanTime(String planTime) {
        this.planTime = planTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaningUnitDTO that = (PlaningUnitDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    public PatientPlan getPatientPlan() {
        return new PatientPlan(id, patientId, deviceId, planDate, planTime);
    }

    @Override
    public String toString() {
        return "PlaningUnitDTO{" +
                "id=" + id +
                ", patientId='" + patientId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", planDate=" + planDate +
                ", planTime='" + planTime + '\'' +
                '}';
    }
}
