package com.example.vo;


import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@ApiModel(value = "网格基本属性")
public class ModelMeshPrimitiveAttributesVo {
    @JSONField(name = "POSITION")
    private Double POSITION;
    @JSONField(name = "TEXCOORD_0")
    private Double TEXCOORD_0;
    @JSONField(name = "NORMAL")
    private Double NORMAL;
}
