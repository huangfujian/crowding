package com.gx.crowd.controller;

import com.gx.crowd.api.MySqlServerRemote;
import com.gx.crowd.config.UploadPictureProperties;
import com.gx.crowd.entity.vo.*;
import com.gx.crowd.utils.CrowdConstants;
import com.gx.crowd.utils.CrowdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/12 18:59
 */
@Controller
@Slf4j
public class ProjectController {
    @Autowired
    private UploadPictureProperties uploadPictureProperties;
    @Autowired
    private MySqlServerRemote mySqlServerRemote;

    @PostMapping("/create/project/information")
    public String createProject(ProjectVO projectVO,
                                MultipartFile headerPicture,
                                List<MultipartFile> detailPictureList,
                                HttpSession session,
                                Model model) {
        //上传头图片
        try {
            ReturnJson<String> headerPictureResult = CrowdUtils.uploadPictureFile(
                    uploadPictureProperties.getEndpoint(),
                    uploadPictureProperties.getAccessKeyId(),
                    uploadPictureProperties.getAccessKeySecret(),
                    uploadPictureProperties.getBucketName(),
                    uploadPictureProperties.getBucketDomain(),
                    headerPicture.getInputStream(),
                    headerPicture.getOriginalFilename());
            //上传成功
            if (headerPictureResult.getStatus()) {
                //设置路径
                projectVO.setHeaderPicturePath(headerPictureResult.getData());
            } else {
                model.addAttribute(CrowdConstants.MESSAGE, CrowdConstants.HENDER_PICTURE_UPLOAD_FAIL);
                return "project-launch";
            }
            List<String> detailsPicturePathList = new ArrayList<>();
            //上传项目的详情图片
            for (MultipartFile multipartFile : detailPictureList) {
                ReturnJson<String> detailsPictureResult = CrowdUtils.uploadPictureFile(uploadPictureProperties.getEndpoint(),
                        uploadPictureProperties.getAccessKeyId(),
                        uploadPictureProperties.getAccessKeySecret(),
                        uploadPictureProperties.getBucketName(),
                        uploadPictureProperties.getBucketDomain(),
                        multipartFile.getInputStream(),
                        multipartFile.getOriginalFilename());
                if (detailsPictureResult.getStatus()) {
                    detailsPicturePathList.add(detailsPictureResult.getData());
                } else {
                    model.addAttribute(CrowdConstants.MESSAGE, CrowdConstants.DETAILS_PICTURE_UPLOAD_FAIL);
                    return "project-launch";
                }
            }
            projectVO.setDetailsPicturePath(detailsPicturePathList);
            session.setAttribute(CrowdConstants.PROJECT_VO, projectVO);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return "project-return";
    }

    @ResponseBody
    @RequestMapping("/create/upload/return/picture")
    public ReturnJson<String> uploadReturnPicture(MultipartFile returnPicture) {
        try {
            ReturnJson<String> returnJson = CrowdUtils.uploadPictureFile(
                    uploadPictureProperties.getEndpoint(),
                    uploadPictureProperties.getAccessKeyId(),
                    uploadPictureProperties.getAccessKeySecret(),
                    uploadPictureProperties.getBucketName(),
                    uploadPictureProperties.getBucketDomain(),
                    returnPicture.getInputStream(),
                    returnPicture.getOriginalFilename());
            if (returnJson.getStatus()) {
                return ReturnJson.returnSuccessWithData(returnJson.getData());
            } else {
                return ReturnJson.returnSuccessWithData(returnJson.getMsg());
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            return ReturnJson.returnFail(e.getMessage());
        }
    }

    @RequestMapping("/create/save/return")
    @ResponseBody
    public ReturnJson returnSetup(ReturnVO returnVO, HttpSession session) {
        ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstants.PROJECT_VO);
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();
        if (returnVOList == null) {
            returnVOList = new ArrayList<>();
            projectVO.setReturnVOList(returnVOList);
        }
        returnVOList.add(returnVO);
        session.setAttribute(CrowdConstants.PROJECT_VO, projectVO);
        return ReturnJson.returnSuccess();
    }

    @RequestMapping("/create/confirm")
    public String confirmProject(MemberConfirmInfoVO memberConfirmInfoVO,
                                 HttpSession session,
                                 Model model) {
        LoginMemberVO loginMemberVO = (LoginMemberVO) session.getAttribute(CrowdConstants.LOGIN_MEMBER_VO);
        memberConfirmInfoVO.setMemberid(loginMemberVO.getId());
        ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstants.PROJECT_VO);
        projectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);
        //保存到数据库
        ReturnJson<String> saveResult = mySqlServerRemote.saveProjectVORemote(projectVO);
        if (saveResult.getStatus()) {
            //将session清空
            session.removeAttribute(CrowdConstants.PROJECT_VO);
            //为true,保存成功
            return "redirect:http://localhost/project/launch/success"; //成功
        } else {
            //为false,保存失败
            model.addAttribute(CrowdConstants.MESSAGE, CrowdConstants.SYSTEM_BUSY);
            log.error(saveResult.getMsg());
        }
        return "project-confirm"; //失败
    }

    @RequestMapping("/create/project/detail/info/{id}")
    public String projectDetails(@PathVariable("id") Integer projectId, Model model) {
        ReturnJson<DetailsProjectVO> detailsProjectVoRemote = mySqlServerRemote.getDetailsProjectVoRemote(projectId);
        if (detailsProjectVoRemote.getStatus()) {
            model.addAttribute(CrowdConstants.DETAILS_PROJECT_VO, detailsProjectVoRemote.getData());
        }
        return "project-details";
    }
}
