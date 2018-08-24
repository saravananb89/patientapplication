package com.example.patientcatalogue.service.visit;

import com.example.patientcatalogue.data.visit.PatientVisitDTO;
import com.example.patientcatalogue.data.visit.PatientVisitDTORepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


public class PatientVisitServiceDatabase implements PatientVisitService {

    @Autowired
    private PatientVisitDTORepository patientVisitDTORepository;

    @Override
    public Optional<PatientVisit> get(String id) {
        return patientVisitDTORepository.findById(Integer.parseInt(id)).map(PatientVisitDTO::getPatientVisit);
    }

    @Override
    public Set<PatientVisit> all() {
        Set<PatientVisit> set = new HashSet<>();
        patientVisitDTORepository.findAll().forEach(patientVisitDTO -> set.add(patientVisitDTO.getPatientVisit()));
        return set;
    }

    @Override
    public String create(String firstName, String lastName, LocalDate visitDate) {
        PatientVisitDTO patientVisitDTO = new PatientVisitDTO(null, firstName, lastName, visitDate);
        return String.valueOf(patientVisitDTORepository.save(patientVisitDTO).getId());
    }

    @Override
    public boolean update(String id, String firstName, String lastName, LocalDate visitDate) {
        PatientVisitDTO patientVisitDTO = new PatientVisitDTO(Integer.parseInt(id), firstName, lastName, visitDate);
        patientVisitDTORepository.save(patientVisitDTO);
        return true;
    }

    @Override
    public boolean delete(String id) {
        patientVisitDTORepository.deleteById(Integer.parseInt(id));
        return true;
    }

    @Override
    public void clear() {
        patientVisitDTORepository.deleteAll();
    }

    @Override
    public List<PatientVisit> findByLastName(String lastName) {
        return patientVisitDTORepository.findByLastName(lastName).stream().map(PatientVisitDTO::getPatientVisit).collect(Collectors.toList());
    }

    @Override
    public List<PatientVisit> findByFirstNameAndLastName(String firstName, String lastName) {
        return patientVisitDTORepository.findByFirstNameAndLastName(firstName, lastName).stream().
                map(PatientVisitDTO::getPatientVisit).collect(Collectors.toList());
    }

}
