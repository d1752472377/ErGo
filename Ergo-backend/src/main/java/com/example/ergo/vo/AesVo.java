package com.example.ergo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class AesVo extends BaseVO{
    @ApiModelProperty(value = "AES密钥")
    private String key;
}
