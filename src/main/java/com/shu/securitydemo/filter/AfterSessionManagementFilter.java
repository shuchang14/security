package com.shu.securitydemo.filter;

import com.shu.securitydemo.entity.OnlineEntity;
import com.shu.securitydemo.entity.UserDetailEntity;
import com.shu.securitydemo.util.SecurityUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 在SessionManagementFilter 拦截器之后，获取当前会话，更新最后访问时间。  liangsc   2019-07-08
 */
public class AfterSessionManagementFilter extends OncePerRequestFilter implements InitializingBean {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String userCode=null;
        Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!principal.equals("anonymousUser")){
            UserDetailEntity userInfo = (UserDetailEntity)principal;
            if(userInfo.getUser()!=null){
                userCode=userInfo.getUsername();
            }
        }
        if(SecurityUtil.onlineMap.containsKey(userCode)) {
            //已登录，更新最后访问时间
            OnlineEntity onlineUserEntity = SecurityUtil.onlineMap.get(userCode);
            onlineUserEntity.setLastVisitTime(new Date());
            SecurityUtil.onlineMap.put(userCode, onlineUserEntity);
        }
        filterChain.doFilter(request,response);
    }
}
