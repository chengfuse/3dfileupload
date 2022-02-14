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
@ApiModel(description = "缓冲视图")
public class ModelBufferViewsVo implements Serializable {

    @ApiModelProperty(value="名字")
    private String name;

    @ApiModelProperty(value="缓冲")
    private Double buffer;

    @ApiModelProperty(value="字节偏移量")
    private Long byteOffset;

    @ApiModelProperty(value="字节长度")
    private Long byteLength;

    @ApiModelProperty(value="字节跨度")
    private Long byteStride;

    @ApiModelProperty(value="目标")
    private Long target;

    @ApiModelProperty(value="扩展")
    private String extras;

    @ApiModelProperty(value="额外")
    private String extensions;


}