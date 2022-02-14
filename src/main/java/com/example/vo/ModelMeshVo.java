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
@ApiModel(description = "网格")
public class ModelMeshVo implements Serializable {

    @ApiModelProperty(value="名称")
    private String name;

    @ApiModelProperty(value="权重") //数据库转换  {weights:[]}
    private List<Double> weights;

    @ApiModelProperty(value="额外")
    private String extras;

    @ApiModelProperty(value="扩展")
    private String extensions;

    @ApiModelProperty(value = "原语")
    private List<ModelMeshPrimitiveVo> primitives;

}