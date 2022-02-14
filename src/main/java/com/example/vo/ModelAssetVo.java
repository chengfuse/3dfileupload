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
@ApiModel(description = "资产")
public class ModelAssetVo implements Serializable {

    @ApiModelProperty(value="版权信息")
    private String copyright;

    @ApiModelProperty(value="生成该资源的工具")
    private String generator;

    @ApiModelProperty(value="当前版本")
    private String version;

    @ApiModelProperty(value="最下的目标版本")
    private String minVersion;

    @ApiModelProperty(value="属性扩展")
    private String extensions;

    @ApiModelProperty(value="附加信息")
    private String extras;

}