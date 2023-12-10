package com.example.common.interceptor;

import com.example.member.controller.MemberController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String requestURI = request.getRequestURI();
        log.info("Request URI : {}", requestURI);
        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute(MemberController.SessionConst.LOGIN_MEMBER) == null)
        {
            log.info("미인증 사용자 요청");
            response.sendRedirect("/signin?redirectURL="+requestURI);
            return false;
        }
        return true;
    }
}
