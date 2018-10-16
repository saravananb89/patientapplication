package com.example.patientcatalogue.service.Role;

import com.example.patientcatalogue.data.Role.RoleDTO;
import com.example.patientcatalogue.data.Role.RoleDTORepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class RoleServiceDatabase implements RoleService {

    @Autowired
    private RoleDTORepository roleDTORepository;

    @Override
    public Optional<Role> get(String roleName) {
        return roleDTORepository.findById(roleName).map(RoleDTO::getRole);
    }

    @Override
    public Set<Role> all() {
        Set<Role> set = new HashSet<>();
        roleDTORepository.findAll().forEach(roleDTO -> set.add(roleDTO.getRole()));
        return set;
    }

    @Override
    public String create(String roleName, Integer patientAccess, Integer visitAccess, Integer userAccess,
                         Integer roleAccess, Integer deviceAccess) {
        RoleDTO roleDTO = new RoleDTO(roleName, patientAccess, visitAccess, userAccess, roleAccess, deviceAccess);
        return String.valueOf(roleDTORepository.save(roleDTO).getRoleName());
    }

    @Override
    public boolean update(String roleName, Integer patientAccess, Integer visitAccess, Integer userAccess,
                          Integer roleAccess, Integer deviceAccess) {
        RoleDTO roleDTO = new RoleDTO(roleName, patientAccess, visitAccess, userAccess, roleAccess, deviceAccess);
        roleDTORepository.save(roleDTO);
        return true;
    }

    @Override
    public boolean delete(String roleName) {
        roleDTORepository.deleteById(roleName);
        return true;
    }

    @Override
    public void clear() {
        roleDTORepository.deleteAll();
    }

}

