package com.yizhuoyan.shidao.common.web.springmvc;

import com.yizhuoyan.shidao.common.exception.PlatformException;
import com.yizhuoyan.shidao.platform.entity.UserContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class AbstractControllerSupport{

private static String SESSION_KEY_CURRENT_USER = "CURRENT_USER_CONTEXT";

protected final Log log = LogFactory.getLog(this.getClass());


protected UserContext getCurrentUser(){
  HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
  HttpSession session = req.getSession(false);
  if(session!=null){
    UserContext userContext = (UserContext)session.getAttribute(SESSION_KEY_CURRENT_USER);
    if(userContext!=null){
      return userContext;
    }
  }
  throw new PlatformException("not-login-in");
}

protected void saveCurrentUser(UserContext uc){
  HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
  req.getSession().setAttribute(SESSION_KEY_CURRENT_USER, uc);
}

}
