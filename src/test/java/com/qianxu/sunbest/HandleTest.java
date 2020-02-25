package com.qianxu.sunbest;
import com.qianxu.sunbest.dao.DIFFDao;
import com.qianxu.sunbest.dao.DNRDao;
import com.qianxu.sunbest.dao.SFCDao;
import com.qianxu.sunbest.model.DIFF;
import com.qianxu.sunbest.model.DNR;
import com.qianxu.sunbest.util.Handle;
import com.qianxu.sunbest.util.Light;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

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
       double bestAngle;
       double lat = 68.75;
      // double lat = 30.75;
       double lon = 139.25;
       /*double[] Ht_user;
       double[] Ht_usermax = new double[handle.n.length];
       double[] Ht_effmax = new double[handle.n.length];
       double[] Ht_eff = new double[handle.n.length];*/
       if(Math.max(Math.abs((int)(Math.round(Math.abs(lat)*100))%100-25),25) != 25)
           lat = Math.floor(lat)+(lat>0?0.75:0.25);
       else
           lat = Math.floor(lat)+(lat>0?0.25:0.75);
       if(Math.max(Math.abs((int)(Math.round(Math.abs(lon)*100))%100-25),25) != 25)
           lon = Math.floor(lon)+(lon>0?0.75:0.25);
       else
           lon = Math.floor(lon)+(lon>0?0.25:0.75);
       DIFF diff = diffDao.getByLATAndLON(lat,lon);
       DNR dnr = dnrDao.getByLATAndLON(lat,lon);
       ArrayList<Double> Hd = handle.getHd(diff);
       ArrayList<Double>  Hb = handle.getHb(dnr);
       System.out.println(Hb);
       System.out.println(Hd);
       handle.arrangeData(Hb,Hd,handle.n);
       System.out.println(Hb);
       System.out.println(Hd);
       System.out.println(handle.n.length);
       double[] Ht_user;
       double[] Ht_usermax = new double[handle.n.length];
       double[] Ht_effmax = new double[handle.n.length];
       double[] Ht_eff = new double[handle.n.length];
       bestAngle = handle.avrg_bestAngle('y',lat,handle.paraCal(handle.n),
               Hb,Hd,0.0);
       System.out.println(bestAngle);
       Ht_user = handle.userHt('y',lat,handle.paraCal(handle.n),Hb,Hd,20.0).clone();
       for(double ht:Ht_user)
           System.out.print(ht+" ");
       System.out.println();
        for(int i = 0;i < Ht_usermax.length;i++)
            Ht_usermax[i] = handle.userHt('y',lat,handle.paraCal(handle.n),Hb,Hd,Math.max(handle.Uopt1[i],0.0))[i];
       handle.b_0 = 20.0;
        for(double ht:Ht_usermax)
           System.out.print(ht+" ");
       System.out.println();
       System.out.println(handle.avrg_userHt(Ht_user));
       for(int i = 0;i < Ht_user.length;i++)
       {
           Ht_effmax[i] = Ht_usermax[i]*light.PV_efficience[0];
           Ht_eff[i] = Ht_user[i]*light.PV_efficience[0];
       }
       for(double ht:Ht_eff)
           System.out.print(ht+" ");
       System.out.println();
       for(double ht:Ht_effmax)
           System.out.print(ht+" ");
       System.out.println();
       double tc = light.tcValue(1,1);
       int roof = 0;
       int wall = 0;
       double hx = 5.0,l = 10.0,b_room = 10.0;
       double CU = light.CUValue(roof,wall,hx,l,b_room);
       System.out.println(CU);
       int climate = 2,level = 2,shape = 1;
       double Acd = light.AcdValue(level,shape,climate);
       int glassType = 0,glassSpecifications = 0;
       double C_av = light.t0Value(glassType,glassSpecifications)*tc*light.tw*CU*Acd;
       System.out.println(C_av);
       double Ad = light.AcValue(l,b_room,hx,tc*light.tw*light.t0Value(glassType,glassSpecifications),
              C_av );
       System.out.println(Ad);
       double[] Lb = new double[handle.n.length];
       for(int i = 0;i < Lb.length;i++)
           Lb[i] = light.LbCal(handle.paraCal(handle.n)[i],lat,Hb.get(i));
       double E_0 = light.standard_matrix[0];
       double[] delta = new double[Lb.length];
       for(int i = 0;i < Lb.length;i++)
           delta[i] = E_0-Lb[i]*C_av;
       double sum = light.Saving(E_0,roof,wall,l,b_room,delta);
       System.out.println(sum);

   }
}
