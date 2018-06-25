package com.example.patientcatalogue.service.patient;

import com.example.patientcatalogue.data.patient.PatientDTO;
import com.example.patientcatalogue.data.patient.PatientDTORepository;
import com.example.patientcatalogue.data.visit.PatientVisitDTO;
import com.example.patientcatalogue.service.visit.PatientVisit;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


public class PatientRepositoryDatabase implements PatientRepository {

    @Autowired
    private PatientDTORepository patientDTORepository;

    @Override
    public Optional<Patient> get(String id) {
        return patientDTORepository.findById(Integer.parseInt(id)).map(PatientDTO::getPatient);
    }

    @Override
    public Set<Patient> all() {
        Set<Patient> set = new HashSet<>();
        patientDTORepository.findAll().forEach(patientDTO -> set.add(patientDTO.getPatient()));
        return set;
    }

    @Override
    public String create(String firstName, String lastName, LocalDate dob, String age, String email) {
        PatientDTO patientDTO = new PatientDTO(null, firstName, lastName, dob, age, email);
        return String.valueOf(patientDTORepository.save(patientDTO).getId());
    }

    @Override
    public boolean update(String id, String firstName, String lastName, LocalDate dob, String age, String email) {
        PatientDTO patientDTO = new PatientDTO(Integer.parseInt(id), firstName, lastName, dob, age, email);
        patientDTORepository.save(patientDTO);
        return true;
    }

    @Override
    public boolean delete(String id) {
        patientDTORepository.deleteById(Integer.parseInt(id));
        return true;
    }

    @Override
    public void clear() {
        patientDTORepository.deleteAll();
    }

    @Override
    public List<Patient> findByLastName(String lastName) {
        return patientDTORepository.findByLastName(lastName).stream().map(PatientDTO::getPatient).collect(Collectors.toList());
    }

    @Override
    public List<Patient> findByFirstNameAndLastName(String firstName, String lastName) {
        return patientDTORepository.findByFirstNameAndLastName(firstName, lastName).stream().
                map(PatientDTO::getPatient).collect(Collectors.toList());
    }

}