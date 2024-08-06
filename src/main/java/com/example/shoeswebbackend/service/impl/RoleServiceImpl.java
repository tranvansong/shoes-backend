package com.example.shoeswebbackend.service.impl;

import com.example.shoeswebbackend.dto.RoleDTO;
import com.example.shoeswebbackend.entity.Role;
import com.example.shoeswebbackend.exception.AppException;
import com.example.shoeswebbackend.repository.RoleRepository;
import com.example.shoeswebbackend.service.RoleService;
import com.example.shoeswebbackend.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream().map(mapper::convertRoleToDTO).collect(Collectors.toList());
    }

    @Override
    public Set<RoleDTO> getRolesByUserId(long userId) {
        return roleRepository.findByUsers_Id(userId).stream().map(mapper::convertRoleToDTO).collect(Collectors.toSet());
    }

    @Override
    public RoleDTO getRoleById(long id) throws AppException {
        Role role = roleRepository.findById(id).orElse(null);
        if (role == null) {
            throw new AppException(404, "Role not found");
        }
        return mapper.convertRoleToDTO(role);
    }

    @Override
    public void saveRole(RoleDTO roleDTO) throws AppException {
        if(roleRepository.findByName(roleDTO.getName()) != null) {
            throw new AppException(409, "Role already exists");
        }
        Role role = mapper.convertDTOToRole(roleDTO);
        roleRepository.save(role);
    }

    @Override
    public void updateRole(RoleDTO roleDTO, long id) throws AppException {
        Role role = roleRepository.findById(id).orElse(null);
        if (role == null) {
            throw new AppException(404, "Role not found");
        }
        Role roleExist = roleRepository.findByName(roleDTO.getName());
        if (roleExist != null && !roleExist.getId().equals(role.getId())) {
            throw new AppException(409, "Role already exists");
        }
        role.setName(roleDTO.getName());
        roleRepository.save(role);
    }

    @Override
    public void deleteRole(long id) {
        roleRepository.deleteById(id);
    }
}
