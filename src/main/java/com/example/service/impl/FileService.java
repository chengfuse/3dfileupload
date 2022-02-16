package com.example.service.impl;

import com.example.dao.FileInfoMapper;
import com.example.dao.ModelAttachInfoMapper;
import com.example.modelVo.FileInfoVo;
import com.example.pojo.FileInfo;
import com.example.pojo.ModelAttachInfo;
import com.example.returnModel.ApiClient;
import com.example.returnModel.ReturnCode;
import com.example.service.IFileService;
import com.example.service.IModelService;
import com.example.util.DistributeUtil;
import com.example.util.FileMergeListener;
import com.example.util.ThumbnailatorUtil;
import com.example.util.ZipUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class FileService implements IFileService {
    //文件路径
    @Value("${file.path}")
    private  String  filePath;
    //文件监听
    @Autowired
    private FileMergeListener listener;
    @Resource
    private IModelService modelService;
    @Resource
    private ModelAttachInfoMapper modelAttachInfoMapper;
    //日志
    private  final  static Logger logger= LoggerFactory.getLogger(FileService.class);
    @Autowired
    @Resource
    private FileInfoMapper fileMapper;
    //分片上传
    @Override
    public ApiClient upload(HttpServletRequest request, FileInfoVo fileInfo) {
        ApiClient apiClient=null;
        BufferedOutputStream os=null;
        if(fileInfo.getFmd5()==null){
            return apiClient=ApiClient.builder().bizCode(ReturnCode.EXECUTION_FAILED.getCode()).bizMsg("md5缺失！").build();
        }
        if (fileInfo.getChunk() == null) {
            fileInfo.setChunk(0);
            fileInfo.setChunks(1);
        }
        File file=new File(filePath+fileInfo.getFmd5()+"/"+fileInfo.getChunk()); //创建分片文件
        logger.info("创建文件分片："+fileInfo.getFile().getOriginalFilename());
        try {
            if (!file.getParentFile().exists()) //判断文件是否存在
                file.getParentFile().mkdirs();
            fileInfo.getFile().transferTo(file);
            if (fileInfo.getChunks()!=null&&fileInfo.getChunks()-1==fileInfo.getChunk()) { //最后一个文件传入时合并文件
                request.setCharacterEncoding("utf-8");
                HttpSession session=request.getSession(true);
                File tempFile=new File(filePath+fileInfo.getName()); //创建完整文件
                if (!tempFile.getParentFile().exists()) {
                    tempFile.getParentFile().mkdirs();
                }
                os=new BufferedOutputStream(new FileOutputStream(tempFile));
                int  percentage=0; //进度
                long writeSize=0;
                logger.info("合并文件");
                boolean flag=false;
                for (int i = 0; i < fileInfo.getChunks(); i++) {
                    File newFile=new File(filePath+fileInfo.getFmd5()+"/"+i); //浏览一个文件分片
                    while (!newFile.exists()){ //查询文件分片是否完整
                        int flog=1;
                        Thread.sleep(1000);
                        flog++;
                        if (flog==10) {
                            flag=true;
                            break;
                        }
                    }
                    if (flag) {
                       return apiClient=ApiClient.builder().bizCode(ReturnCode.EXECUTION_FAILED.getCode()).bizMsg("分片缺失！").build();
                    }
                    byte[] bytes= FileUtils.readFileToByteArray(newFile); //合并文件
                    newFile.delete();
                    os.write(bytes); //写入
                    writeSize+= tempFile.length(); //写入长度
                    percentage=(int) (writeSize*100.0/Double.parseDouble(fileInfo.getSize()));
                    session.setAttribute("percentage",percentage);
                    listener.setSession(session); //监听状态
                    os.flush(); //刷新流
                }
                logger.info("合并完成！");
                File folder=new File(filePath+fileInfo.getFmd5());
                apiClient= saveFile(fileInfo,tempFile.length());
                folder.delete();
                os.flush();
            }
            if (os != null) {
                os.close();
            }
        } catch (Exception e) {
            file.delete();
            e.printStackTrace();
            apiClient= ApiClient.builder()
                    .bizCode(ReturnCode.SYSTEM_ERROR.getCode())
                    .bizMsg("系统错误！")
                    .build();
        }
        return apiClient;
    }

    /**
     * 检查文件
     * @param fileInfo
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiClient checkIsFile(FileInfoVo fileInfo) {
        ApiClient apiClient=null;
        try {
            String md5=fileInfo.getFmd5();
            if (md5==null)
                return apiClient= ApiClient.builder().bizCode(ReturnCode.SUCCESS.getCode()).bizMsg("缺少md5值").build();
            FileInfo fileInfo1=FileInfo.builder()
                    .fileMd5(fileInfo.getFmd5())
                    .build();
            List<FileInfo> fileInfos= fileMapper.selectByFileInfo(fileInfo1);
            if (CollectionUtils.isEmpty(fileInfos))
                return apiClient= ApiClient.builder().bizCode(ReturnCode.SUCCESS.getCode()).bizMsg("文件不存在").build();
            FileInfo  checkFile=fileInfos.get(0);
            File  file  =new File(checkFile.getFilePath()+checkFile.getFileName());
            if (!file.getParentFile().exists())
                return apiClient= ApiClient.builder().bizCode(ReturnCode.SUCCESS.getCode()).bizMsg("文件不存在").build();
            if (!checkFile.getFileSuffix().equals("zip"))
                return apiClient= ApiClient.builder().bizCode(ReturnCode.SUCCESS.getCode()).bizMsg("success").build();
            List<FileInfo> fileInfoList= ZipUtils.zipInFileName(file);
            String  path =checkFile.getFilePath()+checkFile.getFileMd5()+"/";
            ZipUtils.unZip(file,fileInfoList,path);
            List<FileInfo> imgFile=new ArrayList<>();
            logger.info("模型文件处理");
            for (FileInfo fi: fileInfoList) {
                Date date=new Date();
                String  fileName= fi.getFileName();  //后缀截取判断
                int  suffixIndex=fileName.lastIndexOf(".");
                String suffix=fileName.substring(suffixIndex+1,fileName.length());
                fi.setFilePath(path);
                fi.setFileServer("D11000");
                fi.setFileType(1);
                fi.setFileUploadStatus(3);
                fi.setFileMd5(fileInfo.getFmd5());
                fi.setIsValid(1);
                fi.setExtJson("");
                fi.setUpdateTime(date);
                fi.setCreateTime(date);
                if(suffix.equals("png")||suffix.equals("jpg"))
                    imgFile.add(fi);
            }
            logger.info("压缩480");
            List<FileInfo> img480=ThumbnailatorUtil.compressedPhoto480(imgFile,path,path+"480/");
            logger.info("压缩730");
            List<FileInfo> img730=ThumbnailatorUtil.compressedPhoto730(imgFile,path,path+"730/");
            List<FileInfo> modelSave=new ArrayList<>();
            modelSave.addAll(fileInfoList);
            modelSave.addAll(img480);
            modelSave.addAll(img730);
            int  code=fileMapper.insertBath(modelSave);
            if (code>0) {
                apiClient= ApiClient.builder().bizCode(ReturnCode.SUCCESS.getCode()).bizMsg("success").build();
            }
            apiClient= modelService.saveModelFile(fileInfoList,img480,img730);
        }catch (Exception e){
            e.printStackTrace();
            apiClient= ApiClient.builder().bizCode(ReturnCode.SYSTEM_ERROR.getCode()).bizMsg("系统错误！").build();
        }
        return  apiClient;
    }


    /**
     * 下载文件
     * @param fileInfo
     */
    @Override
    public void download(FileInfoVo fileInfo) {

    }

    @Override
    public ApiClient checkUploadFile(FileInfoVo fileInfoVo) {
        ApiClient apiClient=null;
        try {
            FileInfo fileInfo= FileInfo.builder().fileMd5(fileInfoVo.getFmd5()).build();
            List<FileInfo> fileInfos= fileMapper.select(fileInfo);
            boolean blog= CollectionUtils.isEmpty(fileInfos);
            apiClient=ApiClient.builder().bizCode(ReturnCode.NOT_FOUND.getCode()).bizMsg(ReturnCode.NOT_FOUND.getDesc()).build();
            if (!blog)
                apiClient=ApiClient.builder().bizCode(ReturnCode.ALREADY_EXISTS.getCode()).bizMsg(ReturnCode.ALREADY_EXISTS.getDesc()).build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return apiClient;
    }

    @Override
    public FileInfo findFileOne(FileInfo fileInfo) {
        if (fileInfo == null) {
            return null;
        }
        if (fileInfo.getFileUrl() == null) {
            return null;
        }
        List<FileInfo> fileInfos=fileMapper.selectByFileInfo(fileInfo);
        return fileInfos.get(0);
    }

    @Override
    public FileInfo findFileByModelAttachInfoFile(String modelFileId,String  modelSubType,String fileName) {
        AtomicReference<FileInfo> returnFileInfo= new AtomicReference<>();
        try {
            ModelAttachInfo modelAttachInfo= ModelAttachInfo.builder()
                    .modelUrl(modelFileId)
                    .modelSubType(Integer.parseInt(modelSubType))
                    .build();
            List<ModelAttachInfo> modelAttachInfos= modelAttachInfoMapper.select(modelAttachInfo);
            List<FileInfo> fileInfos=new ArrayList<>();
            modelAttachInfos.forEach(modelAttachInfo1 -> {
                FileInfo fileInfo= FileInfo.builder()
                        .fileUrl(modelAttachInfo1.getFileUrl())
                        .build();
                fileInfos.add(findFileOne(fileInfo));
            });
            fileInfos.forEach(fileInfo -> {
                if (fileInfo.getFileOriginalName().equals(fileName)) {
                    returnFileInfo.set(fileInfo);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
        return returnFileInfo.get();
    }

    //保存文件
    private ApiClient saveFile(FileInfoVo fileInfoVo,long fileSize){
        ApiClient apiClient=null;
        try {
            Date date=new Date(); //创建时间
            String  randomStr= DistributeUtil.randomId(); //创建url
            String  fileName= fileInfoVo.getName();  //后缀截取判断
            int  suffixIndex=fileName.lastIndexOf(".");
            String suffix=fileName.substring(suffixIndex+1,fileName.length());
            Integer integer=4;
            if (suffix.equals("png")||suffix.equals("jpg")) {
                integer=1;
            }else if(suffix.equals("gltf")||suffix.equals("glb")) {
                integer=2;
            }else if(suffix.equals("mp4")){
                integer=3;
            }
            //保存
            FileInfo fileInfo =FileInfo.builder()
                    .fileType(integer)
                    .fileName(fileInfoVo.getName())
                    .fileOriginalName(fileInfoVo.getName())
                    .fileUrl(randomStr)
                    .fileUploadStatus(1)
                    .filePath(filePath)
                    .fileServer("D110000")
                    .fileSuffix(suffix)
                    .fileMd5(fileInfoVo.getFmd5())
                    .isValid(1)
                    .extJson("")
                    .createTime(date)
                    .updateTime(date)
                    .build();
            fileInfo.fileSizeCount(fileSize);
            int code= fileMapper.insert(fileInfo);
            apiClient= ApiClient.builder()
                    .bizCode(ReturnCode.EXECUTION_FAILED.getCode())
                    .bizMsg("添加失败！")
                    .build();
            if (code>0) {
                apiClient= ApiClient.builder()
                        .bizCode(ReturnCode.SUCCESS.getCode())
                        .bizMsg("success")
                        .data(fileInfo)
                        .build();
            }
        }catch (Exception e){
            apiClient= ApiClient.builder()
                    .bizCode(ReturnCode.SYSTEM_ERROR.getCode())
                    .bizMsg("系统错误！")
                    .build();
            e.printStackTrace();
        }
        return apiClient;
    }
    /**
     * 判断文件名称
     * @param files 多个文件名
     * @return
     */
    public String inFileName(List<FileInfo> files) {
        int flag = 0;
        int flag2 = 0;
        for (int i = 0; i < files.size(); i++) {
            String originFileName = files.get(i).getFileName();
            int endNo = originFileName.lastIndexOf(".");
            int end = originFileName.lastIndexOf("_");
            String fileSubName = null;
            if (end > 0) {
                fileSubName = originFileName.substring(end + 1, endNo);
            }
            String fileSuffix = originFileName.substring(endNo + 1, originFileName.length());
            if (fileSuffix.equals("bin")) {
                flag += 1000;
            } else if (fileSuffix.equals("gltf")) {
                flag += 200;
            } else if (fileSuffix.equals("png")
                    || fileSuffix.equals("jpg")) {
                flag2 = 1;
            }
            if (fileSuffix.equals("glb")) {
                break;
            }
            if (i == files.size() - 1) {
                flag += flag2;
                if (flag != 1201) {
                    int bin = flag / 1000;
                    int gltf = flag / 100 % 10;
                    String msg = "缺少：";
                    if (bin == 0) {
                        msg += "‘bin’";
                    }
                    if (gltf == 0) {
                        msg += "‘gltf’";
                    }
                    if (flag2 == 0) {
                        msg += "’png或jpg‘";
                    }
                    return msg + "类型文件！";
                }
            }
        }
        return "0";
    }
}

