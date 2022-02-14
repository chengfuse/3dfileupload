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
@ApiModel(description = "")
public class ModelMaterialPdrMetallicRoughnessVo implements Serializable {

    @ApiModelProperty(value="材质的基色因子") //数据库转换  {baseColorFactor:[]}
    private List<Double> baseColorFactor;

    @ApiModelProperty(value="基色纹理")
    private ModelTexturesInfoVo baseColorTexture;

    @ApiModelProperty(value="金属因素")
    private Double metallicFactor;

    @ApiModelProperty(value="金属粗糙度纹理")
    private ModelTexturesInfoVo metallicRoughnessTexture;

    @ApiModelProperty(value="粗糙度系数")
    private Double roughnessFactor;

    @ApiModelProperty(value="扩展")
    private String extras;

    @ApiModelProperty(value="额外")
    private String extensions;

}