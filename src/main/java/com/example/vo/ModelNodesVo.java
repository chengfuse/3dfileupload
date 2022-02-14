package com.example.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

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
@ApiModel(description = "节点")
public class ModelNodesVo implements Serializable {

    @ApiModelProperty(value="摄像机节点，跟着摄像机矩阵")
    private String camera;

    @ApiModelProperty(value="子节点列表") //数据库转换  {children:[]}
    private List<Double> children;

    @ApiModelProperty(value="蒙皮节点")
    private Double skin;

    @ApiModelProperty(value="矩阵")//数据库转换  {matrix:[]}
    private List<Double> matrix;

    @ApiModelProperty(value="物体节点")
    private Double mesh;

    @ApiModelProperty(value="四元数旋转") //数据库转换  {rotation:[]}
    private List<Double> rotation;

    @ApiModelProperty(value="缩放比") //数据库转换  {scale:[]}
    private List<Double> scale;

    @ApiModelProperty(value="位置信息") //数据库转换  {translation:[]}
    private List<Double> translation;

    @ApiModelProperty(value="mesh的权重数组") //数据库转换  {weights:[]}
    private List<Double> weights;

    @ApiModelProperty(value="名字")
    private String name;

    @ApiModelProperty(value="属性扩展")
    private String extensions;

    @ApiModelProperty(value="附加信息")
    private String extras;
}