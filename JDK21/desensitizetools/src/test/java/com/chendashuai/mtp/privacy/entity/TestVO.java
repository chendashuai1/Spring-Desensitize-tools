package com.chendashuai.mtp.privacy.entity;


import com.chendashuai.desensitizetools.annotation.Sensitive;
import lombok.Data;

@Data
public class TestVO {
    private Long id;
    private String username;
    @Sensitive(strategy = "email")
    private String email;
    @Sensitive(strategy = "phone")
    private String phone;
    @Sensitive(strategy = "password")
    private String password;

    public TestVO(Long id, String username, String email, String phone, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }
}
