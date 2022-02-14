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
@ApiModel(description = "")
public class ModelCameraPerspectiveVo implements Serializable {

    @ApiModelProperty(value="纵横比")
    private Double aspectRatio;

    @ApiModelProperty(value="以弧度为单位的浮点垂直视野")
    private Double yfov;

    @ApiModelProperty(value="到远剪裁平面的浮点距离")
    private Double zfar;

    @ApiModelProperty(value="到近剪裁平面的浮点距离")
    private Double znear;

}