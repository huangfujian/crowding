package com.gx.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/12 20:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnVO implements Serializable {
    private Integer id;
    private Integer type;
    private Integer supportmoney;
    private String content;
    private String describPicPath;
    private Integer count;
    private Integer signalpurchase;
    private Integer purchase;
    private Integer freight;
    private Integer invoice;
    private Integer returndate;
}
