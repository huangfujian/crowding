package com.gx.crowd.service;

import com.gx.crowd.entity.vo.DetailsProjectVO;
import com.gx.crowd.entity.vo.ProjectTypeVO;
import com.gx.crowd.entity.vo.ProjectVO;

import java.util.List;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/13 7:24
 */
public interface ProjectService {
    void saveProjectVO(ProjectVO projectVO);

    List<ProjectTypeVO> getProjectTypeVOList();

  DetailsProjectVO getDetailsProjectVO(Integer projectId);
}
