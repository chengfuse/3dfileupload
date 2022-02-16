package com.example.util;

import com.example.pojo.FileInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
@Slf4j
public  class ZipUtils {

    /**
     * 解压压缩文件zip转换字节返回
     * @param  filePath
     * @return  map
     * */
    public static Map unZip(MultipartFile[] filePath, String path){
        long start = System.currentTimeMillis();
        Map<String,Object> map=new HashMap<>();
        int count=0;
        // 开始解压
        try {
            ByteArrayInputStream bis=new ByteArrayInputStream(filePath[0].getBytes());
            ZipInputStream zipInputStream=new ZipInputStream(bis);
            while (zipInputStream!= null) {
                ZipEntry entry = zipInputStream.getNextEntry();
                log.info("解压" + entry.getName());
                // 如果是文件夹，就创建个文件夹
                if (entry.isDirectory()) {
                    String dirPath = path + "/" + entry.getName();
                    File dir = new File(dirPath);
                    dir.mkdirs();
                } else {
                    //转成字节处理
                    ByteArrayOutputStream bos=new ByteArrayOutputStream();
                    int len=-1;
                    byte[] buf = new byte[1024];
                    while ((len=zipInputStream.read(buf,0,buf.length))!=-1){
                        bos.write(buf,0,len);
                    }
                    map.put("file"+count,entry.getName());
                    byte[] b= bos.toByteArray();
                    map.put("byte"+count,b);
                    bos.flush();
                    bos.close();
                    count++;
                }
            }
            bis.close();
            zipInputStream.close();
        } catch (Exception e) {
            throw new RuntimeException("unzip error from ZipUtils", e);
        } finally {
            long end = System.currentTimeMillis();
            log.info("解压完成，耗时：" + (end - start) +" ms");
            map.put("count",count);
            return map;
        }
    }

    /**
     * 解压文件不通过流
     * @param file 出入文件地址
     */
    public  static  void  unZip(File file,List<FileInfo> fileInfos,String path){
        FileOutputStream outputStream=null;
        try{
            InputStream is=new FileInputStream(file);
            ZipInputStream zipInputStream=new ZipInputStream(is, Charset.forName("GBK"));
            int count =0;
            while (zipInputStream!=null){
                ZipEntry zn =zipInputStream.getNextEntry();
                if (zn == null) {
                    break;
                }
                if (!zn.isDirectory()) {
                    String fileName=fileInfos.get(count).getFileName();
                    log.info("当前解压文件"+fileName);
                    path= path+"/";
                    if (fileInfos.get(count).getFilePath() != null) {
                       path= path+fileInfos.get(count).getFilePath();
                    }
                    String  randomStr= DistributeUtil.randomId(); //创建url
                    int  suffixIndex=fileName.lastIndexOf(".");
                    String suffix=fileName.substring(suffixIndex+1,fileName.length());
                    fileInfos.get(count).setFileSuffix(suffix);
                    fileInfos.get(count).setFileOriginalName(fileName);
                    File newFile = new File(path,fileInfos.get(count).getFileName());
                    if (!newFile.getParentFile().exists()) {
                        newFile.getParentFile().mkdirs();
                    }
//                    fileInfos.get(count).getId(randomStr);
                    outputStream=new  FileOutputStream(newFile);
                    byte b[]=new  byte[1024];
                    int  len=0;
                    while ((len= zipInputStream.read(b))!=-1){
                       outputStream.write(b,0,len);
                    }
                    outputStream.close();
                    count++;
                }

            }
            is.close();
            zipInputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取zip里的文件名
     * @param file
     * @return
     */
    public static List<FileInfo> zipInFileName(File file){
        List<FileInfo> fileInfos=new ArrayList<>();
        try{
            InputStream inputStream=new FileInputStream(file);
            ZipInputStream zipInputStream=new ZipInputStream(inputStream, Charset.forName("GBK"));
            while (zipInputStream!=null){
                ZipEntry zn =zipInputStream.getNextEntry();
                if (zn == null) {
                    break;
                }
                if (!zn.isDirectory()) {
                    FileInfo fileInfo=new FileInfo();
                    String fileName=zn.getName();
                    long size=zn.getSize();
                    int i=fileName.lastIndexOf("/");
                    fileInfo.setFileName(fileName.substring(i+1,fileName.length()));
                    fileInfo.fileSizeCount(zn.getSize());
                    if (i != -1) {
                        String filePath=fileName.substring(0,i);
                        fileInfo.setFilePath(filePath);
                    }
                    log.info("当前zip获取文件"+fileInfo.getFileName()+"文件大小"+fileInfo.getFileSize());
                    fileInfos.add(fileInfo);
                }
            }
            inputStream.close();
            zipInputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return fileInfos;
    }
}
