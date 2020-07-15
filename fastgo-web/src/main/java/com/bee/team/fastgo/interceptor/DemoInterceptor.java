//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bee.team.fastgo.interceptor;

import com.spring.simple.development.core.annotation.base.SimpleInterceptor;
import com.spring.simple.development.core.baseconfig.idempotent.IdempotentHandler;
import com.bee.team.fastgo.service.TestDemoBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@SimpleInterceptor
public class DemoInterceptor implements HandlerInterceptor {
    @Autowired
    private TestDemoBo testDemoBo;
    public DemoInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
        IdempotentHandler.clearIdempotentModel();
    }
}
