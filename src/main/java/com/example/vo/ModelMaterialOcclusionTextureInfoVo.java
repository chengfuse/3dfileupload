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
@ApiModel(description = "")
public class ModelMaterialOcclusionTextureInfoVo implements Serializable {

    @ApiModelProperty(value="指数")
    private Double index;

    @ApiModelProperty(value="强度")
    private Double strength;

    @ApiModelProperty(value="坐标")
    private String texCoord;

    @ApiModelProperty(value="扩展")
    private String extras;

    @ApiModelProperty(value="额外")
    private String extensions;

}