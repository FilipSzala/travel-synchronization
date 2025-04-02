package com.travelsync.traveldatasync.model.user.request;

import lombok.Data;

@Data
public class AddUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String fullName;
    private String companyName;
    private String phoneNumber;
}
