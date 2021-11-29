package com.tfb.log.common.enums;

import lombok.Getter;

/**
 * @Author: lipan
 * @Date: 2021/9/16
 * @Description： 简单描述下：「 应用来源 」
 */

@Getter
public enum EAppType {

    YARN("yarn 程序"),
    RISK("risk 风控"),
    LOG("日志监控"),
    SPARK("Spark 程序"),
    CDH("CDH 平台");

    private String name;

    EAppType(String name) {
        this.name = name;
    }
}
