package com.gx.crowd.controller;

import com.gx.crowd.entity.vo.MemberVO;
import com.gx.crowd.entity.vo.ProjectVO;
import com.gx.crowd.entity.vo.ReturnJson;
import com.gx.crowd.service.MemberService;
import com.gx.crowd.utils.CrowdConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/12 15:01
 */
@RestController
public class MemberController {
    @Autowired
    private MemberService memberService;

    @RequestMapping("/get/member/vo/remote")
    public ReturnJson<MemberVO> getMemberVORemote(@RequestParam("loginAcct") String loginAcct) {
        try {
            MemberVO memberVO = memberService.getMemberVO(loginAcct);
            return ReturnJson.returnSuccessWithData(memberVO);
        } catch (Exception e) {
            return ReturnJson.returnSuccessMsg(e.getMessage());
        }
    }

    @RequestMapping("/save/member/vo/remote")
    public ReturnJson<String> saveMemberVORemote(@RequestBody MemberVO memberVO) {
        try {
            memberService.saveMemberVO(memberVO);
            return ReturnJson.returnSuccess();
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                return ReturnJson.returnFail(CrowdConstants.LOGINACCT_REPEAT);
            }
            return ReturnJson.returnFail(e.getMessage());
        }
    }
}
