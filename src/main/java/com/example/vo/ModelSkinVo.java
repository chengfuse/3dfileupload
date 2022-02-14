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
@ApiModel(description = "皮肤")
public class ModelSkinVo implements Serializable {

    @ApiModelProperty(value="名称")
    private String name;

    @ApiModelProperty(value="骨骼")
    private String skeleton;

    @ApiModelProperty(value="关节") //数据库转换  {joints:[]}
    private List<Double> joints;

    @ApiModelProperty(value="逆绑定矩阵")
    private String inverseBindMatrices;

    @ApiModelProperty(value="扩展")
    private String extras;

    @ApiModelProperty(value="额外")
    private String extensions;

}