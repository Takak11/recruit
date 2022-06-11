package com.takaki.recruit.filter;

import com.takaki.recruit.common.JwtToken;
import com.takaki.recruit.constant.JwtConstant;
import com.takaki.recruit.constant.ResponseStateEnum;
import com.takaki.recruit.exception.BusinessBaseException;
import com.takaki.recruit.utils.JwtUtil;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Takaki
 * @date 2022/6/11
 */
public class LoginFilter extends BasicHttpAuthenticationFilter {
    /**
     * 检查是否为登录请求
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {

        return ((HttpServletRequest) request).getHeader("Authorization") != null;
    }

    /**
     * 提交给realm验证token合法性，进行登录
     *
     * @throws Exception 当token不合法时或无效时抛出异常。
     *                   由于在Filter中抛出异常无法被全局异常处理器捕获，故发生异常时转发至Controller处理，由Controller抛出
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {

        HttpServletRequest req = (HttpServletRequest) request;
        String authorizationHeader = req.getHeader(JwtConstant.AUTH_HEADER);
        String token = null;
        try {
            token = JwtUtil.getFormattedToken(authorizationHeader);
            boolean isValid = JwtUtil.verifyToken(token);
            if (!isValid) {
                throw new BusinessBaseException(ResponseStateEnum.UNAUTHORIZED.getCode(), "登陆状态失效，请重新登录");
            }
            JwtToken jwtToken = new JwtToken(token);
            getSubject(request, response).login(jwtToken);

            return true;
        } catch (Exception e) {
            req.setAttribute("filter.e", e);
            req.getRequestDispatcher("/api/recruit/global/error").forward(request, response);

            return false;
        }


    }

    /**
     * 跨域请求支持
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        res.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        res.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        res.setHeader("Access-Control-Allow-Headers", req.getHeader("Access-Control-Request-Headers"));

        if (req.getMethod().equals(RequestMethod.OPTIONS.name())) {
            res.setStatus(HttpStatus.OK.value());
            return false;
        }

        return super.preHandle(request, response);
    }
}
