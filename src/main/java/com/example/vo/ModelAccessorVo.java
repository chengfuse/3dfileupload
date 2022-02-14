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
@ApiModel(description = "储存器")
public class ModelAccessorVo implements Serializable {

    @ApiModelProperty(value="名称")
    private String name;
 
    @ApiModelProperty(value="缓冲区试图")
    private Double bufferView;

    @ApiModelProperty(value="字节偏移")
    private Double bufferOffset;

    @ApiModelProperty(value="组件类型")
    private Long componentType;

    @ApiModelProperty(value="总数")
    private Long count;

    @ApiModelProperty(value="类型")
    private String type;

    @ApiModelProperty(value="归一化")
    private String normalized;

    @ApiModelProperty(value="最大")
    private List<Double> max;

    @ApiModelProperty(value="最小")
    private List<Double> min;

    @ApiModelProperty(value="额外")
    private String extras;

    @ApiModelProperty(value="扩展")
    private String extensions;

    @ApiModelProperty(value="稀疏访问编号")
    private ModelAccessorSparseVo sparse;
}