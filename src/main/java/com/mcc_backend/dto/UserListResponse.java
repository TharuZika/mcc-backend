package com.mcc_backend.dto;

import com.mcc_backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserListResponse {
    private List<User> users;
    private long totalCount;
    private int currentPage;
    private int totalPages;
}
