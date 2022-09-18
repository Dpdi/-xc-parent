package com.xuecheng.system.controller;

import com.xuecheng.commons.model.dto.LoginDto;
import com.xuecheng.commons.model.vo.ResponseResult;
import com.xuecheng.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param dto
     * @return
     */
    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginDto dto) {
        return userService.login(dto);
    }
}
