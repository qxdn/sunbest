package com.qianxu.sunbest;
import com.qianxu.sunbest.dao.DIFFDao;
import com.qianxu.sunbest.dao.DNRDao;
import com.qianxu.sunbest.dao.SFCDao;
import com.qianxu.sunbest.model.Answer;
import com.qianxu.sunbest.model.DIFF;
import com.qianxu.sunbest.model.DNR;
import com.qianxu.sunbest.util.Handle;
import com.qianxu.sunbest.util.Light;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest
public class HandleTest {
    @Autowired
    DIFFDao diffDao;

    @Autowired
    DNRDao dnrDao;

    @Autowired
    SFCDao sfcDao;

    @Autowired
    Handle handle;

    @Autowired
    Light light;

   /* @Test
    void arrangeTest(){
        DIFF diff = diffDao.getByLATAndLON(85.25,-52.75);
        DNR dnr = dnrDao.getByLATAndLON(85.25,-52.75);
        ArrayList<Double> Hd = handle.getHd(diff);
        ArrayList<Double>  Hb = handle.getHb(dnr);
        System.out.println(Hd);
        handle.arrangeData(Hb,Hd,handle.n);
        System.out.println(Hd);

    }*/

   @Test
    void Test(){
       handle.n = new int[]{17, 47, 75, 105, 135, 162, 198, 228, 258, 288, 318, 344};
       double bestAngle;
       double lat = 28.957;
       double lon = 118.876;
       //优化数据
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
       System.out.println("Hb:"+Hb);
       System.out.println("Hd"+Hd);
       handle.arrangeData(Hb,Hd,handle.n);
       System.out.println("Hb_new:"+Hb);
       System.out.println("Hd_new:"+Hd);
       System.out.println("n_length:"+handle.n.length);
       double[] Ht_user;
       double[] Ht_usermax = new double[handle.n.length];
       double[] Ht_effmax = new double[handle.n.length];
       double[] Ht_eff = new double[handle.n.length];
       bestAngle = handle.avrg_bestAngle('y',lat,handle.paraCal(handle.n),
               Hb,Hd,0.0);
       System.out.println("bestangle:"+bestAngle);
       Ht_user = handle.userHt('y',lat,handle.paraCal(handle.n),Hb,Hd,0).clone();
       System.out.print("Ht_user:");
       for(double ht:Ht_user)
           System.out.print(ht+" ");
       System.out.println();
        for(int i = 0;i < Ht_usermax.length;i++)
            Ht_usermax[i] = handle.userHt('y',lat,handle.paraCal(handle.n),Hb,Hd,Math.max(handle.Uopt1[i],0.0))[i];
       handle.b_0 = 20.0;
       System.out.print("Ht_usermax:");
        for(double ht:Ht_usermax)
           System.out.print(ht+" ");
       System.out.println();
       System.out.println("avrg_userHt:"+handle.avrg_userHt(Ht_user));
       for(int i = 0;i < Ht_user.length;i++)
       {
           Ht_effmax[i] = Ht_usermax[i]*light.PV_efficience[0];
           Ht_eff[i] = Ht_user[i]*light.PV_efficience[0];
       }
       System.out.print("Ht_eff:");
       for(double ht:Ht_eff)
           System.out.print(ht+" ");
       System.out.println();
       System.out.print("Ht_effmax:");
       for(double ht:Ht_effmax)
           System.out.print(ht+" ");
       System.out.println();
       double tc = light.tcValue(1,1);
       int roof = 0;
       int wall = 0;
       double hx = 2.8,l = 10,b_room = 10;
       double CU = light.CUValue(roof,wall,hx,l,b_room);
       System.out.println("CU:"+CU);
       int climate = 0,level = 0,shape = 0;
       double Acd = light.AcdValue(level,shape,climate);
       int glassType = 0,glassSpecifications = 0;
       double C_av = light.t0Value(glassType,glassSpecifications)*tc*light.tw*CU*Acd;
       System.out.println("C_av:"+C_av);
       double Ad = light.AcValue(l,b_room,hx,tc*light.tw*light.t0Value(glassType,glassSpecifications),
              C_av );
       System.out.println("Ad:"+Ad);
       double[] Lb = new double[handle.n.length];
       for(int i = 0;i < Lb.length;i++)
           Lb[i] = light.LbCal(handle.paraCal(handle.n)[i],lat,Hb.get(i));
       double E_0 = light.standard_matrix[0];
       double[] delta = new double[Lb.length];
       for(int i = 0;i < Lb.length;i++)
           delta[i] = E_0-Lb[i]*C_av;
       double sum = light.Saving(E_0,roof,wall,l,b_room,delta);
       System.out.println("sum:"+sum);

       //完整显示
       double [] Ht_effmax0 = new double[12];
       double [] Ht_eff0 = new double[12];
       for(int i = 0,k = 0;i < 12;i++)
       {
           if(i == handle.month[k])
           {
               Ht_effmax0[i] = 0.0;
               Ht_eff0[i] = 0.0;
               k++;
           }
           else
           {
               Ht_effmax0[i] = Ht_effmax[i-k];
               Ht_eff0[i] = Ht_eff[i-k];
           }
       }

       Answer answer = new Answer();
       answer.setLat(lat);
       answer.setLon(lon);
       answer.setBestAngle(bestAngle);
       answer.setSavePower(sum);
       answer.setExpectPowerGeneration(handle.avrg_userHt(Ht_eff)*365);
       answer.setAveragePowerGeneration(handle.avrg_userHt(Ht_eff));
       answer.setArea(Ad);
       answer.setBestPower(Ht_effmax0);
       answer.setCurrentPower(Ht_eff0);
       System.out.println("BESTPOWER:"+ Arrays.toString(answer.getBestPower()));
   }
}
