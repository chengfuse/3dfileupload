package com.example.service;

import com.example.pojo.ModelAttachInfo;
import com.example.returnModel.ApiClient;

import java.util.List;

/**
 * 模型文件服务
 */
public interface IModelAttachInfoService {
    public ApiClient queryModelAttacheInfoByApi(ModelAttachInfo modelAttachInfo);

    public List<ModelAttachInfo> queryModelAttacheInfoByList(ModelAttachInfo modelAttachInfo);
}
