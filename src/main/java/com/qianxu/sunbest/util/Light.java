package com.qianxu.sunbest.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Light {
    public double L_efficience = 78;
    public double P_light = 60;
    public double tw = 0.45;
    public double fai0 = 3.11;
    public double C_2 = 1;
    public double[][] tc_matrix = {{0.7,0.8,0.75,0.7},{0.55,0.65,0.60,0.55}};
    public double[][][] CU_MATRIX = {{{1.19,1.19,1.19},{1.05,1,0.97},{0.93,0.86,0.81},{0.83,0.76,0.7},
            {0.76,0.67,0.6},{0.67,0.59,0.53},{0.62,0.53,0.47},{0.57,0.49,0.43},{0.54,0.47,0.41},{0.53,0.46,0.41},
            {0.52,0.45,0.4}},{{1.11,1.11,1.11},{0.98,0.95,0.92},{0.87,0.83,0.78},{0.79,0.73,0.68},{0.71,0.64,0.59},
            {0.64,0.57,0.52},{0.59,0.52,0.47},{0.55,0.48,0.43},{0.52,0.46,0.41},{0.51,0.45,0.4},{0.5,0.44,0.4}},
            {{1.04,1.04,1.04},{0.92,0.9,0.88},{0.83,0.79,0.75},{0.75,0.7,0.66},{0.68,0.62,0.58},{0.61,0.56,0.51},
                    {0.57,0.51,0.46},{0.53,0.47,0.43},{0.51,0.45,0.41},{0.5,0.44,0.4},{0.49,0.44,0.4}}};

    public double[][][] MATRIX = {{{0.1417,0.2125,0.2833},{0.1063,0.1594,0.2125},{0.085,0.1275,0.17},
            {0.0654,0.0981,0.1308},{0.037,0.0554,0.0739}},{{0.15,0.225,0.3},{0.1125,0.1688,0.225},
            {0.09,0.135,0.18},{0.0692,0.1038,0.1385},{0.0391,0.0587,0.0783}},{{0.1667,0.25,0.3333},{0.125,0.1875,
            0.25},{0.1,0.15,0.2},{0.0769,0.1154,0.1538},{0.0435,0.0652,0.087}},{{0.1833,0.275,0.3667},{0.1375,0.2063,0.275},
            {0.11,0.165,0.22},{0.0846,0.1269,0.1692},{0.0478,0.0717,0.0957}},{{0.2,0.3,0.4},
            {0.15,0.225,0.3},{0.12,0.18,0.24},{0.0923,0.1385,0.1846},{0.0522,0.0783,0.1043}}};

    public double[] standard_matrix = {500,500,300,500,300,300,2000,500};//不同建筑照度要求

    public double[][] C1_matrix = {{1,1,1.08},{1.08,1.027,1.027},{1.027,1.027,1.027}};

    public double[] PV_efficience = {0.18,0.19,0.20,0.21};



    public double[] t0_matrix1 = {0.8,0.72,0.89,0.87,0.56,0.56};
    public double[] t0_matrix2 = {0.72,0.79,0.55,0.67,0.38};
    public double[] t0_matrix3 = {0.47,0.46,0.44,0.36,0.34};
    public double[] t0_matrix4 = {0.59,0.69,0.68,0.68,0.66,0.62,0.59,0.57,0.44,0.42};

    public double t0Value(Integer glassType,Integer glassSpecifications){//t0
        ArrayList<double[]> t0List = new ArrayList<double[]>();
        t0List.add(t0_matrix1);
        t0List.add(t0_matrix2);
        t0List.add(t0_matrix3);
        t0List.add(t0_matrix4);
        return t0List.get(glassType)[glassSpecifications];
    }


    public double tcValue(Integer Type1,Integer Type2) {//tc
        //Type1:窗种类 Type2:窗材料
        return tc_matrix[Type1][Type2];
    }

    public double CUValue(Integer roof,Integer wall,double hx,double l,double b_room){//CU
        int RCR = (int) (Math.round(5*hx*(l+b_room)/l/b_room));
        return CU_MATRIX[roof][RCR][wall];
    }

    public double AcdValue(Integer level,Integer shape,Integer climate){//窗地面积比
        return MATRIX[climate][level][shape];
    }



    public double AcValue(double l,double b_room,double hx,double t,double C_av) {//窗洞口面积
        double a = 0.0,b = 0.0;
        double Ad,Ac_1,C_1;
        Ad = l*b_room;
        if(Ad < 450)
        {
            if(Math.round(hx) == 2)
            {
                a = 0.0169;
                b = 0.2817;
            }
            if(Math.round(hx) == 3)
            {
                a = 0.01818;
                b = 0.4632;
            }
            if(Math.round(hx) == 4)
            {
                a = 0.01935;
                b = 0.6384;
            }
            if(Math.round(hx) == 5)
            {
                a = 0.0197;
                b = 0.9345;
            }
            if((Math.round(hx) <= 8)&&(Math.round(hx) >=6))
            {
                a = 0.01909;
                b = 1.731;
            }
            if(Math.round(hx) >=9)
            {
                a = 0.02253;
                b = 3.009;
            }
        }
        else {
            if ((Math.round(hx) <= 4)&&(Math.round(hx) >=2))
            {
                a = 0.01561;
                b = 1.46;
            }
            if((Math.round(hx) <= 7)&&(Math.round(hx) >=5))
            {
                a = 0.01642;
                b = 3.674;
            }
            if((Math.round(hx) <= 10)&&(Math.round(hx) >=8))
            {
                a = 0.01708;
                b = 6.073;
            }
            if((Math.round(hx) <= 14)&&(Math.round(hx) >=11))
            {
                a = 0.01795;
                b = 8.646;
            }
            if(Math.round(hx) >=16)
            {
                a = 0.01893;
                b = 14.28;
            }
        }
        Ac_1 = a*Ad+b;
        C_1 = 0.01;
        return C_av*Ac_1/C_1*0.6/t;
    }



    /*public double dayLight(double t0,double tc, double tw,double CU,double Acd) {//采光系数
        double t = t0*tc*tw;
        double C_av = t*CU*Acd;
        lightPara light = new lightPara();
        light.t = t;
        light.C_av = C_av;
        return light.C_av;
    }*/

    public double LbCal(double a,double lat,double Hb) {//太阳照度Lb
        double h = 90-Math.abs(lat-a);
        double Kb = 134.27*Math.pow(Math.sin(h/360*2*Math.PI), 0.269)*Math.exp(-0.0045*h);
        return Kb*Hb/24*1000;
    }

    public double Saving(double E_0,Integer roof,Integer wall,double l,double b_room,double[] delta){//采光节能
        double A = l*b_room;
        double fai = this.L_efficience*this.P_light;
        double C_1 = C1_matrix[roof][wall];
        double[] N = new double[delta.length];
        double[] N_0 = new double[delta.length];
        double[] W_save = new double[delta.length];
        double sum = 0.0;
        //double E_0 = standard_matrix[buildingType];
        for(int i =0;i < delta.length;i++)
        {
            N[i] = Math.max((int)Math.ceil(delta[i]*A*fai0*C_1*C_2/fai),0);
            N_0[i] = (int)Math.ceil(E_0*A*fai0*C_1*C_2/fai);
            W_save[i] = (N_0[i]-N[i])*P_light*12*30/1000;
            sum += W_save[i];
        }
        return sum;
    }

}

class lightPara{
    double t,C_av;
}
