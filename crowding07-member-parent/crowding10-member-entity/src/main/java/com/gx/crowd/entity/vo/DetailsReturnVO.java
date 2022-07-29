package com.gx.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/13 11:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailsReturnVO implements Serializable {
    private Integer id;
    private Long supportMoney; //支持金额
    private Integer signalPurchase; //单笔购限额 0无限额
    private Integer purchase;//限购数量
    private Integer freight; //0为包邮,1为不包邮
    private Integer returnDate;//众筹多少天后发送回报
    private String content;//回报内容
}
