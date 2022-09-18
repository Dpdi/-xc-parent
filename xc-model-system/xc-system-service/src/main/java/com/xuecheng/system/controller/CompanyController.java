package com.xuecheng.system.controller;

import com.xuecheng.commons.enums.ErrorCode;
import com.xuecheng.commons.model.vo.CompanyVo;
import com.xuecheng.commons.model.vo.ResponseResult;
import com.xuecheng.commons.utils.BeanHelper;
import com.xuecheng.commons.utils.JwtUtils;
import com.xuecheng.system.domain.Company;
import com.xuecheng.web.exceptions.BusinessException;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xuecheng.system.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 教育机构前端控制器
 * </p>
 *
 * @author itheima
 */
@Slf4j
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService  companyService;

    /**
     * 查询机构信息
     * @param token
     * @return
     */
    @GetMapping("/mine")
    public ResponseResult mine(@RequestHeader("Authorization") String token) {

        //1.判断token是否合法
        Boolean verifyToken = JwtUtils.verifyToken(token);
        if (!verifyToken) {
            throw new BusinessException(ErrorCode.ERROR);
        }

        //2.拿取token中的企业id
        Claims claims = JwtUtils.parserToken(token).getBody();
        Long companyId = claims.get("companyId", Long.class);

        //3.调用service查询企业信息
        Company company = companyService.getById(companyId);

        //拷贝返回结果
        CompanyVo vo = BeanHelper.copyProperties(company, CompanyVo.class);

        //4.返回结果
        return ResponseResult.okResult(vo);

    }
}
