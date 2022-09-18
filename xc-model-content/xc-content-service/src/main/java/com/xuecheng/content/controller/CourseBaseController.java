package com.xuecheng.content.controller;

import com.xuecheng.commons.model.dto.CourseDto;
import com.xuecheng.commons.model.dto.PageRequest;
import com.xuecheng.commons.model.vo.PageResponseResult;
import com.xuecheng.commons.model.vo.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xuecheng.content.service.CourseBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 课程基本信息前端控制器
 * </p>
 *
 * @author itheima
 */
@Slf4j
@RestController
@RequestMapping("/course")
public class CourseBaseController {

    @Autowired
    private CourseBaseService  courseBaseService;

    /**
     * 分页查询课程列表
     * @param dto
     * @param request
     * @return
     */
    @PostMapping("/list")
    public PageResponseResult list(@RequestBody CourseDto dto, PageRequest request) {
        return courseBaseService.findAll(dto,request);
    }
}
