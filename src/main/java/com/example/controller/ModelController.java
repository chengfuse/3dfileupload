package com.example.controller;

import com.example.pojo.Model;
import com.example.returnModel.ApiClient;
import com.example.service.IModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 模型服务
 * @author  陈福森-chengfuse@outlook.com
 * @Date: 2022-1-13
 */
@RestController
@RequestMapping("model")
@Api(value = "模型Controller",tags = {"用于模型操作信息"})
public class ModelController {

    private  final  static Logger logger= LoggerFactory.getLogger(FileController.class);

    @Resource
    private IModelService modelService;

    public ApiClient  queryModel(Model model){
        return null;
    }

    //查询模型的所有的图片
    @ApiOperation(value = "获取模型的图片信息")
    @GetMapping("queryModelAttach/{modelName}")
    public  ApiClient queryModelAttach(@PathVariable String  modelName){
        logger.info("查询前信息={}",modelName);
        Model model= Model.builder().modelName(modelName).isValid(1).build();
        ApiClient apiClient=modelService.queryModelAttach(model);
        logger.info("返回结果={}",apiClient.toString());
        return  apiClient;
    }

}
