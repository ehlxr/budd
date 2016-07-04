package com.pxene.dsp.archer.dao;

import com.pxene.dsp.archer.model.AdGroupAppTargetFilterModel;
import com.pxene.dsp.archer.model.AdGroupAppTargetFilterModelExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AdGroupAppTargetFilterDao {
    int countByExample(AdGroupAppTargetFilterModelExample example);

    int deleteByExample(AdGroupAppTargetFilterModelExample example);

    int deleteByPrimaryKey(String id);

    int insert(AdGroupAppTargetFilterModel record);

    int insertSelective(AdGroupAppTargetFilterModel record);

    List<AdGroupAppTargetFilterModel> selectByExample(AdGroupAppTargetFilterModelExample example);

    AdGroupAppTargetFilterModel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AdGroupAppTargetFilterModel record, @Param("example") AdGroupAppTargetFilterModelExample example);

    int updateByExample(@Param("record") AdGroupAppTargetFilterModel record, @Param("example") AdGroupAppTargetFilterModelExample example);

    int updateByPrimaryKeySelective(AdGroupAppTargetFilterModel record);

    int updateByPrimaryKey(AdGroupAppTargetFilterModel record);

    public List<Map<String,String>> selectAdxByAppParentCode(String parentcode);
}