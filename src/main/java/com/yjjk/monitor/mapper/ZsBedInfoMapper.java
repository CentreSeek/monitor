package com.yjjk.monitor.mapper;

import com.yjjk.monitor.entity.ZsBedInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ZsBedInfoMapper {
    int deleteByPrimaryKey(Integer bedId);

    int insert(ZsBedInfo record);

    int insertSelective(ZsBedInfo record);

    ZsBedInfo selectByPrimaryKey(Integer bedId);

    int updateByPrimaryKeySelective(ZsBedInfo record);

    int updateByPrimaryKey(ZsBedInfo record);
}