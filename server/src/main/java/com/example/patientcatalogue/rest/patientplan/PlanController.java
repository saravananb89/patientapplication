package com.example.patientcatalogue.rest.patientplan;

import com.example.patientcatalogue.service.patientplan.PatientPlan;
import com.example.patientcatalogue.service.patientplan.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/plan")
public class PlanController {

    private PlanService repository;

    @Autowired
    public PlanController(PlanService repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Set<PatientPlan> all() {
        return repository.all();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public PatientPlan get(@PathVariable String id) {
        assert id != null && !id.isEmpty();
        return repository.get(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Planing Id ID %s not found.", id)));
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@RequestBody Map<String, Object> body) {
        assert body.containsKey("patientId") && body.containsKey("deviceId") && body.containsKey("planDate")
                && body.containsKey("planTime");
        return repository.create((String) body.get("patientId"), (String) body.get("deviceId"),
                LocalDate.parse((String) body.get("planDate")),
                (String) body.get("planTime"));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public boolean update(@PathVariable String id, @RequestBody Map<String, Object> body) {
        assert id != null && !id.isEmpty();
        assert body.containsKey("patientId") && body.containsKey("deviceId") && body.containsKey("planDate")
                && body.containsKey("planTime");
        return repository.update(id, (String) body.get("patientId"), (String) body.get("deviceId"),
                LocalDate.parse((String) body.get("planDate")),
                (String) body.get("planTime"));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public boolean delete(@PathVariable String id) {
        assert id != null && !id.isEmpty();
        return repository.delete(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search_both/{deviceId}/{date}")
    public Set<PatientPlan> searchByDeviceIdAndPlanDate(@PathVariable String deviceId,
                                                        @PathVariable String date) {
        final List<PatientPlan> planingUnitDTOS = repository.findByDeviceIdAndPlanDate(deviceId, LocalDate.parse(date));
        return planingUnitDTOS.stream().collect(Collectors.toSet());
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

@ControllerAdvice(assignableTypes = PlanController.class)
class PlanControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }
}

