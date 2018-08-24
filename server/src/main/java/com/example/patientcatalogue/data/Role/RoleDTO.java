package com.example.patientcatalogue.data.Role;

import com.example.patientcatalogue.service.Role.Role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "roletable")
public class RoleDTO {

    @Column(name = "roleName", length = 20)
    @Id
    private String roleName;
    @Column(name = "patientAccess")
    private Integer patientAccess;
    @Column(name = "visitAccess")
    private Integer visitAccess;
    @Column(name = "userAccess")
    private Integer userAccess;
    @Column(name = "roleAccess")
    private Integer roleAccess;

    public RoleDTO() {
    }

    public RoleDTO(String roleName, Integer patientAccess, Integer visitAccess, Integer userAccess, Integer roleAccess) {
        this.roleName = roleName;
        this.patientAccess = patientAccess;
        this.visitAccess = visitAccess;
        this.userAccess = userAccess;
        this.roleAccess = roleAccess;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getPatientAccess() {
        return patientAccess;
    }

    public void setPatientAccess(Integer patientAccess) {
        this.patientAccess = patientAccess;
    }

    public Integer getVisitAccess() {
        return visitAccess;
    }

    public void setVisitAccess(Integer visitAccess) {
        this.visitAccess = visitAccess;
    }

    public Integer getUserAccess() {
        return userAccess;
    }

    public void setUserAccess(Integer userAccess) {
        this.userAccess = userAccess;
    }

    public Integer getRoleAccess() {
        return roleAccess;
    }

    public void setRoleAccess(Integer roleAccess) {
        this.roleAccess = roleAccess;
    }

    public Role getRole() {
        return new Role(roleName, patientAccess, visitAccess, userAccess, roleAccess);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDTO roleDTO = (RoleDTO) o;
        return Objects.equals(roleName, roleDTO.roleName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(roleName);
    }
}
