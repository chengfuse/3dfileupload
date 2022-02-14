package com.example.service;

import com.example.pojo.FileInfo;
import com.example.pojo.Model;
import com.example.returnModel.ApiClient;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
/**
 * 模型服务
 */
public interface IModelService {
    public ApiClient saveModelFile(List<FileInfo> fileInfoList, List<FileInfo> image480,List<FileInfo> img730);

    public  ApiClient  queryModelFileOne(Model model,String  modelSubType);

    public  void returnGltf(Model model, String  modelSubType,HttpServletResponse response);

    public void returnGltf(Model model,HttpServletResponse response);

    public  ApiClient queryModelAttach(Model model);

    public ApiClient  queryModelAttachInfoOne(Model model,String modelSubType);
}
