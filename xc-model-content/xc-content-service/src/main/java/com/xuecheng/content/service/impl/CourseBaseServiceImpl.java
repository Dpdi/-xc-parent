package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.commons.model.dto.CourseDto;
import com.xuecheng.commons.model.dto.PageRequest;
import com.xuecheng.commons.model.vo.AuthInfo;
import com.xuecheng.commons.model.vo.PageResponseResult;
import com.xuecheng.commons.model.vo.ResponseResult;
import com.xuecheng.commons.utils.AuthInfoHolder;
import com.xuecheng.content.domain.CourseBase;
import com.xuecheng.content.mappers.CourseBaseMapper;
import com.xuecheng.content.service.CourseBaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程基本信息 服务实现类
 * </p>
 *
 * @author itheima
 * @since 2022-09-19
 */
@Service
public class CourseBaseServiceImpl extends ServiceImpl<CourseBaseMapper, CourseBase> implements CourseBaseService {

    /**
     * 分页查询课程列表
     * @param dto
     * @param request
     * @return
     */
    @Override
    public PageResponseResult findAll(CourseDto dto, PageRequest request) {

        Integer pageNo = request.getPageNo();
        Integer pageSize = request.getPageSize();
        //1.创建分页对象
        Page<CourseBase> page = new Page<>(pageNo,pageSize);

        //2.设置查询条件
        LambdaQueryWrapper<CourseBase> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(CourseBase::getCompanyId, AuthInfoHolder.getCompanyId());

        String courseName = dto.getCourseName();
        if (StringUtils.isNotEmpty(courseName)) {
            wrapper.like(CourseBase::getName,courseName);
        }
        String teachmode = dto.getTeachmode();
        if (StringUtils.isNotEmpty(teachmode)) {
            wrapper.eq(CourseBase::getTeachmode,teachmode);
        }
        String auditStatus = dto.getAuditStatus();
        if (StringUtils.isNotEmpty(auditStatus)) {
            wrapper.eq(CourseBase::getAuditStatus,auditStatus);
        }

        //3.执行查询
        page = this.page(page,wrapper);

        //4.设置返回结果
        long total = page.getTotal();
        List<CourseBase> records = page.getRecords();
        return new PageResponseResult(total,records);
    }
}
