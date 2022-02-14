package com.example.dao;

import com.example.pojo.FileInfo;

import java.util.List;

/**
*作者：chengfuse@outlook.com
*联系邮箱:chengfuse@outlook.com
*日期2022-01-04 17:18:57
*/
//文件信息接口
public interface FileInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FileInfo record);

    int insertSelective(FileInfo record);

    FileInfo selectByPrimaryKey(Integer id);

    List<FileInfo> selectByFileInfo(FileInfo fileInfo);

    int updateByPrimaryKeySelective(FileInfo record);

    int updateByPrimaryKey(FileInfo record);

    int  insertBath(List<FileInfo> list);

    List<FileInfo> select(FileInfo fileInfo);
}