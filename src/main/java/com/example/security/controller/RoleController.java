package com.example.security.controller;

import com.example.security.model.Role;
import com.example.security.service.IRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {

    private IRoleService rolesService;

    public RoleController (IRoleService rolesService) {
        super();
        this.rolesService = rolesService;
    }

    @GetMapping("/api/roles")
    public List<Role> findAll () {
        List <Role> roles = this.rolesService.findAllRoles();
        return roles;
    }

    @GetMapping("/api/roles/{id}")
    public ResponseEntity <?> findById (@PathVariable Long id) {
        Role role = this.rolesService.findRoleById(id);
        if (role == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("City with id " + id + " not found");
        }
        return ResponseEntity.ok(role);
    }

    @PostMapping("/api/roles")
    public ResponseEntity <?> create (@RequestBody Role role) {
        Role newRole = this.rolesService.createRole(role);
        return ResponseEntity.ok(newRole);
    }


    @DeleteMapping("/api/roles/{id}")
    public ResponseEntity <?> delete (@PathVariable Long id) {
        boolean result = this.rolesService.deleteRole(id);
        if (result) {
            return ResponseEntity.ok("Role with id " + id + " deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role with id " + id + " not found");
    }

    @PutMapping("/api/roles/{id}")
    public ResponseEntity <?> update (@PathVariable Long id, @RequestBody Role role) {
        Role updatedRole = this.rolesService.updateRole(id, role);
        if (updatedRole == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role with id " + id + " not found");
        }
        return ResponseEntity.ok(updatedRole);
    }
}
