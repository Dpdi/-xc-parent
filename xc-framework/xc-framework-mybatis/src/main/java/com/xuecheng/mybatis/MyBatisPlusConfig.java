package com.xuecheng.mybatis;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.xuecheng.commons.utils.AuthInfoHolder;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class MyBatisPlusConfig {

    private static String [] tables = new String[]{"tb_dictionary","tb_category", "undo_log","tb_application","tb_company"};

    //注册mybatis的插件
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //多租户插件（必须配置到分页之前）
        interceptor.addInnerInterceptor(tenantLineInnerInterceptor());
        //分页的插件
        interceptor.addInnerInterceptor(paginationInnerInterceptor());
        return interceptor;
    }

    //分页的插件配置
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        return new PaginationInnerInterceptor(DbType.MYSQL);
    }

    //多租户插件
    @Bean
    public TenantLineInnerInterceptor tenantLineInnerInterceptor() {
        return new TenantLineInnerInterceptor(new TenantLineHandler() {
            //获取当前登录用户的机构id
            @Override
            public Expression getTenantId() {
                Long companyId = AuthInfoHolder.getCompanyId();
                if (companyId == null) {
                    return null;
                } else {
                    return new StringValue(companyId.toString());
                }
            }

            //指定数据库表中的企业id字段
            @Override
            public String getTenantIdColumn() {
                return "company_id";
            }

            //判断，当前操作的表，是否需要添加租户条件  true-忽略，false-拼接
            @Override
            public boolean ignoreTable(String tableName) {
                Long companyId = AuthInfoHolder.getCompanyId();
                if (companyId == null) {
                    return true;
                }
                if (ArrayUtil.contains(tables, tableName)) {
                    return true;
                }
                return false;
            }
        });
    }
}