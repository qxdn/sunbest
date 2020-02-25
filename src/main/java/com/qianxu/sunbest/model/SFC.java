package com.qianxu.sunbest.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class SFC implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1592078189084595742L;
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
