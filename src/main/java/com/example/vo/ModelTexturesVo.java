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
@ApiModel(description = "纹理")
public class ModelTexturesVo implements Serializable {

    @ApiModelProperty(value="来源")
    private Double source;

    @ApiModelProperty(value="取样器")
    private Double sampler;

}