package com.gx.crowd.service;

import com.gx.crowd.entity.vo.AddressVO;
import com.gx.crowd.entity.vo.OrderProjectVO;
import com.gx.crowd.entity.vo.OrderVO;

import java.util.List;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/13 11:59
 */
public interface OrderService {
    OrderProjectVO getOrderProjectVO(Integer returnId);

    List<AddressVO> getAddressList(Integer id);

    void saveOrderVO(OrderVO orderVO);

}
