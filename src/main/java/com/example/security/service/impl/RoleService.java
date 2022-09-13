package com.example.security.service.impl;

import com.example.security.model.Role;
import com.example.security.repository.RoleRepository;
import com.example.security.service.IRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements IRoleService {
    private RoleRepository roles;

    private RoleService(RoleRepository roles) {
        super();
        this.roles = roles;
    }
    @Override
    public Role findRoleById(Long id) {
        try{
            return this.roles.findById(id).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Role> findAllRoles() {
        return this.roles.findAll();
    }

    @Override
    public Role createRole(Role role) {
        return this.roles.save(role);
    }

    @Override
    public boolean deleteRole(long id){
        Role r = this.findRoleById(id);
        try {
            this.roles.delete(r);
        }
        catch (Exception e) {
            return false;
        }finally {
            return true;
        }
    }

    @Override
    public Role updateRole(long id, Role role) {
        Role oldRole = this.findRoleById(id);

        if (role ==null) {
            return null;
        }else {
            if (role.getName() != null) {
                oldRole.setName(role.getName());
            }
        }
        return this.roles.save(oldRole);
    }

    @Override
    public Long findRoleByName(String name) {
        return this.roles.findByName(name).getId();
    }
}
