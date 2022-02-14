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
@ApiModel(description = "动画采样器")
public class ModelAnimationSamplerVo implements Serializable {

    @ApiModelProperty(value="输入")
    private String input;

    @ApiModelProperty(value="输出")
    private String output;

    @ApiModelProperty(value="扩展")
    private String extras;

    @ApiModelProperty(value="额外")
    private String extensions;

    @ApiModelProperty(value="插值")
    private String interpolation;

}