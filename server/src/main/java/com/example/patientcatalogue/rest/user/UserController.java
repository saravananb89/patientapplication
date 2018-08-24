package com.example.patientcatalogue.rest.user;

import com.example.patientcatalogue.service.user.User;
import com.example.patientcatalogue.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService repository;

    @Autowired
    public UserController(UserService repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Set<User> all() {
        return repository.all();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userName}")
    public User get(@PathVariable String userName) {
        assert userName != null && !userName.isEmpty();
        return repository.get(userName)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User Name %s not found.", userName)));
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@RequestBody Map<String, Object> body) {
        assert body.containsKey("userName") && body.containsKey("password") && body.containsKey("preferredLocale")
                && body.containsKey("lastLogin");
        LinkedHashMap<String, Object> lastLogin = (LinkedHashMap<String, Object>) body.get("lastLogin");
        LocalDate localDate = lastLogin == null ? null : LocalDate.of((Integer) lastLogin.get("year"), (Integer) lastLogin.get("monthValue"),
                (Integer) lastLogin.get("dayOfMonth"));
        return repository.create((String) body.get("userName"), (String) body.get("password"), (String) body.get("preferredLocale"),
                localDate);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{userName}")
    public boolean update(@PathVariable String userName, @RequestBody Map<String, Object> body) {
        assert userName != null && !userName.isEmpty();
        assert body.containsKey("userName") && body.containsKey("password") && body.containsKey("preferredLocale")
                && body.containsKey("lastLogin");
        LinkedHashMap<String, Object> lastLogin = (LinkedHashMap<String, Object>) body.get("lastLogin");
        LocalDate localDate = lastLogin == null ? null : LocalDate.of((Integer) lastLogin.get("year"), (Integer) lastLogin.get("monthValue"),
                (Integer) lastLogin.get("dayOfMonth"));
        return repository.update((String) body.get("userName"), (String) body.get("password"),
                (String) body.get("preferredLocale"),
                localDate);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{userName}")
    public boolean delete(@PathVariable String userName) {
        assert userName != null && !userName.isEmpty();
        return repository.delete(userName);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void clearVisit() {
        repository.clear();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search/{userName}")
    public Set<User> searchByUserName(@PathVariable String userName) {
        return repository.findByUserName(userName).stream().collect(Collectors.toSet());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search/{userName}/{password}")
    public Set<User> searchByUserNameAndPassword(@PathVariable String userName, @PathVariable String password) {
        return repository.findByUserNameAndPassword(userName, password).stream().collect(Collectors.toSet());
    }

}

@ControllerAdvice(assignableTypes = UserController.class)
class UserControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }
}
