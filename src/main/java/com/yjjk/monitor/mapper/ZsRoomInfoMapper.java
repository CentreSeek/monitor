package com.yjjk.monitor.mapper;

import com.yjjk.monitor.entity.ZsRoomInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ZsRoomInfoMapper {
    int deleteByPrimaryKey(Integer roomId);

    int insert(ZsRoomInfo record);

    int insertSelective(ZsRoomInfo record);

    ZsRoomInfo selectByPrimaryKey(Integer roomId);

    int updateByPrimaryKeySelective(ZsRoomInfo record);

    int updateByPrimaryKey(ZsRoomInfo record);

    /**
     * 查询房间信息
     * @return
     */
    List<ZsRoomInfo> selectRooms(Integer departmentId);
}