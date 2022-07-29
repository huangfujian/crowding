package com.gx.crowd.api;

import com.gx.crowd.entity.po.AddressPO;
import com.gx.crowd.entity.vo.*;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.ResultSet;
import java.util.List;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/12 14:58
 */
@FeignClient("gx-crowd-mysql")
public interface MySqlServerRemote {
    @RequestMapping("/get/member/vo/remote")
    ReturnJson<MemberVO> getMemberVORemote(@RequestParam("loginAcct") String loginAcct);

    @RequestMapping("/save/member/vo/remote")
    ReturnJson<String> saveMemberVORemote(@RequestBody MemberVO memberVO);

    @RequestMapping("/save/project/vo/remote")
    ReturnJson<String> saveProjectVORemote(@RequestBody ProjectVO projectVO);

    @RequestMapping("/get/project/type/remote")
    ReturnJson<List<ProjectTypeVO>> getProjectTypeRemote();

    @RequestMapping("/get/details/project/vo/remote")
    ReturnJson<DetailsProjectVO> getDetailsProjectVoRemote(@RequestParam("projectId") Integer projectId);

    @RequestMapping("/get/order/project/vo/remote")
    ReturnJson<OrderProjectVO> getOrderProjectVORemote(@RequestParam("returnId") Integer returnId);

    @RequestMapping("/get/address/by/memberid/list/remote")
    ReturnJson<List<AddressVO>> getAddressByMemberIdListRemote(@RequestParam("id") Integer id);

    @RequestMapping("/save/order/vo/remote")
    ReturnJson<String> saveOrderVORemote(@RequestBody OrderVO orderVO);

}
