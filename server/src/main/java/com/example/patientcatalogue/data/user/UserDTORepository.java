package com.example.patientcatalogue.data.user;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDTORepository extends CrudRepository<UserDTO, String> {

    List<UserDTO> findByUserName(String userName);

    List<UserDTO> findByUserNameAndPassword(String userName, String password);

}
