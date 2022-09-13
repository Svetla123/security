package com.example.security.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.security.service.IAppUserSerivce;
import com.example.security.model.AppUser;
import com.example.security.model.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;


@RestController @RequestMapping("/api")
@RequiredArgsConstructor
public class AppUserControlller {
    private final IAppUserSerivce appUserSerivce;

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> findAll(){
        return ResponseEntity.ok().body(appUserSerivce.findAllUsers());
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<?> findById (@PathVariable Long id) {
        AppUser user = this.appUserSerivce.findUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id " + id + " not found");
        }
        return ResponseEntity.ok(user);
    }
    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id) {
        boolean result  = this.appUserSerivce.deleteUser(id);

        if (result) {
            return ResponseEntity.ok("User with id " + id + " deleted");
        } else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id " + id + " not found");
        }
    }

    @PostMapping("/users/")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users").toUriString());
        return ResponseEntity.created(uri).body(appUserSerivce.createUser(user));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUseForm form){
        appUserSerivce.addRoleToUser(form.getUsername(), form.getRoleName());
       return ResponseEntity.ok().build();
    }
    @PutMapping ("/users/{id}")
    public ResponseEntity<?> update (@RequestBody AppUser user, @PathVariable Long id) {
        AppUser updatedUser = this.appUserSerivce.updateUser(user, id);
        if (updatedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id " + id + " not found");
        }
        return ResponseEntity.ok(updatedUser);
    }


    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        System.out.println(request.getHeader(AUTHORIZATION));
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length()); //we remove "Bearer "
                Algorithm algorithm = Algorithm.HMAC256("secrete".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);

                String username = decodedJWT.getSubject();
                AppUser user = appUserSerivce.getUser(username);

                String access_token = JWT.create().withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+10*60*1000))
                        .withIssuer((request.getRequestURL()).toString())
                        .withClaim("roles",user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

//        response.setHeader("access_token",access_token);
//        response.setHeader("refresh_token",refresh_token);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            }catch (Exception e) {
                response.setHeader("error",e.getMessage());
                response.setStatus(FORBIDDEN.value());
//                    response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);

            }
        }else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}

@Data
class RoleToUseForm {
    private String username;
    private String roleName;
}