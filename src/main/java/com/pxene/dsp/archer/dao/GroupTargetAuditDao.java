package com.pxene.dsp.archer.dao;

import com.pxene.dsp.archer.model.GroupTargetAuditModel;
import com.pxene.dsp.archer.model.GroupTargetAuditModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GroupTargetAuditDao {
    int countByExample(GroupTargetAuditModelExample example);

    int deleteByExample(GroupTargetAuditModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(GroupTargetAuditModel record);

    int insertSelective(GroupTargetAuditModel record);

    List<GroupTargetAuditModel> selectByExample(GroupTargetAuditModelExample example);

    GroupTargetAuditModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") GroupTargetAuditModel record, @Param("example") GroupTargetAuditModelExample example);

    int updateByExample(@Param("record") GroupTargetAuditModel record, @Param("example") GroupTargetAuditModelExample example);

    int updateByPrimaryKeySelective(GroupTargetAuditModel record);

    int updateByPrimaryKey(GroupTargetAuditModel record);
}