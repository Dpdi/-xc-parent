package com.xuecheng.commons.model.vo;

import lombok.Data;

@Data
public class AuthInfo {

    /**
     * 用户id
     */
    private Long uid;

    /**
     * 公司id
     */
    private Long companyId;

    private String companyName;
}
