package com.example.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
*作者：chengfuse@outlook.com
*联系邮箱:chengfuse@outlook.com
*日期2022-01-07 10:24:58
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(description = "模型文件表")
public class ModelAttachInfo implements Serializable {
    @ApiModelProperty(value="编号")
    private Integer id;

    @ApiModelProperty(value="文件地址")
    private String fileUrl;

    @ApiModelProperty(value="模型号")
    private String modelUrl;

    @ApiModelProperty(value="模型文件类型（1.gtlf 2.其他文件 3.图片 4.glb）")
    private Integer modelType;

    @ApiModelProperty(value="模型子文件类型(1.1 gtlf  2.2 其他文件 3.0图片文件512 3.1图片760 3.2原图)")
    private Integer modelSubType;

    @ApiModelProperty(value="文件排序")
    private Integer fileSort;

    @ApiModelProperty(value="是否有效")
    private Integer isValid;

    @ApiModelProperty(value="扩展字段")
    private String extJson;

    @ApiModelProperty(value="创建时间")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    private Date updateTime;
}