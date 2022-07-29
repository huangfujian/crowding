package com.gx.crowd.controller;

import com.gx.crowd.entity.vo.AddressVO;
import com.gx.crowd.entity.vo.OrderProjectVO;
import com.gx.crowd.entity.vo.OrderVO;
import com.gx.crowd.entity.vo.ReturnJson;
import com.gx.crowd.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/13 11:58
 */
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/get/order/project/vo/remote")
    public ReturnJson<OrderProjectVO> getOrderProjectVORemote(@RequestParam("returnId") Integer returnId) {
        try {
            OrderProjectVO orderProjectVO = orderService.getOrderProjectVO(returnId);
            return ReturnJson.returnSuccessWithData(orderProjectVO);
        } catch (Exception e) {
            return ReturnJson.returnFail(e.getMessage());
        }
    }

    @RequestMapping("/get/address/by/memberid/list/remote")
    public ReturnJson<List<AddressVO>> getAddressByMemberIdListRemote(@RequestParam("id") Integer id) {
        try {
            List<AddressVO> addressList = orderService.getAddressList(id);
            return ReturnJson.returnSuccessWithData(addressList);
        } catch (Exception e) {
            return ReturnJson.returnFail(e.getMessage());
        }
    }

    @RequestMapping("/save/order/vo/remote")
    public ReturnJson<String> saveOrderVORemote(@RequestBody OrderVO orderVO) {
        try {
            orderService.saveOrderVO(orderVO);
            return ReturnJson.returnSuccess();//成功
        } catch (Exception e) {
            return ReturnJson.returnFail(e.getMessage());//失败
        }
    }
}
