package com.xuecheng.content.service;

import com.xuecheng.commons.model.dto.CourseDto;
import com.xuecheng.commons.model.dto.PageRequest;
import com.xuecheng.commons.model.vo.PageResponseResult;
import com.xuecheng.commons.model.vo.ResponseResult;
import com.xuecheng.content.domain.CourseBase;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程基本信息 服务类
 * </p>
 *
 * @author itheima
 * @since 2022-09-19
 */
public interface CourseBaseService extends IService<CourseBase> {

    /**
     * 分页查询课程列表
     * @param dto
     * @param request
     * @return
     */
    PageResponseResult findAll(CourseDto dto, PageRequest request);
}
