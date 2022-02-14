package com.example.service.impl;

import com.example.dao.ModelAttachInfoMapper;
import com.example.pojo.ModelAttachInfo;
import com.example.returnModel.ApiClient;
import com.example.service.IModelAttachInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ModelAttachInfoService implements IModelAttachInfoService {

    @Resource
    private ModelAttachInfoMapper modelAttachInfoMapper;

    @Override
    public ApiClient queryModelAttacheInfoByApi(ModelAttachInfo modelAttachInfo) {

        return null;
    }

    @Override
    public List<ModelAttachInfo> queryModelAttacheInfoByList(ModelAttachInfo modelAttachInfo) {
       List<ModelAttachInfo> modelAttachInfos=modelAttachInfoMapper.select(modelAttachInfo);
        return modelAttachInfos;
    }
}
