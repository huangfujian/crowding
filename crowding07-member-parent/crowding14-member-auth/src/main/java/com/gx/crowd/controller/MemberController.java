package com.gx.crowd.controller;

import com.gx.crowd.api.MySqlServerRemote;
import com.gx.crowd.api.RedisServerRemote;
import com.gx.crowd.config.ShortMessageProperties;
import com.gx.crowd.entity.vo.LoginMemberVO;
import com.gx.crowd.entity.vo.MemberVO;
import com.gx.crowd.entity.vo.ReturnJson;
import com.gx.crowd.utils.CrowdConstants;
import com.gx.crowd.utils.CrowdUtils;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/12 14:35
 */
@Controller
@Slf4j
public class MemberController {
    @Autowired
    private MySqlServerRemote mySqlServerRemote;
    @Autowired
    private RedisServerRemote redisServerRemote;
    @Autowired
    private ShortMessageProperties shortMessageProperties;

    @PostMapping("/auth/do/login")
    public String doLogin(String loginAcct,
                          String userPswd,
                          Model model, HttpSession session) {
        ReturnJson<MemberVO> memberVORemote = mySqlServerRemote.getMemberVORemote(loginAcct);
        if (memberVORemote.getStatus()) {
            MemberVO memberVO = memberVORemote.getData();
            if (memberVO != null) {
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                boolean matches = bCryptPasswordEncoder.matches(userPswd, memberVO.getUserpswd());
                if (matches) {
                    //登录成功跳转页面
                    LoginMemberVO loginMemberVO = new LoginMemberVO();
                    BeanUtils.copyProperties(memberVO, loginMemberVO);
                    session.setAttribute(CrowdConstants.LOGIN_MEMBER_VO, loginMemberVO);
                    //重定向到会员中心页面
                    return "redirect:http://localhost/auth/center/page";
                } else {
                    model.addAttribute(CrowdConstants.MESSAGE, CrowdConstants.USERPSWD_FAIL);
                }
            } else {
                model.addAttribute(CrowdConstants.MESSAGE, CrowdConstants.LOGINACCT_NON_EXISTENCE);
            }
        } else {
            model.addAttribute(CrowdConstants.MESSAGE, memberVORemote.getMsg());
        }
        return "member-login";
    }
    /**
     * 退出登录
     *
     * @param session
     * @return
     */
    @GetMapping("/auth/do/logout")
    public String doLogOut(HttpSession session) {
        session.invalidate();
        //回到主页面
        return "redirect:http://localhost/";
    }
    /**
     * 注册账户
     *
     * @param memberVO
     * @param model
     * @return
     */
    @PostMapping("/auth/do/reg")
    public String register(MemberVO memberVO, Model model) {
        String key = memberVO.getPhoneNum() + memberVO.getCode();
        ReturnJson<String> redisKeyValueRemote = redisServerRemote.getRedisKeyValueRemote(key);
        if (redisKeyValueRemote.getStatus()) {
            String redisCode = redisKeyValueRemote.getData();
            if (!StringUtils.isEmpty(redisCode)) {
                //找到验证码说明，输入的验证码正确
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                String userPswd = memberVO.getUserpswd();
                //进行密码加密
                String encodePswd = bCryptPasswordEncoder.encode(userPswd);
                memberVO.setUserpswd(encodePswd);
                ReturnJson<String> result = mySqlServerRemote.saveMemberVORemote(memberVO);
                if (result.getStatus()) {
                    //注册成功
                    return "redirect:http://localhost/auth/login/page";//重定向到登录页面
                } else {
                    //注册
                    model.addAttribute(CrowdConstants.MESSAGE, result.getMsg());
                    log.error(result.getMsg());//打印错误
                }
            } else {
                model.addAttribute(CrowdConstants.MESSAGE, CrowdConstants.CODE_EXPIRED_FAIL);
            }
        } else {
            log.error(redisKeyValueRemote.getMsg());//记录日志
            model.addAttribute(CrowdConstants.MESSAGE, CrowdConstants.SYSTEM_BUSY);
        }
        return "member-reg"; //注册页面
    }

    /**
     * 发送短信
     *
     * @return
     */
    @RequestMapping("/auth/short/message")
    @ResponseBody
    public ReturnJson<String> sendShortMessage(@RequestParam("phoneNum") String phoneNum) {
        ReturnJson returnJson = CrowdUtils.sendMessage(
                shortMessageProperties.getAccessKeyId(),
                shortMessageProperties.getAccessKeySecret(),
                shortMessageProperties.getDomain(),
                shortMessageProperties.getSignName(),
                shortMessageProperties.getTemplateCode(),
                phoneNum);
        if (returnJson.getStatus()) {
            //判断是否发送成功
            String code = (String) returnJson.getData();
            String key = phoneNum + code;
            //保存到redis中
            ReturnJson<String> saveRedis = redisServerRemote.setRedisKeyValueRemoteTimeOut(key, code, 15L, TimeUnit.MINUTES);
            if (saveRedis.getStatus()) {
                //保存成功
                return ReturnJson.returnSuccess();
            } else {
                log.error(saveRedis.getMsg());
                //保存失败
                return ReturnJson.returnFail();
            }
        } else {
            //发送失败
            log.error(returnJson.getMsg());
            return ReturnJson.returnFail();
        }
    }
}
