package com.yjjk.monitor.mapper;

import com.yjjk.monitor.entity.ZsLoginState;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ZsLoginStateMapper {
    int deleteByPrimaryKey(String token);

    int insert(ZsLoginState record);

    int insertSelective(ZsLoginState record);

    ZsLoginState selectByPrimaryKey(String token);

    int updateByPrimaryKeySelective(ZsLoginState record);

    int updateByPrimaryKey(ZsLoginState record);

    ZsLoginState selectByManagerId(Integer managerId);

    /**
     * 使用token查找departmentId
     * @param token
     * @return
     */
    Integer selectDepartmentIdByToken(String token);
}