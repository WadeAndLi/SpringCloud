package com.wade.controller;

import com.wade.common.CookieUtils;
import com.wade.po.UserPO;
import com.wade.type.CheckTypeEnum;
import com.wade.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${fly.jwt.cookieName}")
    private String cookieName;

    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> checkDataType(@PathVariable("data")String data, @PathVariable("type") Integer type) {

        return ResponseEntity.ok(userService.checkDataType(data, CheckTypeEnum.getCheckEnum(type)));
    }

    @PostMapping("/user/create")
    public ResponseEntity<Integer> createUser(@RequestBody UserPO userPO) {

        return ResponseEntity.ok(0);
    }

    @PostMapping("code")
    public ResponseEntity<Void> sendCode(@RequestParam("phone") String phone) {
        userService.sendCode(phone);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("login")
    public ResponseEntity<Void> loginUser(@RequestParam("username")String username,
                                          @RequestParam("password")String password,
                                          HttpServletRequest request,
                                          HttpServletResponse response
    ) {
        String token = userService.loginUser(username, password);
        CookieUtils.setCookie(request, response, cookieName, token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
