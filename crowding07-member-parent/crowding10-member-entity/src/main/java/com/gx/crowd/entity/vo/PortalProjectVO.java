package com.gx.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.omg.CORBA.ORB;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/13 8:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortalProjectVO {
    private Integer id;
    private String projectName;
    private Integer money;
    private Integer supporter;
    private String deploydate;
    private Integer percentage;
    private String headerPicturePath;
}
