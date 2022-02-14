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
@ApiModel(description = "相机")
public class ModelCameraVo implements Serializable {

    @ApiModelProperty(value="名称")
    private String name;

    @ApiModelProperty(value="类型")
    private String type;

    @ApiModelProperty(value="扩展")
    private String extras;

    @ApiModelProperty(value="额外")
    private String extensions;

    @ApiModelProperty(value="相机正交编号")
    private ModelCameraOrthographicVo orthographic;

    @ApiModelProperty(value="相机视角编号")
    private ModelCameraPerspectiveVo perspective;

}