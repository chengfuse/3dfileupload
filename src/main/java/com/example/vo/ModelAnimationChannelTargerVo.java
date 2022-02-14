package com.example.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

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
@ApiModel(description = "动画频道目标")
public class ModelAnimationChannelTargerVo implements Serializable {
    @ApiModelProperty(value="编号")
    private Double id;

    @ApiModelProperty(value="节点")
    private String node;

    @ApiModelProperty(value="路径")
    private String path;

    @ApiModelProperty(value="扩展")
    private String extras;

    @ApiModelProperty(value="额外")
    private String extensions;

    @ApiModelProperty(value="是否有效")
    private Double isValid;

    @ApiModelProperty(value="创建时间")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    private Date updateTime;

    @ApiModelProperty(value="扩展字段")
    private String extJson;
}