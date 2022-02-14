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
@ApiModel(description = "网格基本体")
public class ModelMeshPrimitiveVo implements Serializable {

    @ApiModelProperty(value="指数")
    private Double indices;

    @ApiModelProperty(value="材料")
    private Double material;

    @ApiModelProperty(value="模式")
    private Double mode;

    @ApiModelProperty(value="目标")//数据库转换  {targets:[]}
    private List<String> targets;

    @ApiModelProperty(value="属性")//数据库转换  {attributes:{}}
    private ModelMeshPrimitiveAttributesVo attributes;

    @ApiModelProperty(value="扩展")
    private String extras;

    @ApiModelProperty(value="额外扩展")
    private String extensions;

}