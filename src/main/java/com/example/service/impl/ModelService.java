package com.example.service.impl;

import com.example.controller.FileController;
import com.example.dao.FileInfoMapper;
import com.example.dao.ModelAttachInfoMapper;
import com.example.dao.ModelMapper;
import com.example.modelVo.ModelAttachImageVo;
import com.example.modelVo.ModelAttachInfoVo;
import com.example.modelVo.ModelVo;
import com.example.pojo.FileInfo;
import com.example.pojo.Model;
import com.example.pojo.ModelAttachInfo;
import com.example.returnModel.ApiClient;
import com.example.returnModel.ReturnCode;
import com.example.service.IModelAttachInfoService;
import com.example.service.IModelService;
import com.example.util.DistributeUtil;
import com.example.util.ThumbnailatorUtil;
import com.example.vo.ModelImageVo;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class ModelService implements IModelService {
    @Resource
    private ModelMapper modelMapper;
    @Resource
    private ModelAttachInfoMapper modelAttachInfoMapper;
    @Resource
    private FileInfoMapper fileInfoMapper;
    @Resource
    private IModelAttachInfoService modelAttachInfoService;
    //日志
    private  final  static Logger logger= LoggerFactory.getLogger(ModelService.class);


    @Override
    public ApiClient saveModelFile(List<FileInfo> fileInfoList, List<FileInfo> image480, List<FileInfo> img730) {
        ApiClient apiClient=null;
        try{
            FileInfo  gltf=new FileInfo();
            for (FileInfo  fi:fileInfoList) {
                if (fi.getFileSuffix().equals("gltf")) {
                    gltf=fi;
                }
            }
            String  modelUrl= DistributeUtil.randomId();
            Date  date=new Date();
            Model model= Model.builder()
                    .modelName(gltf.getFileName())
                    .modelSuffix(gltf.getFileSuffix())
                    .modelMd5(gltf.getFileMd5())
                    .modelUrl(modelUrl)
                    .isValid(1)
                    .extJson("")
                    .createTime(date)
                    .updateTime(date)
                    .build();
            int  code=modelMapper.insert(model);
            apiClient= ApiClient.builder().bizCode(ReturnCode.EXECUTION_FAILED.getCode()).bizMsg("未修改成功!").build();
            if (code>0) {
                List<ModelAttachInfo> modelAttachInfos=new ArrayList<>();
                List<FileInfo> images=new ArrayList<>();
                FileInfo fileInfo=new FileInfo();
                for (FileInfo fi:fileInfoList) {
                    ModelAttachInfo mai= new ModelAttachInfo();
                    mai.setFileSort(0);
                    mai.setModelType(4);
                    mai.setModelSubType(1);
                    if (fi.getFileSuffix().equals("gltf")) {
                        mai.setFileSort(0);
                        mai.setModelType(1);
                        mai.setModelSubType(1);
                        BeanUtils.copyProperties(fi,fileInfo);
                    }else if (fi.getFileSuffix().equals("bin")) {
                        mai.setFileSort(0);
                        mai.setModelType(2);
                        mai.setModelSubType(1);
                    }else if (fi.getFileSuffix().equals("png")||fi.getFileSuffix().equals("jpg")) {
                        images.add(fi);
                        continue;
                    }
                    mai.setFileUrl(fi.getFileUrl());
                    mai.setModelUrl(modelUrl);
                    mai.setIsValid(1);
                    mai.setExtJson("");
                    mai.setUpdateTime(date);
                    mai.setCreateTime(date);
                    modelAttachInfos.add(mai);
                }
                List<ModelAttachInfo> image1=ThumbnailatorUtil.shortFile(fileInfo,1,image480,modelUrl);
                List<ModelAttachInfo> image2=ThumbnailatorUtil.shortFile(fileInfo,2,img730,modelUrl);
                List<ModelAttachInfo> image3=ThumbnailatorUtil.shortFile(fileInfo,3,images,modelUrl);
                modelAttachInfos.addAll(image1);
                modelAttachInfos.addAll(image2);
                modelAttachInfos.addAll(image3);
                int code1= modelAttachInfoMapper.insertBath(modelAttachInfos);
                if (code1 >0) {
                    apiClient= ApiClient.builder().bizCode(ReturnCode.SUCCESS.getCode()).data(gltf).bizMsg("success").build();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            apiClient= ApiClient.builder().bizCode(ReturnCode.SYSTEM_ERROR.getCode()).bizMsg("系统异常").build();
        }
        return apiClient;
    }

    @Override
    public ApiClient queryModelFileOne(Model model,String  modelSubType) {
        Integer flag=1;
        if (modelSubType != null) {
            flag=Integer.parseInt(modelSubType);
        }
        ApiClient apiClient=null;
        try{
            apiClient= ApiClient.builder().bizCode(ReturnCode.EXECUTION_FAILED.getCode()).bizMsg(ReturnCode.EXECUTION_FAILED.getDesc()).build();
            List<Model> models=modelMapper.select(model);
            ModelVo modelVo=new ModelVo();
            BeanUtils.copyProperties(models.get(0),modelVo);
            ModelAttachInfo image= ModelAttachInfo.builder()
                    .modelUrl(modelVo.getModelUrl())
                    .modelType(3)
                    .modelSubType(flag)
                    .build();
            ModelAttachInfo bin= ModelAttachInfo.builder()
                    .modelUrl(modelVo.getModelUrl())
                    .modelType(2)
                    .modelSubType(1)
                    .build();
            ModelAttachInfo gltf=ModelAttachInfo.builder()
                    .modelUrl(modelVo.getModelUrl())
                    .modelType(1)
                    .modelSubType(1)
                    .build();
            CompletableFuture<List<ModelAttachInfo>> c1=CompletableFuture.supplyAsync(()->modelAttachInfoMapper.select(image));
            CompletableFuture<List<ModelAttachInfo>> c2=CompletableFuture.supplyAsync(()->modelAttachInfoMapper.select(bin));
            CompletableFuture<List<FileInfo>> c3=CompletableFuture.supplyAsync(()->queryFile(gltf));
            CompletableFuture.allOf(c1,c2,c3).join();
            try {
                List<ModelAttachInfo> images=c1.get(2000, TimeUnit.SECONDS);
                modelVo.setImages(images);
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                List<ModelAttachInfo> bins=c2.get(2000, TimeUnit.SECONDS);
                modelVo.setBin(bins);
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                List<FileInfo> fileInfo=c3.get(2000,TimeUnit.SECONDS);
                modelVo.setGltf(fileInfo.get(0));
            }catch (Exception e){
                e.printStackTrace();
            }
            apiClient= ApiClient.builder()
                    .bizCode(ReturnCode.SUCCESS.getCode())
                    .data(modelVo)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            apiClient= ApiClient.builder()
                    .bizCode(ReturnCode.SUCCESS.getCode())
                    .bizMsg(ReturnCode.SYSTEM_ERROR.getDesc())
                    .build();
        }
        return apiClient;
    }

    /**
     * 返回拼接gltf文件
     * @param model
     * @param response
     */
    @Override
    public void returnGltf(Model model, String  modelSubType,HttpServletResponse response) {
        ApiClient apiClient= queryModelFileOne(model,modelSubType);
        if (apiClient == null) {
            return;
        }
        if (apiClient.isSuccess()) {
            return ;
        }
        ModelVo returnFile= (ModelVo) apiClient.getData();
        try {
            ThumbnailatorUtil.updateGLTF(returnFile.getGltf(),returnFile.getBin(),returnFile.getImages(),response);
            logger.info("下载完成");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void returnGltf(Model model, HttpServletResponse response) {
        ApiClient apiClient= queryModelAttachInfoOne(model,null);
        if (apiClient == null) {
            return;
        }
        if (apiClient.isSuccess()) {
            return ;
        }
        ModelAttachInfoVo returnFile= (ModelAttachInfoVo) apiClient.getData();
        try {
            ThumbnailatorUtil.updateGLTF(returnFile.getGltf(),returnFile.getModelUrl(),returnFile.getBin(),returnFile.getImages(),response);
            logger.info("下载完成");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public ApiClient queryModelAttach(Model model) {
        ApiClient apiClient=null;
        try {
            List<Model> models=modelMapper.select(model);
            if (CollectionUtils.isEmpty(models)) {
                return apiClient= ApiClient.builder().bizCode(ReturnCode.NOT_FOUND.getCode())
                        .bizMsg(ReturnCode.NOT_FOUND.getDesc()).build();
            }
            Model returnModel=models.get(0);
            ModelAttachImageVo modelAttachImageVo=new ModelAttachImageVo();
            modelAttachImageVo.setModelName(returnModel.getModelName());
            ModelAttachInfo modelAttach1024=ModelAttachInfo.builder().modelUrl(returnModel.getModelUrl()).modelType(3).modelSubType(2).isValid(1).build();
            ModelAttachInfo modelAttach2048=ModelAttachInfo.builder().modelUrl(returnModel.getModelUrl()).modelType(3).modelSubType(3).isValid(1).build();
            CompletableFuture<List<ModelAttachInfo>> c1=CompletableFuture.supplyAsync(()->modelAttachInfoMapper.select(modelAttach1024));
            CompletableFuture<List<ModelAttachInfo>> c2=CompletableFuture.supplyAsync(()->modelAttachInfoMapper.select(modelAttach2048));
            CompletableFuture.allOf(c1,c2).join();
            try {
                List<ModelAttachInfo> return1024=c1.get(2000, TimeUnit.SECONDS);
                modelAttachImageVo.setImage1024(return1024);
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                List<ModelAttachInfo> return4096=c2.get(2000, TimeUnit.SECONDS);
                modelAttachImageVo.setImage4096(return4096);
            }catch (Exception e){

                e.printStackTrace();
            }
            apiClient= ApiClient.builder().bizCode(ReturnCode.SUCCESS.getCode()).data(modelAttachImageVo).build();
        }catch (Exception e){
            e.printStackTrace();
            apiClient= ApiClient.builder().bizCode(ReturnCode.SYSTEM_ERROR.getCode())
                    .bizMsg(ReturnCode.SYSTEM_ERROR.getDesc())
                    .build();
        }
        return apiClient;
    }

    @Override
    public ApiClient queryModelAttachInfoOne(Model model,String modelSubType) {
        Integer flag=1;
        if (modelSubType != null) {
            flag=Integer.parseInt(modelSubType);
        }
        ApiClient apiClient=null;
        try{
            apiClient= ApiClient.builder().bizCode(ReturnCode.EXECUTION_FAILED.getCode()).bizMsg(ReturnCode.EXECUTION_FAILED.getDesc()).build();
            List<Model> models=modelMapper.select(model);
            ModelAttachInfoVo modelVo=new ModelAttachInfoVo();
            BeanUtils.copyProperties(models.get(0),modelVo);
            ModelAttachInfo image= ModelAttachInfo.builder()
                    .modelUrl(modelVo.getModelUrl())
                    .modelType(3)
                    .modelSubType(flag)
                    .build();
            ModelAttachInfo bin= ModelAttachInfo.builder()
                    .modelUrl(modelVo.getModelUrl())
                    .modelType(2)
                    .modelSubType(1)
                    .build();
            ModelAttachInfo gltf=ModelAttachInfo.builder()
                    .modelUrl(modelVo.getModelUrl())
                    .modelType(1)
                    .modelSubType(1)
                    .build();
            CompletableFuture<List<FileInfo>> c1=CompletableFuture.supplyAsync(()->queryFile(image));
            CompletableFuture<List<FileInfo>> c2=CompletableFuture.supplyAsync(()->queryFile(bin));
            CompletableFuture<List<FileInfo>> c3=CompletableFuture.supplyAsync(()->queryFile(gltf));
            CompletableFuture.allOf(c1,c2,c3).join();
            try{
                List<FileInfo> fileInfos=c1.get(2000,TimeUnit.SECONDS);
                modelVo.setImages(fileInfos);
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                List<FileInfo> fileInfos=c2.get(2000,TimeUnit.SECONDS);
                modelVo.setBin(fileInfos);
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                List<FileInfo> fileInfos=c3.get(2000,TimeUnit.SECONDS);
                modelVo.setGltf(fileInfos.get(0));
            }catch (Exception e){
                e.printStackTrace();
            }
            apiClient= ApiClient.builder()
                    .bizCode(ReturnCode.SUCCESS.getCode())
                    .data(modelVo)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            apiClient= ApiClient.builder()
                    .bizCode(ReturnCode.SUCCESS.getCode())
                    .bizMsg(ReturnCode.SYSTEM_ERROR.getDesc())
                    .build();
        }
        return apiClient;
    }

    private List<FileInfo> queryFile(ModelAttachInfo modelAttachInfo){
        List<ModelAttachInfo> modelAttachInfos= modelAttachInfoMapper.select(modelAttachInfo);
        if (!CollectionUtils.isEmpty(modelAttachInfos)) {
            List<FileInfo> returnFile=new ArrayList<>();
            modelAttachInfos.forEach(modelAttachInfo1 -> {
                FileInfo fileInfo = FileInfo.builder()
                        .fileUrl(modelAttachInfo1.getFileUrl())
                        .build();
                 List<FileInfo> fi= fileInfoMapper.select(fileInfo);
                if (fi != null) {
                    returnFile.add(fi.get(0));
                }
            });
            return  returnFile;
        }
        return null;
    }

}
