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
@ApiModel(description = "图片")
public class ModelImageVo implements Serializable {

    @ApiModelProperty(value="图片地址")
    private String uri;

    @ApiModelProperty(value="名字")
    private String name;

    @ApiModelProperty(value="图片类型")
    private String mimeType;

    @ApiModelProperty(value="缓冲视图")
    private String bufferView;

    @ApiModelProperty(value="扩展")
    private String extras;

    @ApiModelProperty(value="额外")
    private String extensions;

}