package com.qianxu.sunbest;

import com.qianxu.sunbest.dao.DIFFDao;
import com.qianxu.sunbest.dao.DNRDao;
import com.qianxu.sunbest.dao.SFCDao;
import com.qianxu.sunbest.model.DIFF;
import com.qianxu.sunbest.model.DNR;
import com.qianxu.sunbest.model.SFC;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DaoTest {

    @Autowired
    DIFFDao diffDao;

    @Autowired
    DNRDao dnrDao;

    @Autowired
    SFCDao sfcDao;



    @ParameterizedTest
    @CsvSource({"-89.75,-163.25","-89.75,-164.75"})
    void getDIFFByLATAndLONTest(double lat,double lon){
        DIFF diff =diffDao.getByLATAndLON(lat,lon);
        System.out.println(diff);
    }

    @ParameterizedTest
    @CsvSource({"-89.75,-163.25","-89.75,-164.75"})
    void getDNRByLATAndLONTest(double lat,double lon){
        DNR dnr =dnrDao.getByLATAndLON(lat,lon);
        System.out.println(dnr);
    }

    @ParameterizedTest
    @CsvSource({"-89.75,-163.25","-89.75,-164.75"})
    void getSFCByLATAndLONTest(double lat,double lon){
        SFC sfc =sfcDao.getByLATAndLON(lat,lon);
        System.out.println(sfc);
    }


    @Test
    void getAllDIFFTest(){
        List<DIFF> diffs =diffDao.getAll();
        for(DIFF diff:diffs){
            System.out.println(diff);
        }
    }

    @Test
    void getAllDNRTest(){
        List<DNR> dnrs =dnrDao.getAll();
        for(DNR dnr:dnrs){
            System.out.println(dnr);
        }
    }

    @Test
    void getAllSFCTest(){
        List<SFC> sfcs =sfcDao.getAll();
        for(SFC sfc:sfcs){
            System.out.println(sfc);
        }
    }
}
