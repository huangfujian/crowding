package com.gx.crowd.mapper;

import com.gx.crowd.entity.po.ProjectPO;
import com.gx.crowd.entity.po.ProjectPOExample;
import com.gx.crowd.entity.vo.DetailsProjectVO;
import com.gx.crowd.entity.vo.ProjectTypeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectPOMapper {
    int countByExample(ProjectPOExample example);

    int deleteByExample(ProjectPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProjectPO record);

    int insertSelective(ProjectPO record);

    List<ProjectPO> selectByExample(ProjectPOExample example);

    ProjectPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByExample(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByPrimaryKeySelective(ProjectPO record);

    int updateByPrimaryKey(ProjectPO record);

    int bathInsertMiddleType(@Param("typeIdList") List<Integer> typeIdList, @Param("projectId") Integer projectId);

    int bathInsertMiddleTag(@Param("tagIdList") List<Integer> tagIdList,@Param("projectId") Integer projectId);

    List<ProjectTypeVO> selectProjectTypeVO();

    DetailsProjectVO selectDetailsProjectVO(Integer projectId);
}