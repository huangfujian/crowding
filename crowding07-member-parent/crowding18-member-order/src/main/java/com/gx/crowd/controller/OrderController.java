package com.gx.crowd.controller;

import com.gx.crowd.api.MySqlServerRemote;
import com.gx.crowd.entity.vo.AddressVO;
import com.gx.crowd.entity.vo.LoginMemberVO;
import com.gx.crowd.entity.vo.OrderProjectVO;
import com.gx.crowd.entity.vo.ReturnJson;
import com.gx.crowd.utils.CrowdConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/13 11:44
 */
@Controller
public class OrderController {
    @Autowired
    private MySqlServerRemote mySqlServerRemote;

    @RequestMapping("/confirm/return/info/{id}")
    public String orderReturnInfo(@PathVariable("id") Integer returnId, HttpSession session) {
        ReturnJson<OrderProjectVO> orderProjectVO = mySqlServerRemote.getOrderProjectVORemote(returnId);
        if (orderProjectVO.getStatus()) {
            session.setAttribute(CrowdConstants.ORDER_PROJECT_VO, orderProjectVO.getData());
        }
        return "redirect:http://localhost/order/return/page";
    }

    /**
     * 确认订单
     *
     * @param count
     * @return
     */
    @RequestMapping("/confirm/order/{count}")
    public String confirmOrder(@PathVariable("count") Integer count,
                               HttpSession session, Model model) {
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute(CrowdConstants.ORDER_PROJECT_VO);
        orderProjectVO.setReturnCount(count); //重新设置一下用户修改的数量
        LoginMemberVO loginMemberVO = (LoginMemberVO) session.getAttribute(CrowdConstants.LOGIN_MEMBER_VO);
        Integer id = loginMemberVO.getId();
        ReturnJson<List<AddressVO>> addressByMemberIdListRemote = mySqlServerRemote.getAddressByMemberIdListRemote(id);
        //重新设置进去
        session.setAttribute(CrowdConstants.ORDER_PROJECT_VO,orderProjectVO);
        if (addressByMemberIdListRemote.getStatus()) {
            List<AddressVO> addressVOList = addressByMemberIdListRemote.getData();
            model.addAttribute(CrowdConstants.ADDRESS_LIST, addressVOList);
        }
        //返回页面
        return "order-confirm";
    }
}
