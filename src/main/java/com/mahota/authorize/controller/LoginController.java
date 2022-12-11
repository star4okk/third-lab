package com.mahota.authorize.controller;

import com.mahota.authorize.entity.UserEntity;
import com.mahota.authorize.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
    private final SecurityService securityService;
    private final List<UserEntity> USERS = new ArrayList<>();

    @PostConstruct
    public void init() {
        byte[] salt = securityService.generateSalt();
        byte[] hash = securityService.generateHash("123", salt);

        UserEntity userEntity = new UserEntity()
                .setId(1L)
                .setUsername("maxim")
                .setHash(hash)
                .setSalt(salt);
        USERS.add(userEntity);
    }

    @GetMapping
    public String login() {
        return "login";
    }

    @PostMapping("/do")
    public String loginDo(@ModelAttribute LoginDoRequest request, Model model) {
        boolean isSuccessAuthorize = checkPassword(request);
        if (!isSuccessAuthorize) return "error";

        model.addAttribute("users", USERS);
        return "main";
    }

    public boolean checkPassword(LoginDoRequest user) {
        UserEntity userEntity = USERS.get(0);
        byte[] salt = userEntity.getSalt();
        byte[] hash = securityService.generateHash(user.getPassword(), salt);
        return userEntity.getUsername().equals(user.getUsername()) && Arrays.equals(hash, userEntity.getHash());
    }
}
