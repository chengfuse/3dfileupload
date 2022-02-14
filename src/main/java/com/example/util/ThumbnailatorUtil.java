package com.example.util;

import com.alibaba.fastjson.JSONObject;
import com.example.pojo.FileInfo;
import com.example.pojo.ModelAttachInfo;
import com.example.vo.ModelBufferVo;
import com.example.vo.ModelImageVo;
import com.example.vo.ModelVo;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 图片压缩工具
 */
@Component
public class ThumbnailatorUtil {
    /**
     * 图片压缩清晰度
     * @param fileInfos
     * @param path
     * @param newPath
     */
    public static  List<FileInfo>  compressedPhoto480(List<FileInfo> fileInfos, String path, String  newPath){
        List<FileInfo> image=new ArrayList<>();
        try {
            for (FileInfo fi: fileInfos) {
                FileInfo fileInfo=new FileInfo();
                BeanUtils.copyProperties(fi,fileInfo);
                String  randomStr= DistributeUtil.randomId(); //创建url
                File file = new File(path+"/"+fileInfo.getFileName());
                File newFile = new File(newPath + fileInfo.getFileName());
                if (!newFile.getParentFile().exists()) {
                    newFile.getParentFile().mkdirs();
                }
                fileInfo.setFileUrl(randomStr);
                Thumbnails.of(file).scale(0.2f).outputQuality(0.2f).toFile(newFile);
                fileInfo.setFilePath(newPath);
                fileInfo.fileSizeCount(file.length());
                image.add(fileInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 压缩1024
     * @param fileInfos
     * @param path
     * @param newPath
     * @return
     */
    public static List<FileInfo>  compressedPhoto730(List<FileInfo> fileInfos,String path,String  newPath){
        List<FileInfo> image=new ArrayList<>();
        try {
            for (FileInfo fi : fileInfos) {
                FileInfo fileInfo=new FileInfo();
                BeanUtils.copyProperties(fi,fileInfo);
                String  randomStr= DistributeUtil.randomId(); //创建url
//                String fileName=fileInfo.getFileOriginalName();
//                String newFileName="";
//                if ( fileName.indexOf("_")== 0) {
//                    newFileName = randomStr+fileName;
//                }else{
//                    newFileName = randomStr+"_"+ fileName;
//                }
//                fileInfo.setFileName(newFileName);
                File file = new File(fileInfo.getFilePath()+"/"+fileInfo.getFileName());
                File newFile = new File(newPath + fileInfo.getFileName());
                if (!newFile.getParentFile().exists()) {
                    newFile.getParentFile().mkdirs();
                }
                Thumbnails.of(file).scale(0.5f).outputQuality(0.5f).toFile(newFile);
                newFile.length();
                fileInfo.setFileUrl(randomStr);
                fileInfo.setFilePath(newPath);
                fileInfo.fileSizeCount(file.length());
                image.add(fileInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 文件排序
     * @param fileInfo
     * @param fileSubType
     * @param images
     * @param modelUrl
     * @return
     */
    public  static  List<ModelAttachInfo> shortFile(FileInfo fileInfo,Integer fileSubType,List<FileInfo> images,String modelUrl){
        List<ModelAttachInfo> modelAttachInfos=new ArrayList<>();
        String  jsonFile="";
        try {
            File  file=new File(fileInfo.getFilePath()+"/"+fileInfo.getFileName());
            InputStream is=new FileInputStream(file);
            InputStreamReader isr=new InputStreamReader(is);
            BufferedReader br=new BufferedReader(isr);
            StringBuffer  sb=new StringBuffer();
            jsonFile=br.readLine();
            while (jsonFile!=null){
                sb.append(jsonFile);
                jsonFile=br.readLine();
            }
            Date date=new Date();
            ModelVo model= JSONObject.parseObject(sb.toString(),ModelVo.class);
            List<ModelImageVo> modelImages= model.getImages();
            for (int i = 0; i < modelImages.size(); i++) {
                for (FileInfo fi:images) {
                    if (fi.getFileOriginalName().equals(modelImages.get(i).getUri())) {
                        ModelAttachInfo mai= ModelAttachInfo.builder()
                                .modelUrl(modelUrl)
                                .fileUrl(fi.getFileUrl())
                                .modelType(3)
                                .modelSubType(fileSubType)
                                .fileSort(i)
                                .isValid(1)
                                .extJson("")
                                .createTime(date)
                                .updateTime(date)
                                .build();
                        modelAttachInfos.add(mai);
                    }
                }
            }
            is.close();
            isr.close();
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return modelAttachInfos;
    }
    /**
     *gltf文件处理
     * @param fileInfo
     */
    public static void updateGLTF(FileInfo fileInfo, List<ModelAttachInfo> bins, List<ModelAttachInfo> returnImages, HttpServletResponse response){
        String  jsonFile="";
        try{
            //读出压缩后的gltf文件
            File  file=new File(fileInfo.getFilePath()+fileInfo.getFileName());
            InputStream is=new FileInputStream(file);
            InputStreamReader isr=new InputStreamReader(is);
            BufferedReader br=new BufferedReader(isr);
            StringBuffer  sb=new StringBuffer();
            jsonFile=br.readLine();
            while (jsonFile!=null){
                sb.append(jsonFile);
                jsonFile=br.readLine();
            }
            ModelVo model= JSONObject.parseObject(sb.toString(),ModelVo.class);
            List<ModelBufferVo> modelBufferVos=model.getBuffers();
            for (int i = 0; i < modelBufferVos.size(); i++) {
                modelBufferVos.get(i).setUri("download/"+bins.get(i).getFileUrl());
            }
            List<ModelImageVo> images= model.getImages();
            int i=0;
            for (ModelImageVo image:images) {
                image.setUri("download/"+returnImages.get(i).getFileUrl());
                i++;
            }
            response.setHeader("content-type","application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition","attachment;filename=" + fileInfo.getFileName());
            OutputStream os=response.getOutputStream();
            String  json=JSONObject.toJSONString(model);
            byte[] bytes=json.getBytes(StandardCharsets.UTF_8);
            os.write(bytes);
            os.close();
            is.close();
            isr.close();
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *gltf文件处理
     * @param fileInfo
     */
    public static void updateGLTF(FileInfo fileInfo,String modelUrl,List<FileInfo> bins, List<FileInfo> returnImages, HttpServletResponse response){
        String  jsonFile="";
        try{
            //读出压缩后的gltf文件
            File  file=new File(fileInfo.getFilePath()+fileInfo.getFileName());
            InputStream is=new FileInputStream(file);
            InputStreamReader isr=new InputStreamReader(is);
            BufferedReader br=new BufferedReader(isr);
            StringBuffer  sb=new StringBuffer();
            jsonFile=br.readLine();
            while (jsonFile!=null){
                sb.append(jsonFile);
                jsonFile=br.readLine();
            }
            ModelVo model= JSONObject.parseObject(sb.toString(),ModelVo.class);
            List<ModelBufferVo> modelBufferVos=model.getBuffers();
            for (int i = 0; i < modelBufferVos.size(); i++) {
                modelBufferVos.get(i).setUri("download/"+bins.get(i).getFileUrl());
            }
            List<ModelImageVo> images= model.getImages();
            int i=0;
            for (ModelImageVo image:images) {
                image.setUri("download/"+modelUrl+"/1/"+returnImages.get(i).getFileOriginalName());
                i++;
            }
            response.setHeader("content-type","application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition","attachment;filename=" + fileInfo.getFileName());
            OutputStream os=response.getOutputStream();
            String  json=JSONObject.toJSONString(model);
            byte[] bytes=json.getBytes(StandardCharsets.UTF_8);
            os.write(bytes);
            os.close();
            is.close();
            isr.close();
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
