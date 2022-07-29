package com.gx.crowd.controller;

import com.gx.crowd.entity.vo.DetailsProjectVO;
import com.gx.crowd.entity.vo.ProjectTypeVO;
import com.gx.crowd.entity.vo.ProjectVO;
import com.gx.crowd.entity.vo.ReturnJson;
import com.gx.crowd.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/13 7:24
 */
@RestController
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @RequestMapping("/save/project/vo/remote")
    public ReturnJson<String> saveProjectVORemote(@RequestBody ProjectVO projectVO) {
        try {
            projectService.saveProjectVO(projectVO);
            return ReturnJson.returnSuccess();
        } catch (Exception e) {
            return ReturnJson.returnFail(e.getMessage());
        }

    }

    @RequestMapping("/get/project/type/remote")
    public ReturnJson<List<ProjectTypeVO>> getProjectTypeRemote() {
        try {
            List<ProjectTypeVO> projectTypeVOList = projectService.getProjectTypeVOList();
            return ReturnJson.returnSuccessWithData(projectTypeVOList);
        } catch (Exception e) {
            return ReturnJson.returnFail(e.getMessage());
        }

    }

    @RequestMapping("/get/details/project/vo/remote")
    public ReturnJson<DetailsProjectVO> getDetailsProjectVoRemote(@RequestParam("projectId") Integer projectId) {
        try {
            DetailsProjectVO detailsProjectVO = projectService.getDetailsProjectVO(projectId);
            return ReturnJson.returnSuccessWithData(detailsProjectVO);
        } catch (Exception e) {
            return ReturnJson.returnFail(e.getMessage());
        }
    }
}
