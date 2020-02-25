package com.qianxu.sunbest.dao;


import com.qianxu.sunbest.model.DNR;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 查找DNR
 */
@Mapper
public interface DNRDao {
    @Deprecated
    List<DNR> getAll();
    DNR getByLATAndLON(double lat,double lon);
}
