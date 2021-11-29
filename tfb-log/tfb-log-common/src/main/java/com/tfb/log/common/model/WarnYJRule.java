package com.tfb.log.common.model;

import com.tfb.log.common.enums.ERuleType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: lipan
 * @Date: 2021/9/14
 * @Description： 简单描述下：「 异常事件识别规则 」
 */
@Getter
@Setter
@ToString
public class WarnYJRule extends WarnRule {

    // 窗口时间（默认5分钟）
    private Integer windowTimeM;

    //窗口允许异常最大次数
    private Integer WindowExceptionMax;

}
