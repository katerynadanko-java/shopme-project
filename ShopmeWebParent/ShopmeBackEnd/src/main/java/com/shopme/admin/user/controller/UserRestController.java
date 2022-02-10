package com.shopme.admin.user.controller;

import com.shopme.admin.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
    @Autowired
    private UserService userService;

    @PostMapping("/users/check_email")
    public String checkDuplicateEmail(@Param("id") Integer id, @Param("email") String email) {
        String response = userService.isEmailUnique(id, email) ? "OK" : "Duplicated";
        System.out.println(response);
        return userService.isEmailUnique(id, email) ? "OK" : "Duplicated";
    }
}
