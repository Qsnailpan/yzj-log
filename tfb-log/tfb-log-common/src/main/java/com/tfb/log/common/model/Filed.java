package com.tfb.log.common.model;

import lombok.*;

/**
 * @Author: lipan
 * @Date: 2021/9/14
 * @Description： 简单描述下：「 TODO 」
 */
@Getter
@Setter
public class Filed {
    // 应用名称
    private String app;
    // 日志类型
    private String logtype;
    // 应用所在主机名
    private String hostname;
    // 应用所在主机ip
    private String ip;
    // 渠道编号
    private String sysNum;
    // 渠道名称
    private String sysName;
}
