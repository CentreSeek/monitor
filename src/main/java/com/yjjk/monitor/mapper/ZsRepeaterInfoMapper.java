package com.yjjk.monitor.mapper;

import com.yjjk.monitor.entity.ZsRepeaterInfo;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

import javax.validation.constraints.Max;
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
}