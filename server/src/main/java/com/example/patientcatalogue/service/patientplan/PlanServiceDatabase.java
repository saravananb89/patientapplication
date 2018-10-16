package com.example.patientcatalogue.service.patientplan;

import com.example.patientcatalogue.data.patientplan.PlaningDTORepository;
import com.example.patientcatalogue.data.patientplan.PlaningUnitDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class PlanServiceDatabase implements PlanService {

    @Autowired
    private PlaningDTORepository planDTORepository;

    @Override
    public Optional<PatientPlan> get(String id) {
        return planDTORepository.findById(Integer.parseInt(id)).map(PlaningUnitDTO::getPatientPlan);
    }

    @Override
    public Set<PatientPlan> all() {
        Set<PatientPlan> set = new HashSet<>();
        planDTORepository.findAll().forEach(planingUnitDTO -> set.add(planingUnitDTO.getPatientPlan()));
        return set;
    }

    @Override
    public String create(String patientId, String deviceId, LocalDate date, String time) {
        PlaningUnitDTO planingUnitDTO = new PlaningUnitDTO(null, patientId, deviceId, date, time);
        return String.valueOf(planDTORepository.save(planingUnitDTO).getDeviceId());
    }

    @Override
    public boolean update(String id, String patientId, String deviceId, LocalDate planDate, String time) {
        PlaningUnitDTO planingUnitDTO = new PlaningUnitDTO(Integer.parseInt(id), patientId, deviceId, planDate, time);
        planDTORepository.save(planingUnitDTO);
        return true;
    }

    @Override
    public boolean delete(String id) {
        planDTORepository.deleteById(Integer.parseInt(id));
        return true;
    }

    @Override
    public void clear() {
        planDTORepository.deleteAll();
    }

    @Override
    public List<PatientPlan> findByDeviceIdAndPlanDate(String deviceId, LocalDate date) {
        return planDTORepository.findByDeviceIdAndPlanDate(deviceId, date).stream().
                map(PlaningUnitDTO::getPatientPlan).collect(Collectors.toList());
    }


}

