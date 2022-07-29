package com.gx.crowd.controller;

import com.gx.crowd.api.MySqlServerRemote;
import com.gx.crowd.entity.vo.ProjectTypeVO;
import com.gx.crowd.entity.vo.ReturnJson;
import com.gx.crowd.utils.CrowdConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/12 14:31
 */
@Controller
public class PortalController {
    @Autowired
    private MySqlServerRemote mySqlServerRemote;

    @RequestMapping("/")
    public String index(Model model) {
        //查询分类的数据
        ReturnJson<List<ProjectTypeVO>> projectTypeRemote = mySqlServerRemote.getProjectTypeRemote();
        if (projectTypeRemote.getStatus()) {

            model.addAttribute(CrowdConstants.PORTAL_TYPE_VO_LIST, projectTypeRemote.getData());
        }
        return "member-index";
    }
}
