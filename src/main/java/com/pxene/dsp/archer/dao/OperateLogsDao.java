package com.pxene.dsp.archer.dao;

import com.pxene.dsp.archer.model.OperateLogsModel;
import com.pxene.dsp.archer.model.OperateLogsModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OperateLogsDao {
    int countByExample(OperateLogsModelExample example);

    int deleteByExample(OperateLogsModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(OperateLogsModel record);

    int insertSelective(OperateLogsModel record);

    List<OperateLogsModel> selectByExampleWithBLOBs(OperateLogsModelExample example);

    List<OperateLogsModel> selectByExample(OperateLogsModelExample example);

    OperateLogsModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OperateLogsModel record, @Param("example") OperateLogsModelExample example);

    int updateByExampleWithBLOBs(@Param("record") OperateLogsModel record, @Param("example") OperateLogsModelExample example);

    int updateByExample(@Param("record") OperateLogsModel record, @Param("example") OperateLogsModelExample example);

    int updateByPrimaryKeySelective(OperateLogsModel record);

    int updateByPrimaryKeyWithBLOBs(OperateLogsModel record);

    int updateByPrimaryKey(OperateLogsModel record);
}