package com.yjjk.monitor.mapper;

import com.yjjk.monitor.entity.ZsBedInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ZsBedInfoMapper {
    int deleteByPrimaryKey(Integer bedId);

    int insert(ZsBedInfo record);

    int insertSelective(ZsBedInfo record);

    ZsBedInfo selectByPrimaryKey(Integer bedId);

    int updateByPrimaryKeySelective(ZsBedInfo record);

    int updateByPrimaryKey(ZsBedInfo record);

    List<ZsBedInfo> selectEmptyBeds(Map<String, Object> paramMap);
}