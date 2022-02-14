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
@ApiModel(description = "动画频道")
public class ModelAnimationChannelVo implements Serializable {

    @ApiModelProperty(value="取样器")
    private String sampler;

    @ApiModelProperty(value="扩展")
    private String extras;

    @ApiModelProperty(value="额外")
    private String extensions;


    @ApiModelProperty(value="动画频道目标编号")
    private ModelAnimationChannelTargerVo targer;
}