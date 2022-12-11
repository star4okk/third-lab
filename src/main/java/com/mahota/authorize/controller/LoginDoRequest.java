package com.mahota.authorize.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class LoginDoRequest {
    private String username;
    private String password;
}
