package com.yjjk.monitor.mapper;
import com.yjjk.monitor.entity.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

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
    List<ZsManagerInfo>  selectNormalList(Map<String, Object> paramMap);

    /**
     * select---查询管理员数量
     * @param paramMap
     * @return
     */
    int selectNormalListCount(Map<String, Object> paramMap);

    /**
     * select---使用账户查询用户信息
     * @param managerInfo
     * @return
     */
    ZsManagerInfo selectByAccount(ZsManagerInfo managerInfo);

    /**
     * 使用token查询管理员信息
     * @param Token
     * @return
     */
    ZsManagerInfo selectByToken(String Token);

}