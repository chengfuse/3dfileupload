package com.example.controller;

import com.example.modelVo.FileInfoVo;
import com.example.pojo.FileInfo;
import com.example.pojo.Model;
import com.example.returnModel.ApiClient;
import com.example.returnModel.ReturnCode;
import com.example.service.IModelService;
import com.example.service.impl.FileService;
import com.example.util.FileMergeListener;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * 文件操作类
 * @author 陈福森
 * @Date 2021-1-4
 */
@CrossOrigin
@Api(value = "文件Controller",tags = {"文件接口"})
@RestController
@RequestMapping("file")
public class FileController {

    private  final  static  String  UFT8="utf-8";
    //service服务
    @Resource
    @Autowired
    private FileService fileService;
    //日志
    private  final  static Logger logger=LoggerFactory.getLogger(FileController.class);
    //文件监听
    @Autowired
    private FileMergeListener listener;
    @Resource
    private IModelService modelService;
    /**
     * 上传文件
     * @param request
     * @param fileInfo
     */
    @ApiOperation(value = "上传文件分片并合并 文件分片不超过10m")
    @PostMapping("upload")
    @ResponseBody
    public  void  upload(HttpServletRequest request, FileInfoVo fileInfo){
        fileService.upload(request,fileInfo);
    }

    @PostMapping("checkUploadFile")
    public  ApiClient checkUploadFile(FileInfoVo fileInfoVo){
        logger.info("接受请求={}",fileInfoVo);
        ApiClient apiClient= fileService.checkUploadFile(fileInfoVo);
        logger.info("返回请求={}",apiClient);
        return apiClient;
    }

    /**
     * 审核文件
     * @param request
     * @param fileInfo
     */
    @ApiOperation(value = "验证文件是否合法")
    @PostMapping(value = "checkIsFile",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public  ApiClient  checkIsFile(HttpServletRequest request,FileInfoVo fileInfo){
        ApiClient apiClient= fileService.checkIsFile(fileInfo);
        return apiClient;
    }

    /**
     * 文件合并状态
     */
    @ApiOperation(value = "合并文件状态")
    @PostMapping(value = "mergeStatus")
    public ApiClient status(){
        logger.info("获取文件合并状态");
        ApiClient apiClient=null;
        try {
            Object percentage=listener.getSession("percentage"); //获取文件进度
            apiClient= ApiClient.builder().bizCode(ReturnCode.SUCCESS.getCode()).data(percentage).build();
        }catch (Exception e){
            e.printStackTrace();
            apiClient= ApiClient.builder().bizCode(ReturnCode.SYSTEM_ERROR.getCode()).bizMsg("系统错误").build();
        }
        return apiClient;
    }

    /**
     * 返回文件
     */
    @ApiOperation(value = "文件下载")
    @RequestMapping(value = "queryModel/download/{modelFileId}/{modelSubType}/{fileName}")
    public  void  download(@PathVariable String modelFileId,@PathVariable String  modelSubType
            ,@PathVariable String fileName,HttpServletResponse response){
        FileInfo returnFile=fileService.findFileByModelAttachInfoFile(modelFileId,modelSubType,fileName);
        if (returnFile == null) {
            return;
        }
        String  fileName1=returnFile.getFileName();
        String path=returnFile.getFilePath();
        response.setHeader("content-type","application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition","attachment;filename=" + fileName1);
        byte[] bytes=new byte[1024];
        BufferedInputStream bis=null;
        OutputStream os=null;
        try {
            os= response.getOutputStream();
            bis=new BufferedInputStream(new FileInputStream(path+fileName1));
            int i=bis.read(bytes);
            while (i!=-1){
                os.write(bytes,0,i);
                os.flush();
                i=bis.read(bytes);
            }
            logger.info("下载完成");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 文件下载
     */
    @ApiOperation(value = "文件下载")
    @RequestMapping(value = "queryModel/download/{fileUrl}")
    public void download(@PathVariable String  fileUrl, HttpServletResponse response){
        FileInfo fileInfo=FileInfo.builder()
                .fileUrl(fileUrl)
                .build();
        FileInfo returnFile=fileService.findFileOne(fileInfo);
        String  fileName=returnFile.getFileName();
        String path=returnFile.getFilePath();
        response.setHeader("content-type","application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition","attachment;filename=" + fileName);
        byte[] bytes=new byte[1024];
        BufferedInputStream bis=null;
        OutputStream os=null;
        try {
            os= response.getOutputStream();
            bis=new BufferedInputStream(new FileInputStream(path+fileName));
            int i=bis.read(bytes);
            while (i!=-1){
                os.write(bytes,0,i);
                os.flush();
                i=bis.read(bytes);
            }
            logger.info("下载完成");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation(value = "查询gltf模型文件")
    @GetMapping("queryModel/{fileName}")
    public void queryModel(@PathVariable  String  fileName, HttpServletResponse response){
        Model model= Model.builder().modelName(fileName).build();
        modelService.returnGltf(model,response);
    }
}
