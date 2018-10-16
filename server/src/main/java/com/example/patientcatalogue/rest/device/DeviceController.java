package com.example.patientcatalogue.rest.device;

import com.example.patientcatalogue.rest.role.RoleController;
import com.example.patientcatalogue.service.device.Device;
import com.example.patientcatalogue.service.device.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/device")
public class DeviceController {


    private DeviceService repository;

    @Autowired
    public DeviceController(DeviceService repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Set<Device> all() {
        return repository.all();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{deviceName}")
    public Device get(@PathVariable String deviceName) {
        assert deviceName != null && !deviceName.isEmpty();
        return repository.get(deviceName)
                .orElseThrow(() -> new IllegalArgumentException(String.format("PatientPlan Name %s not found.", deviceName)));
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@RequestBody Map<String, Object> body) {
        assert body.containsKey("deviceName") && body.containsKey("host") && body.containsKey("port");
        return repository.create((String) body.get("deviceName"), (String) body.get("host"), (Integer) body.get("port"));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{deviceName}")
    public boolean update(@PathVariable String deviceName, @RequestBody Map<String, Object> body) {
        assert deviceName != null && !deviceName.isEmpty();
        assert body.containsKey("deviceName") && body.containsKey("host") && body.containsKey("port");
        return repository.update((String) body.get("deviceName"), (String) body.get("host"), (Integer) body.get("port"));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{deviceName}")
    public boolean delete(@PathVariable String deviceName) {
        assert deviceName != null && !deviceName.isEmpty();
        return repository.delete(deviceName);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void clearVisit() {
        repository.clear();
    }

}

@ControllerAdvice(assignableTypes = RoleController.class)
class DeviceControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }
}
