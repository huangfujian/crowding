package com.gx.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/12 19:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectVO implements Serializable {
    private Integer id;
    private String projectName;
    private String projectDescription;
    private Integer money;
    private Integer day;
    private String headerPicturePath;
    private List<String> detailsPicturePath;
    private List<Integer> typeIdList;
    private List<Integer> tagIdList;
    private List<ReturnVO> returnVOList;
    private MemberLaunchInfoVO memberLaunchInfoVO;
    private MemberConfirmInfoVO memberConfirmInfoVO;
}