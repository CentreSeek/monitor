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

    /**
     * select---使用managerId查询
     * @param managerId
     * @return
     */
    ZsLoginState selectByManagerId(Integer managerId);
}