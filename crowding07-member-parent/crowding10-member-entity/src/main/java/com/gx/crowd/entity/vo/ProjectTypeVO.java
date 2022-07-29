package com.gx.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/13 8:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTypeVO implements Serializable {
    private Integer id;
    private String name;
    private String remark;
    List<PortalProjectVO> projectVOList;
}
