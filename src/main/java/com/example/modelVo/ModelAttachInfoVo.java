package com.example.modelVo;

import com.example.pojo.FileInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

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
@ApiModel(description = "模型附件")
public class ModelAttachInfoVo {

    @ApiModelProperty(value="模型号")
    private String modelUrl;

    @ApiModelProperty(value="模型名称")
    private String modelName;

    @ApiModelProperty(value="md5加密")
    private String modelMd5;

    @ApiModelProperty(value = "主文件")
    private FileInfo gltf;

    @ApiModelProperty(value="关联文件图片")
    private List<FileInfo> images;

    @ApiModelProperty(value="bin文件")
    private List<FileInfo> bin;

}
