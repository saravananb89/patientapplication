package com.example.patientcatalogue.service.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Role {

    private String roleName;
    private Integer patientAccess;
    private Integer visitAccess;
    private Integer userAccess;
    private Integer roleAccess;
    private Integer deviceAccess;

}
