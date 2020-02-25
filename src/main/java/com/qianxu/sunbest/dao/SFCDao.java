package com.qianxu.sunbest.dao;

import com.qianxu.sunbest.model.SFC;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 查找SFC
 */
@Mapper
public interface SFCDao {
    @Deprecated
    List<SFC> getAll();
    SFC getByLATAndLON(double lat,double lon);
}
