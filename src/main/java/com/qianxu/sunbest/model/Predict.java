package com.qianxu.sunbest.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class Predict {

    //经度
    @NotNull
    private Double lon;
    //纬度
    @NotNull
    private Double lat;
    //方位角
    @NotNull
    private Double azimuth;
}
