package com.example.returnModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public enum ReturnCode {
    SUCCESS(0,"校验正常！"),
    SYSTEM_ERROR(-1,"系统异常！"),
    EXECUTION_FAILED(101,"执行失败！"),
    NOT_FOUND(110,"未找到！"),
    ALREADY_EXISTS(111,"已存在"),
    ;
    private Integer code;
    private String desc;

    ReturnCode(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
