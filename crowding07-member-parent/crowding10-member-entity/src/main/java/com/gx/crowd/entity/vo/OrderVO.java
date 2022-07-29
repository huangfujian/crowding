package com.gx.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/13 15:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO implements Serializable {
    private static final long serialVersionUID = -2811145354692667973L;
    private Integer id;
    private String orderNum;
    private String payOrderNum;
    private Integer orderAmount;
    private Integer invoice;
    private String invoiceTitle;
    private String orderRemark;
    private String addressId;
    private OrderProjectVO orderProjectVO;
}
