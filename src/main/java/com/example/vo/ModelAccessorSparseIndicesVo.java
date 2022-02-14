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
@ApiModel(description = "访问器稀疏索引")
public class ModelAccessorSparseIndicesVo implements Serializable {

    @ApiModelProperty(value="缓冲视图")
    private String bufferView;

    @ApiModelProperty(value="字节偏移")
    private Long byteOffset;

    @ApiModelProperty(value="组件类型")
    private Long componentType;

    @ApiModelProperty(value="额外")
    private String extras;

    @ApiModelProperty(value="扩展")
    private String extensions;

}