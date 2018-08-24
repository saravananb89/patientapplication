package com.example.patientcatalogue.rest.role;

import com.example.patientcatalogue.service.Role.Role;
import com.example.patientcatalogue.service.Role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/role")
public class RoleController {

    private RoleService repository;

    @Autowired
    public RoleController(RoleService repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Set<Role> all() {
        return repository.all();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{roleName}")
    public Role get(@PathVariable String roleName) {
        assert roleName != null && !roleName.isEmpty();
        return repository.get(roleName)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User Name %s not found.", roleName)));
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@RequestBody Map<String, Object> body) {
        assert body.containsKey("roleName") && body.containsKey("patientAccess") && body.containsKey("visitAccess")
                && body.containsKey("userAccess") && body.containsKey("roleAccess");
        return repository.create((String) body.get("roleName"), (Integer) body.get("patientAccess"), (Integer) body.get("visitAccess"),
                (Integer) body.get("userAccess"), (Integer) body.get("roleAccess"));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{roleName}")
    public boolean update(@PathVariable String roleName, @RequestBody Map<String, Object> body) {
        assert roleName != null && !roleName.isEmpty();
        assert body.containsKey("roleName") && body.containsKey("patientAccess") && body.containsKey("visitAccess")
                && body.containsKey("userAccess") && body.containsKey("roleAccess");
        return repository.update((String) body.get("roleName"), (Integer) body.get("patientAccess"), (Integer) body.get("visitAccess"),
                (Integer) body.get("userAccess"), (Integer) body.get("roleAccess"));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{roleName}")
    public boolean delete(@PathVariable String roleName) {
        assert roleName != null && !roleName.isEmpty();
        return repository.delete(roleName);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void clearVisit() {
        repository.clear();
    }

}

@ControllerAdvice(assignableTypes = RoleController.class)
class RoleControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }
}
