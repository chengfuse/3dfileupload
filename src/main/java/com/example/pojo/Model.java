package com.example.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
*作者：chengfuse@outlook.com
*联系邮箱:chengfuse@outlook.com
*日期2022-01-06 14:51:28
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(description = "模型")
public class Model implements Serializable {
    @ApiModelProperty(value="编号")
    private Integer id;

    @ApiModelProperty(value="模型号")
    private String modelUrl;

    @ApiModelProperty(value="模型名称")
    private String modelName;

    @ApiModelProperty(value="模型后缀")
    private String modelSuffix;

    @ApiModelProperty(value="md5加密")
    private String modelMd5;

    @ApiModelProperty(value="是否有效")
    private Integer isValid;

    @ApiModelProperty(value="扩展字段")
    private String extJson;

    @ApiModelProperty(value="创建时间")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    private Date updateTime;
}