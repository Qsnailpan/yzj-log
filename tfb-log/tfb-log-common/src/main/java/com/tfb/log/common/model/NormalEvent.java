package com.tfb.log.common.model;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @Author: lipan
 * @Date: 2021/9/14
 * @Description： 简单描述下：「 源事件 」
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NormalEvent {

    //----------------事件属性信息
    // 事件串号
    private String msgNum;
    // 事件完整内容
    private String message;
    // 事件采集源-位置 /homg/xxx.log
    private String source;
    // 事件采集源-时间
    private String eventTime;
    // 属性节点
    private Filed filed;


    //---------------过程标识信息
    // 是否告警：true
    private Boolean warn;
    // 识别预警时间
    private ZonedDateTime warnTime;
    // 当前事件匹配的异常规则
    private List<WarnRule> warnRuleList;

    private List<String> errorList;

    private List<String> infoList;


    public void addError(String errorMsg) {
        if (Objects.isNull(this.errorList)) {
            this.errorList = new ArrayList<>();
        }
        this.errorList.add(errorMsg);
    }

    public void addInfo(String infoMsg) {
        if (Objects.isNull(this.infoList)) {
            this.infoList = new ArrayList<>();
        }
        this.errorList.add(infoMsg);
    }


}
