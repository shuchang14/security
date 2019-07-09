package com.shu.securitydemo.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shu.securitydemo.entity.OnlineEntity;
import com.shu.securitydemo.entity.UserDetailEntity;
import com.shu.securitydemo.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        //response.getWriter().write(objectMapper.writeValueAsString("登录成功"));
        UserDetailEntity userInfo = (UserDetailEntity)authentication.getPrincipal();
        OnlineEntity onlineUser= new OnlineEntity();
        onlineUser.setSessionId(request.getSession().getId());
        onlineUser.setUserCode(userInfo.getUsername());
        //onlineUser.set(userInfo.getUser().getUserName());
       // onlineUser.setStatus("在线");
        SecurityUtil.onlineMap.put(userInfo.getUsername(),onlineUser);//把当前登录对象放入在线用户map容器中。 liangsc 2019-07-08
        response.sendRedirect("/toIndex");
    }
}
