package com.example.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 模型对外访问vo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ModelVo implements Serializable {

    @ApiModelProperty(value = "访问器")
    private List<ModelAccessorVo> accessors;

    @ApiModelProperty(value = "动画")
    private List<ModelAnimationVo> animations;

    @ApiModelProperty(value = "资产")
    private ModelAssetVo asset;

    @ApiModelProperty(value = "缓冲区视图")
    private List<ModelBufferViewsVo> bufferViews;

    @ApiModelProperty(value = "缓冲区")
    private List<ModelBufferVo> buffers;

    @ApiModelProperty(value = "相机")
    private List<ModelCameraVo> cameras;

    @ApiModelProperty(value = "图片")
    private List<ModelImageVo> images;

    @ApiModelProperty(value = "材质")
    private List<ModelMaterialVo> materials;

    @ApiModelProperty(value = "网格")
    private List<ModelMeshVo> meshes;

    @ApiModelProperty(value = "节点")
    private List<ModelNodesVo> nodes;

    @ApiModelProperty(value = "取样器")
    private List<ModelSamplersVo> samplers;

    @ApiModelProperty(value = "场景")
    private Double  scene;

    @ApiModelProperty(value = "场景")
    private List<ModelScenesVo> scenes;

    @ApiModelProperty(value = "皮肤")
    private List<ModelSkinVo> skins;

    @ApiModelProperty(value = "纹理")
    private List<ModelTexturesVo> textures;

    @ApiModelProperty(value="扩展")
    private String extras;

    @ApiModelProperty(value="额外")
    private String extensions;

    @ApiModelProperty(value = "需要扩展")
    private  List<String> extensionsRequired;

    @ApiModelProperty(value = "扩展使用")
    private  List<String> extensionsUsed;
}
