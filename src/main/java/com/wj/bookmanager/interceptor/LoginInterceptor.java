package com.wj.bookmanager.interceptor;


import com.wj.bookmanager.model.Ticket;
import com.wj.bookmanager.service.TicketService;
import com.wj.bookmanager.utils.CookieUtils;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Created by nowcoder on 2018/08/07 下午4:13
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

  @Autowired
  private TicketService ticketService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    //没有t票，去登陆
    String t = CookieUtils.getCookie("t",request);
    if(StringUtils.isEmpty(t)){
      response.sendRedirect("/users/login");
      return false;
    }

    //无效t票，去登陆
    Ticket ticket = ticketService.getTicket(t);
    if(ticket == null){
      response.sendRedirect("/users/login");
      return false;
    }

    //过期t票，去登陆
    if(ticket.getExpiredAt().before(new Date())){
      response.sendRedirect("/users/login");
      return false;
    }

    return true;
  }
}
