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
@ApiModel(description = "材质")
public class ModelMaterialVo implements Serializable {
    @ApiModelProperty(value="编号")
    private Double id;

    @ApiModelProperty(value="材质的阿尔法值")
    private Double alphaCutoff;

    @ApiModelProperty(value="阿尔法模式")
    private String alphaMode;

    @ApiModelProperty(value="双面")
    private Double doubleSided;

    @ApiModelProperty(value="发光系数") //数据库转换  {emissiveFactor:[]}
    private List<Double> emissiveFactor;

    @ApiModelProperty(value="发光纹理编号")
    private ModelTexturesInfoVo emissiveTexture;

    @ApiModelProperty(value="扩展")
    private String extensions;

    @ApiModelProperty(value="名称")
    private String name;

    @ApiModelProperty(value="普通纹理编号")
    private ModelMaterialNormalTextureInfoVo normalTexture;

    @ApiModelProperty(value="遮挡纹理")
    private ModelMaterialOcclusionTextureInfoVo occlusionTexture;

    @ApiModelProperty(value="pdr金属粗糙度")
    private ModelMaterialPdrMetallicRoughnessVo pbrMetallicRoughness;

}