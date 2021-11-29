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
public class WarnYcRule extends WarnRule {


    // 预警识别方式： 正则，包含...
    private ERuleType type;

    // 规则字符串
    private String matchStr;


}
