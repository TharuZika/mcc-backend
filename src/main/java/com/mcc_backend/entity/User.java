package com.mcc_backend.entity;

import com.mcc_backend.dto.UserDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;
    
    private String mobileNo;
    private String driverLicNo;
    private boolean isOnline;
    private boolean onTrip;
    private Integer status;
    private String address;
    private String dlUrl;
    private String imgUrl;

    public User(UserDto user) {
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.mobileNo = user.getContactNumber();
        this.driverLicNo = user.getDriverLicenseNo();
        this.status = user.getStatus();
        this.imgUrl = user.getImgUrl();
        this.dlUrl = user.getDlUrl();
        this.address = user.getAddress();
    }

    public void updateUserDetails(UserDto userDetails) {
        this.firstName = userDetails.getFirstName();
        this.lastName = userDetails.getLastName();
        this.email = userDetails.getEmail();
        this.mobileNo = userDetails.getContactNumber();
        this.driverLicNo = userDetails.getDriverLicenseNo();
    }
}
