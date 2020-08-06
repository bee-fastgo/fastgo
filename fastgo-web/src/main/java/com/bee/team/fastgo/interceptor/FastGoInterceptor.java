////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.bee.team.fastgo.interceptor;
//
//import com.bee.team.fastgo.common.CommonLoginValue;
//import com.spring.simple.development.core.annotation.base.NoLogin;
//import com.spring.simple.development.core.annotation.base.SimpleInterceptor;
//import com.spring.simple.development.support.exception.GlobalException;
//import com.spring.simple.development.support.exception.GlobalResponseCode;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ObjectUtils;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
///**
// * @author luke
// */
//@Component
//public class FastGoInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
//        if (handler instanceof HandlerMethod) {
//            HandlerMethod method = (HandlerMethod) handler;
//            NoLogin noLogin = method.getMethodAnnotation(NoLogin.class);
//            if (noLogin != null) {
//                return true;
//            }
//        }
//
//        HttpSession session = httpServletRequest.getSession();
//        if (ObjectUtils.isEmpty(session.getAttribute(CommonLoginValue.SESSION_LOGIN_KEY))) {
//            throw new GlobalException(GlobalResponseCode.SYS_NO_LOGIN);
//        }
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
//
//    }
//}
