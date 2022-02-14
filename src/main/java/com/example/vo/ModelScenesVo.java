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
@ApiModel(description = "场景")
public class ModelScenesVo implements Serializable {

    @ApiModelProperty(value="下表节点") //数据库转换  {nodes:[]}
    private List<Double> nodes;

    @ApiModelProperty(value="名字")
    private String name;

    @ApiModelProperty(value="属性扩展")
    private String extensions;

    @ApiModelProperty(value="附加信息")
    private String extras;

}