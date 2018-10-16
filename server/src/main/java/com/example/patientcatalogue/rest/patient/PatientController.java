package com.example.patientcatalogue.rest.patient;

import com.example.patientcatalogue.service.patient.Patient;
import com.example.patientcatalogue.service.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class PatientController {

    private PatientService repository;

    @Autowired
    public PatientController(PatientService repository) {
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

    @RequestMapping(method = RequestMethod.GET, value = "/search_lastname/{lastName}")
    public Set<Patient> searchByLastName(@PathVariable String lastName) {
        lastName = appendLike(lastName);
        return repository.findByLastNameLikeIgnoreCase(lastName).stream().collect(Collectors.toSet());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search_firstname/{firstName}")
    public Set<Patient> searchByFirstName(@PathVariable String firstName) {
        firstName = appendLike(firstName);
        return repository.findByFirstNameLikeIgnoreCase(firstName).stream().collect(Collectors.toSet());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search_both/{firstName}/{lastName}")
    public Set<Patient> searchByFirstNameAndLastName(@PathVariable String firstName, @PathVariable String lastName) {
        firstName = appendLike(firstName);
        lastName = appendLike(lastName);
        return repository.findByFirstNameAndLastNameLikeIgnoreCase(firstName, lastName).stream().collect(Collectors.toSet());
    }

    private String appendLike(@PathVariable String name) {
        boolean startsWith = name.startsWith("%");
        if (!startsWith) {
            name = "%" + name;
        }

        boolean endsWith = name.endsWith("%");

        if (!endsWith) {
            name = name + "%";
        }
        return name;
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

