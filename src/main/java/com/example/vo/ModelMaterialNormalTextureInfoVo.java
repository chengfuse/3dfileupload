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
@ApiModel(description = "材质法线纹理信息")
public class ModelMaterialNormalTextureInfoVo implements Serializable {

    @ApiModelProperty(value="指数")
    private Double index;

    @ApiModelProperty(value="规模")
    private Double scale;

    @ApiModelProperty(value="坐标")
    private String texCoord;

    @ApiModelProperty(value="扩展")
    private String extras;

    @ApiModelProperty(value="额外")
    private String extensions;

}