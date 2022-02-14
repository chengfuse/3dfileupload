package com.example.returnModel;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author: sunjb
 * @Descriptin:
 * @Date: 2021-04-24 22:34
 */
@Data
@ToString
@Builder
public class ApiClient  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer bizCode;
    private String bizMsg;
    private Object data;

    public boolean isSuccess(){
        if(ReturnCode.SUCCESS.equals(bizCode)){
            return true;
        }
        return false;
    }

}
