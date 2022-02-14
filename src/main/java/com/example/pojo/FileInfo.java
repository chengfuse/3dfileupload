package com.example.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
*作者：chengfuse@outlook.com
*联系邮箱:chengfuse@outlook.com
*日期2022-01-04 17:18:57
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(description = "文件信息")
public class FileInfo implements Serializable {
    @ApiModelProperty(value="编号")
    private Integer id;

    @ApiModelProperty(value="文件类型(1.图片 2.模型文件 3.视频文件 4.其他文件)")
    private Integer fileType;

    @ApiModelProperty(value="文件名称")
    private String fileName;

    @ApiModelProperty(value="文件原名称")
    private String fileOriginalName;

    @ApiModelProperty(value="文件大小(K,M,G)")
    private String fileSize;

    @ApiModelProperty(value="文件地址")
    private String fileUrl;

    @ApiModelProperty(value="文件上传状态(0.未完成 1.已完成 2.验证通过)")
    private Integer fileUploadStatus;

    @ApiModelProperty(value="文件路径")
    private String filePath;

    @ApiModelProperty(value="文件存储服务器名")
    private String fileServer;

    @ApiModelProperty(value="文件后缀")
    private String fileSuffix;

    @ApiModelProperty(value="md5加密值")
    private String fileMd5;

    @ApiModelProperty(value="是否有效（1.有效 0.无效）")
    private Integer isValid;

    @ApiModelProperty(value="扩展字段")
    private String extJson;

    @ApiModelProperty(value="创建时间")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    private Date updateTime;

    public  void  fileSizeCount(long  fileSize){
        long  kb=fileSize/1024==0?1:fileSize/1024;
        long  mb=kb/1024==0?0:kb/1024;
        long  gb=mb/1024==0?0:mb/1024;
        String fileSize1=null;
        if (gb>0) {
            fileSize1=gb+"g";
        }else if (mb>0) {
            fileSize1=mb+"m";
        }else  if(kb>0){
            fileSize1=kb+"kb";
        }
        this.fileSize=fileSize1;
    }
}