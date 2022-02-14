package com.example.service;

import com.example.modelVo.FileInfoVo;
import com.example.pojo.FileInfo;
import com.example.returnModel.ApiClient;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件服务接口
 * @author  chengfusen-chengfuse@outlook.com
 * @Date 2022-1-20
 * @version 0.0.1
 */
public interface IFileService {
    /**
     * 文件上传 （注意：此上传要结合前端webuploader分片上传 最后一个分片上传完后会自动扫描文件分片是否完整并合并）
     * @param request
     * @param fileInfo
     * @return
     */
    public ApiClient upload(HttpServletRequest request, FileInfoVo fileInfo);

    /**
     * 上传完后校验文件并压缩
     * @param fileInfo
     * @return
     */
    public ApiClient  checkIsFile(FileInfoVo fileInfo);
    public void  download(FileInfoVo fileInfo);

    /**
     * 验证上传的文件是否存在
     * @param fileInfoVo
     * @return
     */
    public ApiClient checkUploadFile(FileInfoVo fileInfoVo);

    /**
     * 查询单文件
     * @param fileInfo
     * @return
     */
    public FileInfo findFileOne(FileInfo fileInfo);

    /**
     * 查询模型的文件 (这里只是返回但个文件的数据)
     * @param modelFileId
     * @param modelSubType
     * @param fileName
     * @return
     */
    public FileInfo findFileByModelAttachInfoFile(String modelFileId,String  modelSubType,String fileName);
}
