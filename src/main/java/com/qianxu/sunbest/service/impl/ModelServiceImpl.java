package com.qianxu.sunbest.service.impl;

import com.qianxu.sunbest.dao.DIFFDao;
import com.qianxu.sunbest.dao.DNRDao;
import com.qianxu.sunbest.model.*;
import com.qianxu.sunbest.util.Handle;
import com.qianxu.sunbest.service.api.ModelService;
import com.qianxu.sunbest.util.Light;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.MarshalledObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service("modelService")
public class ModelServiceImpl implements ModelService {
    @Autowired
    DIFFDao diffDao;

    @Autowired
    DNRDao dnrDao;

    @Autowired
    Handle handle;

    @Autowired
    Light light;


    @Override
    public Answer getAnswer(UserDefine userDefine) {
        Answer answer = new Answer();
        double lat = userDefine.getLat();
        double lon = userDefine.getLon();
        //优化数据
//        if(Math.max(Math.abs((int)(Math.round(Math.abs(lat)*100))%100-25),25) != 25)
//            lat = Math.floor(lat)+(lat>0?0.75:0.25);
//        else
//            lat = Math.floor(lat)+(lat>0?0.25:0.75);
//        if(Math.max(Math.abs((int)(Math.round(Math.abs(lon)*100))%100-25),25) != 25)
//            lon = Math.floor(lon)+(lon>0?0.75:0.25);
//        else
//            lon = Math.floor(lon)+(lon>0?0.25:0.75);
        int num1at = Math.abs((int)(Math.round(Math.abs(lat)*100))%100);
        int numlon = Math.abs((int)(Math.round(Math.abs(lon)*100))%100);
        if( num1at >= 0 && num1at <= 49  )
            lat = lat>=0?(Math.floor(lat)+0.25):(Math.ceil(lat)-0.25);
        else
            lat = lat>=0?(Math.floor(lat)+0.75):(Math.ceil(lat)-0.75);
        if( numlon >= 0 && numlon <= 49  )
            lon = lon>=0?(Math.floor(lon)+0.25):(Math.ceil(lon)-0.25);
        else
            lon = lon>=0?(Math.floor(lon)+0.75):(Math.ceil(lon)-0.75);

        DIFF diff = diffDao.getByLATAndLON(lat,lon);
        DNR dnr = dnrDao.getByLATAndLON(lat,lon);
        ArrayList<Double> Hb = handle.getHb(dnr);
        ArrayList<Double> Hd = handle.getHd(diff);

        //筛选
        handle.arrangeData(Hb,Hd,handle.n);

        //初始化
        double[] Ht_user;
        double[] Ht_usermax = new double[handle.n.length];
        double[] Ht_effmax = new double[handle.n.length];
        double[] Ht_eff = new double[handle.n.length];

        //最佳倾角
        double bestAngle = handle.avrg_bestAngle(userDefine.getAzimuth()>0?'n':'y',lat,handle.paraCal(handle.n),
                Hb,Hd,userDefine.getAzimuth());

        //输入倾角对应Ht
        Ht_user = handle.userHt(userDefine.getAzimuth()>0?'n':'y',lat,handle.paraCal(handle.n),Hb,Hd,
                userDefine.getUserAngle()).clone();

        //最佳倾角对应Ht
        for(int i = 0;i < Ht_usermax.length;i++)
            Ht_usermax[i] = handle.userHt('y',lat,handle.paraCal(handle.n),Hb,Hd,Math.max(handle.Uopt1[i],0.0))[i];
        handle.b_0 = 20.0;

        //Ht对应发电量
        for(int i = 0;i < Ht_user.length;i++) {
            Ht_effmax[i] = Ht_usermax[i]*light.PV_efficience[userDefine.getPhotovoltaicPanelType()];
            Ht_eff[i] = Ht_user[i]*light.PV_efficience[userDefine.getPhotovoltaicPanelType()];
        }

        //采光系数
        double tc = light.tcValue(userDefine.getWindowsType(),userDefine.getWindowsMaterial());
        int roof = userDefine.getShedReflect(),wall = userDefine.getWallReflect();
        double hx = userDefine.getHouseHigh(),l = userDefine.getHouseLength(),b_room = userDefine.getHouseWidth();
        double CU = light.CUValue(roof,wall,hx,l,b_room);
        int climate = userDefine.getLightClimate(),level = userDefine.getLightLevel(),shape = userDefine.getHouseType();
        double Acd = light.AcdValue(level,shape,climate);
        int glassType = userDefine.getGlassType(),glassSpecifications = userDefine.getGlassSpecifications();
        double C_av = light.t0Value(glassType,glassSpecifications)*tc*light.tw*CU*Acd;

        //窗洞口面积
        double Ad = light.AcValue(l,b_room,hx,tc*light.tw*light.t0Value(glassType,glassSpecifications),
                C_av );

        //采光节能
        double[] Lb = new double[handle.n.length];
        for(int i = 0;i < Lb.length;i++)
            Lb[i] = light.LbCal(handle.paraCal(handle.n)[i],lat,Hb.get(i));
        double E_0 = light.standard_matrix[userDefine.getBuildingType()];
        double[] delta = new double[Lb.length];
        for(int i = 0;i < Lb.length;i++)
            delta[i] = E_0-Lb[i]*C_av;
        double sum = light.Saving(E_0,roof,wall,l,b_room,delta);

//        Map<Integer,Double> bestpower = new HashMap<>();
//        Map<Integer,Double> currentpower = new HashMap<>();
//        for(int i = 0;i < handle.n.length;i++) {
//            bestpower.put((int)Math.ceil(handle.n[i]/30.0), Ht_effmax[i]);
//            currentpower.put((int)Math.ceil(handle.n[i]/30.0),Ht_eff[i]);
//        }

        answer.setLat(lat);
        answer.setLon(lon);
        answer.setUserAngle(userDefine.getUserAngle());
        answer.setBestAngle(bestAngle);
        answer.setSavePower(sum);
        answer.setExpectPowerGeneration(handle.avrg_userHt(Ht_eff)*365);
        answer.setAveragePowerGeneration(handle.avrg_userHt(Ht_eff));
        answer.setArea(Ad);
        answer.setBestPower(Ht_effmax);
        answer.setCurrentPower(Ht_eff);


        return answer;
    }

    @Override
    public Double getBestAngle(Predict predict) {
        return null;
    }

}
