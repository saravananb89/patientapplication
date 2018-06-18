package com.example.patientcatalogue.rest.patient;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.patientcatalogue.service.patient.Patient;
import com.example.patientcatalogue.service.patient.PatientRepository;
import com.example.patientcatalogue.service.visit.PatientVisit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PatientController {

    private PatientRepository repository;

    @Autowired
    public PatientController(PatientRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Set<Patient> all() {
        return repository.all();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Patient get(@PathVariable String id) {
        assert id != null && !id.isEmpty();
        return repository.get(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("PatientVisit ID %s not found.", id)));
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@RequestBody Map<String, Object> body) {
        assert body.containsKey("firstName") && body.containsKey("lastName") && body.containsKey("dob")
                && body.containsKey("age") && body.containsKey("email");
        LinkedHashMap<String, Object> dob = (LinkedHashMap<String, Object>) body.get("dob");
        return repository.create((String) body.get("firstName"), (String) body.get("lastName"),
                LocalDate.of((Integer) dob.get("year"), (Integer) dob.get("monthValue"),
                        (Integer) dob.get("dayOfMonth")),
                (String) body.get("age"), (String) body.get("email"));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public boolean update(@PathVariable String id, @RequestBody Map<String, Object> body) {
        assert id != null && !id.isEmpty();
        assert body.containsKey("firstName") && body.containsKey("lastName") && body.containsKey("dob")
                && body.containsKey("age") && body.containsKey("email");
        LinkedHashMap<String, Object> dob = (LinkedHashMap<String, Object>) body.get("dob");
        return repository.update(id, (String) body.get("firstName"), (String) body.get("lastName"),
                LocalDate.of((Integer) dob.get("year"), (Integer) dob.get("monthValue"),
                        (Integer) dob.get("dayOfMonth")),
                (String) body.get("age"), (String) body.get("email"));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public boolean delete(@PathVariable String id) {
        assert id != null && !id.isEmpty();
        return repository.delete(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search/{lastName}")
    public Set<Patient> searchByLastName(@PathVariable String lastName) {
        return repository.findByLastName(lastName).stream().collect(Collectors.toSet());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search/{firstName}/{lastName}")
    public Set<Patient> searchByFirstNameAndLastName(@PathVariable String firstName, @PathVariable String lastName) {
        return repository.findByFirstNameAndLastName(firstName, lastName).stream().collect(Collectors.toSet());
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void clear() {
        repository.clear();
    }
}

@ControllerAdvice(assignableTypes = PatientController.class)
class PatientControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }
}

