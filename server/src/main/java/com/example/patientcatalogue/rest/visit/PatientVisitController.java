package com.example.patientcatalogue.rest.visit;

import com.example.patientcatalogue.service.visit.PatientVisit;
import com.example.patientcatalogue.service.visit.PatientVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/visit")
public class PatientVisitController {

    private PatientVisitService repository;

    @Autowired
    public PatientVisitController(PatientVisitService repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Set<PatientVisit> all() {
        return repository.all();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public PatientVisit get(@PathVariable String id) {
        assert id != null && !id.isEmpty();
        return repository.get(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("PatientVisit ID %s not found.", id)));
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@RequestBody Map<String, Object> body) {
        assert body.containsKey("visitPatientFirstName") && body.containsKey("visitPatientLastName") && body.containsKey("patientVisitDate");
        LinkedHashMap<String,Object> patientVisitDate = (LinkedHashMap<String, Object>) body.get("patientVisitDate");
        return repository.create((String)body.get("visitPatientFirstName"),(String)body.get("visitPatientLastName"),
                LocalDate.of((Integer)patientVisitDate.get("year"),(Integer)patientVisitDate.get("monthValue"),
                        (Integer)patientVisitDate.get("dayOfMonth")));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public boolean update(@PathVariable String id, @RequestBody Map<String, Object> body) {
        assert id != null && !id.isEmpty();
        assert body.containsKey("visitPatientFirstName") && body.containsKey("visitPatientLastName") && body.containsKey("patientVisitDate");
        LinkedHashMap<String,Object> patientVisitDate = (LinkedHashMap<String, Object>) body.get("patientVisitDate");
        return repository.update(id, (String)body.get("visitPatientFirstName"),(String)body.get("visitPatientLastName"),
                LocalDate.of((Integer)patientVisitDate.get("year"),(Integer)patientVisitDate.get("monthValue"),
                        (Integer)patientVisitDate.get("dayOfMonth")));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public boolean delete(@PathVariable String id) {
        assert id != null && !id.isEmpty();
        return repository.delete(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search/{visitPatientLastName}")
    public Set<PatientVisit> searchByLastName(@PathVariable String visitPatientLastName) {
        return repository.findByLastName(visitPatientLastName).stream().collect(Collectors.toSet());
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void clearVisit() {
        repository.clear();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search/{firstName}/{lastName}")
    public Set<PatientVisit> searchByFirstNameAndLastName(@PathVariable String firstName, @PathVariable String lastName) {
        return repository.findByFirstNameAndLastName(firstName, lastName).stream().collect(Collectors.toSet());
    }

}

@ControllerAdvice(assignableTypes = PatientVisitController.class)
class PatientVisitControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }
}

