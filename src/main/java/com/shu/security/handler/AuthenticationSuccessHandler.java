package com.shu.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shu.security.entity.OnlineEntity;
import com.shu.security.entity.UserDetailEntity;
import com.shu.security.util.SecurityUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        //response.getWriter().write(objectMapper.writeValueAsString("登录成功"));
        UserDetailEntity userInfo = (UserDetailEntity)authentication.getPrincipal();
        OnlineEntity onlineUser= getOnlineUser(request);
        onlineUser.setSessionId(request.getSession().getId());
        onlineUser.setUserCode(userInfo.getUsername());
        onlineUser.setLoginTime(new Date());
        onlineUser.setUserName(userInfo.getUser().getUserName());
        //onlineUser.set(userInfo.getUser().getUserName());
       // onlineUser.setStatus("在线");
        SecurityUtil.onlineMap.put(userInfo.getUsername(),onlineUser);//把当前登录对象放入在线用户map容器中。 liangsc 2019-07-08
        for(int i=1;i<100;i++){
            OnlineEntity onlineUser0= new OnlineEntity();
            onlineUser0.setSessionId(request.getSession().getId()+i);
            onlineUser0.setUserCode(userInfo.getUsername()+i);
            onlineUser0.setUserName(userInfo.getUser().getUserName());
            onlineUser0.setIp(onlineUser.getIp());
            onlineUser0.setBrowser(onlineUser.getBrowser());
            onlineUser0.setOsName(onlineUser.getOsName());
            onlineUser0.setLoginAddr(onlineUser.getLoginAddr());
            onlineUser0.setLoginTime(new Date(onlineUser.getLoginTime().getTime()+i));
            SecurityUtil.onlineMap.put(onlineUser0.getUserCode(),onlineUser0);
        }
        response.sendRedirect("/security/toIndex");
    }
    /**
     * 获取当前登录请求信息：ip、设备 信息
     * liangsc
     * 2019-07-08
     * @param request
     * @return
     */
    private OnlineEntity getOnlineUser(HttpServletRequest request){
        OnlineEntity onlineUser = new OnlineEntity();
        String ua = request.getHeader("User-Agent").toLowerCase();
        System.out.println(ua);
        //转成UserAgent对象
        UserAgent userAgent = UserAgent.parseUserAgentString(ua);
        //获取浏览器信息
        Browser browser = userAgent.getBrowser();
        //获取系统信息
        OperatingSystem os = userAgent.getOperatingSystem();

        onlineUser.setBrowser(browser.getName());//浏览器名称
        onlineUser.setOsName(os.getName());//系统名称
        String ip=getIpAddr(request);
        onlineUser.setIp(ip);//ip地址
        String addrStr=null;
        try{
            addrStr= getIpInfo(ip,null);
        }catch (Exception e){
        }
        if(addrStr!=null){
            JSONObject jsonObject = JSONObject.parseObject(addrStr);
            Integer code= (Integer) jsonObject.get("code");
            if(code==0){
                JSONObject dataJsonObject =JSONObject.parseObject(jsonObject.get("data").toString());
                onlineUser.setLoginAddr(dataJsonObject.get("region")+""+dataJsonObject.get("city")+"");//登录地点
            }
        }
        onlineUser.setLoginTime(new Date());//登录时间
        return  onlineUser;
    }
    public String getIpInfo(String ip, String encoding) {
        if(encoding==null)
            encoding="UTF-8";
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL("http://ip.taobao.com/service/getIpInfo.php");
            connection = (HttpURLConnection) url.openConnection(); // 新建连接实例
            connection.setConnectTimeout(2000); // 设置连接超时时间，单位毫秒
            connection.setReadTimeout(2000); // 设置读取数据超时时间，单位毫秒
            connection.setDoOutput(true); // 是否打开输出流 true|false
            connection.setDoInput(true); // 是否打开输入流true|false
            connection.setRequestMethod("POST"); // 提交方法POST|GET
            connection.setUseCaches(false); // 是否缓存true|false
            connection.connect(); // 打开连接端口
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());// 打开输出流往对端服务器写数据
            out.writeBytes("ip="+ip); // 写数据,也就是提交你的表单 name=xxx&pwd=xxx
            out.flush(); // 刷新
            out.close(); // 关闭输出流
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));// 往对端写完数据对端服务器返回数据
            // ,以BufferedReader流来读取
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect(); // 关闭连接
            }
        }
        return null;
    }

    public String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress==null || ipAddress.length()==0 || "unknown".equalsIgnoreCase(ipAddress)){
                ipAddress = request.getHeader("HTTP_CLIENT_IP");
            }if (ipAddress==null || ipAddress.length()==0 || "unknown".equalsIgnoreCase(ipAddress)){
                ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ipAddress==null || ipAddress.length()==0 || "unknown".equalsIgnoreCase(ipAddress)){
                ipAddress = request.getRemoteAddr();
            }

            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress="";
        }
        // ipAddress = this.getRequest().getRemoteAddr();

        return ipAddress;
    }
}
