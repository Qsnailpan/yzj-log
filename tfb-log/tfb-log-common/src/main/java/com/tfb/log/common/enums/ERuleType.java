package com.tfb.log.common.enums;

import lombok.Getter;

/**
 * @Author: lipan
 * @Date: 2021/9/16
 * @Description： 简单描述下：「 预警识别方式 」
 */

@Getter
public enum ERuleType {

    REGEX("正则匹配"),
    CONTAIN("包含");

    private String name;

    ERuleType(String name) {
        this.name = name;
    }
}
