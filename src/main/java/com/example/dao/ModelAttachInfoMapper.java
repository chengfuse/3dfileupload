package com.example.dao;

import com.example.pojo.ModelAttachInfo;

import java.util.List;

/**
*作者：chengfuse@outlook.com
*联系邮箱:chengfuse@outlook.com
*日期2022-01-06 15:11:03
*/
//模型文件表接口
public interface ModelAttachInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ModelAttachInfo record);

    int insertSelective(ModelAttachInfo record);

    ModelAttachInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ModelAttachInfo record);

    int updateByPrimaryKey(ModelAttachInfo record);

    int  insertBath(List<ModelAttachInfo> list);

    List<ModelAttachInfo> select(ModelAttachInfo modelAttachInfo);
}