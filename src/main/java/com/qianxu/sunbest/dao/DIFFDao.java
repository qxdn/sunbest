package com.qianxu.sunbest.dao;

import com.qianxu.sunbest.model.DIFF;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 查找DIFF
 */
@Mapper
public interface DIFFDao {
    @Deprecated
    List<DIFF> getAll();
    DIFF getByLATAndLON(double lat,double lon);
}
