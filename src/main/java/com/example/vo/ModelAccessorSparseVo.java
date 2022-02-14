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
@ApiModel(description = "访问器稀疏")
public class ModelAccessorSparseVo implements Serializable {

    @ApiModelProperty(value="总数")
    private Double count;

    @ApiModelProperty(value="额外")
    private String extras;

    @ApiModelProperty(value="扩展")
    private String extensions;

    @ApiModelProperty(value="指数")
    private ModelAccessorSparseIndicesVo indices;

    @ApiModelProperty(value="访问器稀疏索引")
    private ModelAccessorSparseValueVo values;
}