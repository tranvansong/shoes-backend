package com.example.shoeswebbackend.service;

import com.example.shoeswebbackend.dto.RoleDTO;
import com.example.shoeswebbackend.entity.Role;
import com.example.shoeswebbackend.exception.AppException;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<RoleDTO> getAllRoles();
    Set<RoleDTO> getRolesByUserId(long userId);
    RoleDTO getRoleById(long roleId) throws AppException;
    void saveRole(RoleDTO roleDTO) throws AppException;
    void updateRole(RoleDTO roleDTO, long id) throws AppException;
    void deleteRole(long id);
}
