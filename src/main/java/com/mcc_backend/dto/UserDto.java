package com.mcc_backend.dto;

import com.mcc_backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Long roleId;
    private String contactNumber;
    private String driverLicenseNo;
    private Integer status;
    private String imgUrl;
    private String dlUrl;
    private String address;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.contactNumber = user.getMobileNo();
        this.driverLicenseNo = user.getDriverLicNo();
        this.roleId = user.getRole().getId();
        this.status = user.getStatus();
        this.imgUrl = user.getImgUrl();
        this.dlUrl = user.getDlUrl();
        this.address = user.getAddress();
    }

}
