package com.qianxu.sunbest.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class DIFF implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 7628389109524746154L;
    private Double LAT;
    private Double LON;
    private Double JAN;
    private Double FEB;
    private Double MAR;
    private Double APR;
    private Double MAY;
    private Double JUN;
    private Double JUL;
    private Double AUG;
    private Double SEP;
    private Double OCT;
    private Double NOV;
    private Double DEC;
    private Double ANN;
}
