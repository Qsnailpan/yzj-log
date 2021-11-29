package com.tfb.log.common.model;

import com.tfb.log.common.enums.ERuleType;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: lipan
 * @Date: 2021/9/14
 * @Description： 简单描述下：「 预警事件 」
 */
@Getter
@Setter
public class WarnEvent {

    // 编号
    private String number;

    // 预警内容
    private String warnContent;

    // 预警时间
    private String warnTime;

    // 渠道编号
    private String sysNum;
    // 渠道名称
    private String sysName;


}
