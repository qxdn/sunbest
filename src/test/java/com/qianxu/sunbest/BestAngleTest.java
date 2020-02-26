package com.qianxu.sunbest;
import com.qianxu.sunbest.dao.DIFFDao;
import com.qianxu.sunbest.dao.DNRDao;
import com.qianxu.sunbest.dao.SFCDao;
import com.qianxu.sunbest.model.DIFF;
import com.qianxu.sunbest.model.DNR;
import com.qianxu.sunbest.model.Predict;
import com.qianxu.sunbest.util.Handle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class BestAngleTest {

    @Autowired
    DIFFDao diffDao;

    @Autowired
    DNRDao dnrDao;

    @Autowired
    SFCDao sfcDao;

    @Autowired
    Handle handle;

   /* @Autowired
    Predict predict;*/

    @Test
    void Test(){
        Predict predict = new Predict();
        predict.setLat(30.50);
        predict.setLon(139.25);
        predict.setAzimuth(10.0);
        double lat = predict.getLat();
        double lon = predict.getLon();
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

        System.out.println(handle.avrg_bestAngle(predict.getAzimuth()>0?'n':'y',lat,handle.paraCal(handle.n),
                Hb,Hd,predict.getAzimuth()));
    }

}
