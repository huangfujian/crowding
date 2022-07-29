package com.gx.crowd.service.impl;

import com.gx.crowd.entity.po.*;
import com.gx.crowd.entity.vo.*;
import com.gx.crowd.mapper.*;
import com.gx.crowd.service.ProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/13 7:24
 */
@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {
    @Resource
    private ProjectPOMapper projectPOMapper;
    @Resource
    private ProjectItemPicPOMapper projectItemPicPOMapper;
    @Resource
    private ReturnPOMapper returnPOMapper;
    @Resource
    private MemberConfirmInfoPOMapper memberConfirmInfoPOMapper;
    @Resource
    private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void saveProjectVO(ProjectVO projectVO) {
        ProjectPO projectPO = new ProjectPO();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String data = sdf.format(new Date());
        BeanUtils.copyProperties(projectVO, projectPO);
        projectPO.setCreatedate(data);
        projectPOMapper.insert(projectPO);
        //新增图片
        Integer projectId = projectPO.getId();
        List<String> detailsPicturePath = projectVO.getDetailsPicturePath();
        projectItemPicPOMapper.bathInsertPic(projectId, detailsPicturePath);
        List<Integer> typeIdList = projectVO.getTypeIdList();
        //     List<Integer> typeIdList;
        projectPOMapper.bathInsertMiddleType(typeIdList, projectId);
        List<Integer> tagIdList = projectVO.getTagIdList();
        //     List<Integer> tagIdList;
        projectPOMapper.bathInsertMiddleTag(tagIdList, projectId);
        //     List<ReturnVO> returnVOList;
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();
        List<ReturnPO> returnPOList = new ArrayList<>();
        for (ReturnVO returnVO : returnVOList) {
            ReturnPO returnPO = new ReturnPO();
            BeanUtils.copyProperties(returnVO, returnPO);
            returnPO.setProjectid(projectId);
            returnPOList.add(returnPO);
        }
        returnPOMapper.bathInsertReturnPO(returnPOList);
        //     MemberConfirmInfoVO memberConfirmInfoVO;
        MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();
        MemberConfirmInfoPO memberConfirmInfoPO = new MemberConfirmInfoPO();
        BeanUtils.copyProperties(memberConfirmInfoVO, memberConfirmInfoPO);
        memberConfirmInfoPOMapper.insertSelective(memberConfirmInfoPO);
        //     MemberLaunchInfoVO memberLaunchInfoVO;
        MemberLaunchInfoVO memberLaunchInfoVO = projectVO.getMemberLaunchInfoVO();
        MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();
        BeanUtils.copyProperties(memberLaunchInfoVO, memberLaunchInfoPO);
        memberLaunchInfoPOMapper.insertSelective(memberLaunchInfoPO);
    }
    @Override
    public List<ProjectTypeVO> getProjectTypeVOList() {
        return projectPOMapper.selectProjectTypeVO();
    }
    @Override
    public DetailsProjectVO getDetailsProjectVO(Integer projectId) {
        return projectPOMapper.selectDetailsProjectVO(projectId);
    }
}
