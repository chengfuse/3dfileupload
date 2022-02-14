package com.example.modelVo;

import com.example.pojo.ModelAttachInfo;
import lombok.*;

import java.util.List;

/**
 * 模型图片信息
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelAttachImageVo {
    /**
     * 模型名字
     */
    private  String  modelName;
    /**
     * 1024画质图片
     */
    private List<ModelAttachInfo> image1024;
    /**
     * 2048画质图片
     */
    private List<ModelAttachInfo> image4096;
}
