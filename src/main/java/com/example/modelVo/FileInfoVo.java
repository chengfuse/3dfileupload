package com.example.modelVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 传送文件信息
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "文件信息")
public class FileInfoVo {

    @ApiModelProperty(value="文件名称")
    private  String  name;

    @ApiModelProperty(value="加密值")
    private  String  fmd5;

    @ApiModelProperty(value="当前分片")
    private  Integer chunk;

    @ApiModelProperty(value="分片总数")
    private  Integer chunks;

    @ApiModelProperty(value="文件大小")
    private  String size;

    @ApiModelProperty(value="分片大小")
    private  Integer chunkSize;

    @ApiModelProperty(value="文件地址")
    private  String  filePath;

    @ApiModelProperty(value="文件")
    private  MultipartFile  file;
}
