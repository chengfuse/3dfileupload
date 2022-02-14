package com.example.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

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
@ApiModel(description = "动画")
public class ModelAnimationVo implements Serializable {

    @ApiModelProperty(value="名称")
    private String name;

    @ApiModelProperty(value="扩展")
    private String extras;

    @ApiModelProperty(value="额外")
    private String extensions;

    @ApiModelProperty(value="频道")
    private List<ModelAnimationChannelVo> channels;

    @ApiModelProperty(value="取样器")
    private List<ModelAnimationSamplerVo> samplers;

}