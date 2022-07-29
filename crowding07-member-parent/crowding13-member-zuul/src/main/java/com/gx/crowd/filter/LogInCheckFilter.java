package com.gx.crowd.filter;

import com.gx.crowd.entity.vo.LoginMemberVO;
import com.gx.crowd.utils.CrowdConstants;
import com.gx.crowd.utils.CrowdUtils;
import com.gx.crowd.utils.ResourceAccess;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/12 19:25
 */
@Component
public class LogInCheckFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String servletPath = request.getServletPath();
        return ResourceAccess.checkResources(servletPath);
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpServletResponse response = currentContext.getResponse();
        HttpSession session = request.getSession();
        session.removeAttribute(CrowdConstants.MESSAGE);//清除一下
        LoginMemberVO loginMemberVO = (LoginMemberVO) session.getAttribute(CrowdConstants.LOGIN_MEMBER_VO);
        if (ObjectUtils.isEmpty(loginMemberVO)) {
            session.setAttribute(CrowdConstants.MESSAGE, CrowdConstants.UNAUTHORIZED_ACCESS);
            try {
                response.sendRedirect("/auth/login/page");//重定向
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
