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
public class ModelCameraOrthographicVo implements Serializable {

    @ApiModelProperty(value="视图的浮点水平放大率。不得为零")
    private Long xmag;

    @ApiModelProperty(value="视图的浮点垂直放大率。不得为零。")
    private Long ymag;

    @ApiModelProperty(value="到远剪裁平面的浮点距离。zfar必须大于znear")
    private Long zfar;

    @ApiModelProperty(value="到近剪裁平面的浮点距离")
    private Long znear;

    @ApiModelProperty(value="扩展")
    private String extras;

    @ApiModelProperty(value="额外")
    private String extensions;

}