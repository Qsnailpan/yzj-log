package com.tfb.log.common.model;

import com.tfb.log.common.enums.ERuleType;
import lombok.*;

/**
 * @Author: lipan
 * @Date: 2021/9/14
 * @Description： 简单描述下：「 异常事件识别规则 」
 */
@Getter
@Setter
@ToString
public class WarnRule {

    // 规则编号
    private String number;

    // 异常规则描述
    private String describe;

    // 规则是否启用
    private Boolean enable;

    // 渠道编号
    private String sysNum;
    // 渠道名称
    private String sysName;

}
