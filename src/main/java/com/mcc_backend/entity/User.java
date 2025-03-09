package com.mcc_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    private String mobileNo;
    private String driverLicNo;
    private boolean isOnline;
    private boolean onTrip;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    private String imgUrl;
}
