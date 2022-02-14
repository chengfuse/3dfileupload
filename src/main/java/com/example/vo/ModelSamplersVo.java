package com.example.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
*作者：chengfuse@outlook.com
*联系邮箱:chengfuse@outlook.com
*日期2021-12-22 15:10:17
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(description = "取样器")
public class ModelSamplersVo implements Serializable {

    @ApiModelProperty(value="最大过滤器")
    private Long magFilter;

    @ApiModelProperty(value="最小过滤器")
    private Long minFilter;

    @ApiModelProperty(value="包裹 S 轴")
    private Long wrapS;

    @ApiModelProperty(value="包裹 T 轴")
    private Long wrapT;

}