package com.gx.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/13 11:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailsProjectVO implements Serializable {
    private Integer id;
    private String projectName;
    private String projectDescription;
    private Integer supporter;
    private Integer supportMoney;
    private Integer money;
    private Integer percentage;
    private Integer lastDay;
    private String deployDate;
    private Integer status;
    private Integer day;
    private Integer memberId;
    private String headerPicturePath;
    private Integer follower;
    private List<String> detailPicturePathList;
    private MemberLaunchInfoVO memberLaunchInfoVO; //项目发起人信息
    private List<DetailsReturnVO> detailsReturnVOList;
}
