package com.xuecheng.system.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.commons.enums.ErrorCode;
import com.xuecheng.commons.model.dto.LoginDto;
import com.xuecheng.commons.model.vo.LoginVo;
import com.xuecheng.commons.model.vo.ResponseResult;
import com.xuecheng.commons.utils.JwtUtils;
import com.xuecheng.system.domain.User;
import com.xuecheng.system.mappers.UserMapper;
import com.xuecheng.system.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuecheng.web.exceptions.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author itheima
 * @since 2022-09-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseResult login(LoginDto dto) {

        //1. 验证数据是否为空
        String username = dto.getUsername();
        String password = dto.getPassword();
        String utype = dto.getUtype();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(utype)) {
            throw new BusinessException(ErrorCode.LOGINERROR);
        }

        //2.根据手机号用户类型查找数据
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone,username).eq(User::getUtype,utype);
        User user = userMapper.selectOne(wrapper);

        //3.判断用户是否存在
        if (user == null) {
            throw new BusinessException(ErrorCode.LOGINERROR);
        }

        //4.判断密码是否正确
        String dbPassword = user.getPassword();
        password = SecureUtil.md5(password);
        if (!dbPassword.equals(password)) {
            throw new BusinessException(ErrorCode.LOGINERROR);
        }

        //5.准备返回数据
        Map<String,Object> map = new HashMap<>();
        map.put("userId",user.getId());
        map.put("companyId",user.getCompanyId());
        map.put("companyName",user.getCompanyName());
        String token = JwtUtils.createToken(map, 240);

        LoginVo vo = new LoginVo(token, user.getName());

        return ResponseResult.okResult(vo);
    }
}
