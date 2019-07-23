package com.yjjk.monitor.mapper;
import com.yjjk.monitor.entity.ZsDepartmentInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ZsDepartmentInfoMapper {
    int deleteByPrimaryKey(Integer departmentId);

    int insert(ZsDepartmentInfo record);

    int insertSelective(ZsDepartmentInfo record);

    ZsDepartmentInfo selectByPrimaryKey(Integer departmentId);

    int updateByPrimaryKeySelective(ZsDepartmentInfo record);

    int updateByPrimaryKey(ZsDepartmentInfo record);

    /**
     * 查询医院信息详情
     * @param departmentId
     * @return
     */
    List<ZsDepartmentInfo> selectDetail(Integer departmentId);
}