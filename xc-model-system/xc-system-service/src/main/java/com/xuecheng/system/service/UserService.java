package com.xuecheng.system.service;

import com.xuecheng.commons.model.dto.LoginDto;
import com.xuecheng.commons.model.vo.ResponseResult;
import com.xuecheng.system.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author itheima
 * @since 2022-09-18
 */
public interface UserService extends IService<User> {

    /**
     * 用户登录
     * @param dto
     * @return
     */
    ResponseResult login(LoginDto dto);
}
