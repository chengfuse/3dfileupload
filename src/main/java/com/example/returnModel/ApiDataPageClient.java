package com.example.returnModel;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author: sunjb
 * @Descriptin:
 * @Date: 2021-05-29 22:57
 */
@Data
@ToString
@Builder
public class ApiDataPageClient implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer bizCode;
    private String bizMsg;
    private Object data;
    private Long totalPage;
    private Long totalCount;
    private Integer currentPage;

    public boolean isSuccess(){
        if(this.bizCode == 0){
            return true;
        }
        return false;
    }



}
