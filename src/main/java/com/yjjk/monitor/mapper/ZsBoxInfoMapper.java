package com.yjjk.monitor.mapper;

import com.yjjk.monitor.entity.ZsBoxInfo;

import java.util.List;

public interface ZsBoxInfoMapper {
    int deleteByPrimaryKey(Integer boxId);

    int insert(ZsBoxInfo record);

    int insertSelective(ZsBoxInfo record);

    ZsBoxInfo selectByPrimaryKey(Integer boxId);

    int updateByPrimaryKeySelective(ZsBoxInfo record);

    int updateByPrimaryKey(ZsBoxInfo record);

    /**
     * 查询所有体温盒子信息
     * @return
     */
    List<ZsBoxInfo> selectAll();
}