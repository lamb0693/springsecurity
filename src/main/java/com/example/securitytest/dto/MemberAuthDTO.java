package com.example.securitytest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberAuthDTO {
    private String username;

    private String password;

    @Override
    public String toString() {
        return "MemberAuthDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
