package com.splitwise.Splitwise.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
}