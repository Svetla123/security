package com.example.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long Id;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column (nullable = false)
    private String username;
    @ColumnDefault("0")
    private int arrivalSum;
    @ColumnDefault("0")
    private double sumPoints;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles=new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "userProfile_id")
    private UserProfile userProfile;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserTest> userTest;
    @OneToMany(mappedBy = "corrector")
    @JsonIgnore
    private List<UserTest> correctorTest;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserArrival> userArrivals;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Comment> comments;
}