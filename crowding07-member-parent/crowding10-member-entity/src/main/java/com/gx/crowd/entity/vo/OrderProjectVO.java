package com.gx.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/13 11:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProjectVO implements Serializable{
    private static final long serialVersionUID = 9142630511459768390L;
    private String projectName;
    private String returnContent;
    private Integer returnCount;
    private Integer supportMoney;
    private Integer freight;
    private String launchName;
    private Integer signalPurchase;
    private Integer purchase;
}
