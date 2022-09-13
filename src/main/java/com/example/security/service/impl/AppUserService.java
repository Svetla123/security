package com.example.security.service.impl;

import com.example.security.model.Comment;
import com.example.security.service.IAppUserSerivce;
import com.example.security.model.AppUser;
import com.example.security.model.Role;
import com.example.security.repository.RoleRepository;
import com.example.security.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service @RequiredArgsConstructor @Transactional @Slf4j
public class AppUserService implements IAppUserSerivce, UserDetailsService {

    private final AppUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public AppUser createUser(AppUser user) {
        log.info("Saving user {} to the database", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to the user", roleName, username);
        AppUser user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public AppUser getUser(String username) {
        log.info("Fetching user {}", username);
       return userRepository.findByUsername(username);
    }

    @Override
    public AppUser findUserById(Long id) {
        log.info("Fetching user {}", id);
        try{
            return this.userRepository.findById(id).get();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<AppUser> findAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public boolean deleteUser(long id) {
        log.info("Deleting user {}", id);
        AppUser user = this.findUserById(id);

        try {
            this.userRepository.delete(user);
        }
        catch (Exception e){
            return false;
        }
        finally {
            return true;
        }
    }
    @Override
    public AppUser updateUser(AppUser user, long id) {
        AppUser oldUser = this.findUserById(id);

        if (oldUser == null) {
            return null;
        } else {
            if (user.getEmail() != null) {
                oldUser.setEmail(user.getEmail());
            }
            if (user.getPassword() != null) {
                oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            if (user.getArrivalSum() != 0) {
                oldUser.setArrivalSum(user.getArrivalSum());
            }
            if (user.getSumPoints() != 0.0) {
                oldUser.setSumPoints(user.getSumPoints());
            }
            if (user.getUsername() != null) {
                oldUser.setUsername(user.getUsername());
            }
            if (user.getUserProfile() != null) {
                oldUser.setUserProfile(user.getUserProfile());
            }
            return this.userRepository.save(oldUser);
        }
    }
}
