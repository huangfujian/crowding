package com.gx.crowd.service;

import com.gx.crowd.entity.vo.MemberVO;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/12 15:02
 */
public interface MemberService {
    MemberVO getMemberVO(String loginAcct);

    void saveMemberVO(MemberVO memberVO);
}
