package com.example.patientcatalogue.service.user;

import com.example.patientcatalogue.data.user.UserDTO;
import com.example.patientcatalogue.data.user.UserDTORepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UserRepositoryDatabase implements UserRepository {

    @Autowired
    private UserDTORepository userDTORepository;

    @Override
    public Optional<User> get(String userName) {
        return userDTORepository.findById(userName).map(UserDTO::getUser);
    }

    @Override
    public Set<User> all() {
        Set<User> set = new HashSet<>();
        userDTORepository.findAll().forEach(userDTO -> set.add(userDTO.getUser()));
        return set;
    }

    @Override
    public String create(String userName, String password, String preferredLocale, LocalDate lastLogin) {
        UserDTO userDTO = new UserDTO(userName, password, preferredLocale, lastLogin);
        return String.valueOf(userDTORepository.save(userDTO).getUserName());
    }

    @Override
    public boolean update(String userName, String password, String preferredLocale, LocalDate lastLogin) {
        UserDTO userDTO = new UserDTO(userName, password, preferredLocale, lastLogin);
        userDTORepository.save(userDTO);
        return true;
    }

    @Override
    public boolean delete(String userName) {
        userDTORepository.deleteById(userName);
        return true;
    }

    @Override
    public void clear() {
        userDTORepository.deleteAll();
    }

    @Override
    public List<User> findByUserName(String userName) {
        return userDTORepository.findByUserName(userName).stream().
                map(UserDTO::getUser).collect(Collectors.toList());
    }

    @Override
    public List<User> findByUserNameAndPassword(String userName, String password) {
        return userDTORepository.findByUserNameAndPassword(userName, password).stream().
                map(UserDTO::getUser).collect(Collectors.toList());
    }

}

