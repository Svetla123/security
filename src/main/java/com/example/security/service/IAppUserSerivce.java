package com.example.security.service;

import com.example.security.model.AppUser;

import java.util.List;

public interface IAppUserSerivce {
    AppUser getUser(String username);

    AppUser findUserById(Long id);
    List<AppUser> findAllUsers();

    boolean deleteUser(long id);

    AppUser createUser(AppUser user);

    void addRoleToUser(String username, String roleName);

    AppUser updateUser(AppUser user, long id);
}
