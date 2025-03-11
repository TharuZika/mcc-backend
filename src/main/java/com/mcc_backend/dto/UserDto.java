package com.mcc_backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private String username;
    private String role;
    private String identifier;
    private String firstName;
    private String lastName;

}
