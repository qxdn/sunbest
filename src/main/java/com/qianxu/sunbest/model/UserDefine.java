package com.qianxu.sunbest.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@ToString
public class UserDefine {

    //经纬度
    @NotNull
    private Double lat;

    @NotNull
    private Double lon;

    //方位角
    @NotNull
    private Double azimuth;
    //用户定义倾角
    @NotNull
    private Double userAngle;
    //光伏板类型
    @NotNull
    @Max(3)
    @Min(0)
    private Integer photovoltaicPanelType;

    //房屋类型
    @NotNull
    @Max(3)
    @Min(0)
    private Integer houseType;
    //建筑类型
    @NotNull
    @Max(7)
    @Min(0)
    private Integer buildingType;

    //玻璃规格
    @NotNull
    @Max(9)
    @Min(0)
    private Integer glassSpecifications;
    //玻璃类型
    @NotNull
    @Max(3)
    @Min(0)
    private Integer glassType;

    //窗户种类
    @NotNull
    @Max(1)
    @Min(0)
    private Integer windowsType;
    //窗户材料
    @NotNull
    @Max(3)
    @Min(0)
    private Integer windowsMaterial;
    //房屋长
    @NotNull
    private Double houseLength;
    //房屋高
    @NotNull
    private Double houseHigh;
    //房屋宽
    @NotNull
    private Double houseWidth;
    //棚顶反射比
    @NotNull
    @Max(2)
    @Min(0)
    private Integer shedReflect;
    //墙面反射比
    @NotNull
    @Max(2)
    @Min(0)
    private Integer wallReflect;
    //光气候区
    @NotNull
    @Max(4)
    @Min(0)
    private Integer lightClimate;
    //采光等级
    @NotNull
    @Max(4)
    @Min(0)
    private Integer lightLevel;
}

