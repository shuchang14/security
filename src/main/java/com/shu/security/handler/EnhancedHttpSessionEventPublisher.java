package com.shu.security.handler;

import com.shu.security.dto.OnlineEntity;
import com.shu.security.util.SecurityUtil;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.servlet.http.HttpSessionEvent;
import java.util.Set;

/**
 *Session监听器 监听session创建和销毁    liangsc
 */
public class EnhancedHttpSessionEventPublisher extends HttpSessionEventPublisher {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        System.out.println("**********Created*************");
        super.sessionCreated(event);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        System.out.println("**********Destroyed*************");
        String sessionId = event.getSession().getId();
        Set<String> keys = SecurityUtil.onlineMap.keySet();
        if(keys!=null && keys.size()>0){
            for(String key:keys){
                OnlineEntity onlineUserEntity=SecurityUtil.onlineMap.get(key);
                if(sessionId.equals(onlineUserEntity.getSessionId())){
                    SecurityUtil.onlineMap.remove(onlineUserEntity.getUserCode());
                    break;
                }
            }
        }
        super.sessionDestroyed(event);
    }
}
