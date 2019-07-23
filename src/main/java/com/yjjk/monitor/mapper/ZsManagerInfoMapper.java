package com.yjjk.monitor.mapper;
import com.yjjk.monitor.entity.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ZsManagerInfoMapper {
    int deleteByPrimaryKey(Integer managerId);

    int insert(ZsManagerInfo record);

    int insertSelective(ZsManagerInfo record);

    ZsManagerInfo selectByPrimaryKey(Integer managerId);

    int updateByPrimaryKeySelective(ZsManagerInfo record);

    int updateByPrimaryKey(ZsManagerInfo record);

    /**
     * select---查询所有正常管理员
     * @return
     */
    List<ZsManagerInfo>  selectNormalList();

    /**
     * select---使用账户查询用户信息
     * @param managerInfo
     * @return
     */
    ZsManagerInfo selectByAccount(ZsManagerInfo managerInfo);
}