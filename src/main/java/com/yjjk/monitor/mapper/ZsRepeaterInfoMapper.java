package com.yjjk.monitor.mapper;

import com.yjjk.monitor.entity.ZsRepeaterInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ZsRepeaterInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZsRepeaterInfo record);

    int insertSelective(ZsRepeaterInfo record);

    ZsRepeaterInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZsRepeaterInfo record);

    int updateByPrimaryKey(ZsRepeaterInfo record);

    /**
     * 分页查询路由信息
     * @param record
     * @return
     */
    List<ZsRepeaterInfo> selectRepeaters(ZsRepeaterInfo record);

    /**
     * 查询路由总数
     * @param record
     * @return
     */
    int selectRepeaterCount(ZsRepeaterInfo record);

    /**
     * @Description 查询repeaterId
     * @param bedId
     * @return int
     */
    int selectByBedId(Integer bedId);

    /**
     * @Description 获取当前房间绑定的路由总数
     * @param zsRepeaterInfo
     * @return int
     */
    int isExistRepeater(ZsRepeaterInfo zsRepeaterInfo);
}