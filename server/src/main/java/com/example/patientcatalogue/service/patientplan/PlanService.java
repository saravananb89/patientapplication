package com.example.patientcatalogue.service.patientplan;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PlanService {

    /**
     *
     * @param id
     * @return
     */
    Optional<PatientPlan> get(String id);

    /**
     *
     * @return
     */
    Set<PatientPlan> all();

    /**
     *
     * @param patientId
     * @param deviceId
     * @param date
     * @param time
     * @return
     */
    String create(String patientId, String deviceId, LocalDate date, String time);

    /**
     *
     * @param patientId
     * @param deviceId
     * @param date
     * @param time
     * @return
     */
    boolean update(String id, String patientId, String deviceId, LocalDate date, String time);

    /**
     *
     * @param id
     * @return
     */
    boolean delete(String id);

    /**
     *
     */
    void clear();

    /**
     *
     * @param deviceId
     * @param date
     * @return
     */
    List<PatientPlan> findByDeviceIdAndPlanDate(String deviceId, LocalDate date);

}
