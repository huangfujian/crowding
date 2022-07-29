package com.gx.crowd.service.impl;

import com.gx.crowd.entity.po.MemberPO;
import com.gx.crowd.entity.po.MemberPOExample;
import com.gx.crowd.entity.vo.MemberVO;
import com.gx.crowd.mapper.MemberPOMapper;
import com.gx.crowd.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/12 15:02
 */
@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberPOMapper memberPOMapper;

    @Override
    public MemberVO getMemberVO(String loginAcct) {
        MemberPOExample memberPOExample = new MemberPOExample();
        memberPOExample.createCriteria().andLoginacctEqualTo(loginAcct);
        List<MemberPO> memberPOList = memberPOMapper.selectByExample(memberPOExample);
        if (memberPOList != null && memberPOList.size() != 0) {
            MemberPO memberPO = memberPOList.get(0);
            MemberVO memberVO = new MemberVO();
            BeanUtils.copyProperties(memberPO, memberVO);
            return memberVO;
        }
        return null;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void saveMemberVO(MemberVO memberVO) {
        MemberPO memberPO = new MemberPO();
        BeanUtils.copyProperties(memberVO, memberPO);
        memberPOMapper.insertSelective(memberPO);
    }
}
