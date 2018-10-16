package com.example.patientcatalogue.data.patientplan;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface PlaningDTORepository extends CrudRepository<PlaningUnitDTO, Integer> {

    List<PlaningUnitDTO> findByDeviceIdAndPlanDate(String deviceId, LocalDate date);
}
