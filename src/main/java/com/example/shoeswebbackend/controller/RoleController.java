package com.example.shoeswebbackend.controller;

import com.example.shoeswebbackend.dto.RoleDTO;
import com.example.shoeswebbackend.exception.AppException;
import com.example.shoeswebbackend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<?> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable long id) {
        try {
            RoleDTO roleDTO = roleService.getRoleById(id);
            return ResponseEntity.ok(roleDTO);
        } catch (AppException e) {
            return ResponseEntity.status(e.getCode()).body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRole(@RequestBody RoleDTO role) {
        try {
            roleService.saveRole(role);
        } catch (AppException e) {
            return ResponseEntity.status(e.getCode()).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Role created successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRole(@RequestBody RoleDTO role, @PathVariable("id") long id) {
        try {
            roleService.updateRole(role, id);
        } catch (AppException e) {
            return ResponseEntity.status(e.getCode()).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Role updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable("id") long id) {
        roleService.deleteRole(id);
        return ResponseEntity.status(HttpStatus.OK).body("Role deleted successfully");
    }
}
