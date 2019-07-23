package com.yjjk.monitor.mapper;

import com.yjjk.monitor.entity.ZsRoomInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ZsRoomInfoMapper {
    int deleteByPrimaryKey(Integer roomId);

    int insert(ZsRoomInfo record);

    int insertSelective(ZsRoomInfo record);

    ZsRoomInfo selectByPrimaryKey(Integer roomId);

    int updateByPrimaryKeySelective(ZsRoomInfo record);

    int updateByPrimaryKey(ZsRoomInfo record);
}