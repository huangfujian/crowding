package com.gx.crowd.service.impl;

import com.gx.crowd.entity.po.AddressPO;
import com.gx.crowd.entity.po.AddressPOExample;
import com.gx.crowd.entity.po.OrderPO;
import com.gx.crowd.entity.po.OrderProjectPO;
import com.gx.crowd.entity.vo.AddressVO;
import com.gx.crowd.entity.vo.OrderProjectVO;
import com.gx.crowd.entity.vo.OrderVO;
import com.gx.crowd.mapper.AddressPOMapper;
import com.gx.crowd.mapper.OrderPOMapper;
import com.gx.crowd.mapper.OrderProjectPOMapper;
import com.gx.crowd.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/13 12:00
 */
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderProjectPOMapper orderProjectPOMapper;
    @Resource
    private OrderPOMapper orderPOMapper;
    @Resource
    private AddressPOMapper addressPOMapper;

    @Override
    public OrderProjectVO getOrderProjectVO(Integer returnId) {
        return orderProjectPOMapper.selectOrderProjectVO(returnId);
    }

    @Override
    public List<AddressVO> getAddressList(Integer id) {
        AddressPOExample addressPOExample = new AddressPOExample();
        addressPOExample.createCriteria().andMemberIdEqualTo(id);
        List<AddressPO> addressPOList = addressPOMapper.selectByExample(addressPOExample);
        List<AddressVO> addressVOList = new ArrayList<>();
        for (AddressPO addressPO : addressPOList) {
            AddressVO addressVO = new AddressVO();
            BeanUtils.copyProperties(addressPO, addressVO);
            addressVOList.add(addressVO);
        }
        return addressVOList;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void saveOrderVO(OrderVO orderVO) {
        OrderPO orderPO = new OrderPO();
        BeanUtils.copyProperties(orderVO, orderPO);
        orderPOMapper.insertSelective(orderPO);
        OrderProjectVO orderProjectVO = orderVO.getOrderProjectVO();
        OrderProjectPO orderProjectPO = new OrderProjectPO();
        BeanUtils.copyProperties(orderProjectVO, orderProjectPO);
        orderProjectPO.setOrderId(orderPO.getId());
        orderProjectPOMapper.insertSelective(orderProjectPO);
    }
}
