package com.wj.bookmanager.interceptor;

import com.wj.bookmanager.model.Ticket;
import com.wj.bookmanager.model.User;
import com.wj.bookmanager.service.TicketService;
import com.wj.bookmanager.service.UserService;
import com.wj.bookmanager.utils.ConcurrentUtils;
import com.wj.bookmanager.utils.CookieUtils;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Created by nowcoder on 2018/08/07 下午5:06
 */
@Component
public class HostInfoInterceptor implements HandlerInterceptor {

  @Autowired
  private TicketService ticketService;

  @Autowired
  private UserService userService;

  /**
   * 为注入host信息
   *
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    String t = CookieUtils.getCookie("t", request);
    if (!StringUtils.isEmpty(t)) {
      Ticket ticket = ticketService.getTicket(t);
      if (ticket != null && ticket.getExpiredAt().after(new Date())) {
        User host = userService.getUser(ticket.getUserId());
        ConcurrentUtils.setHost(host);
      }
    }
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    ConcurrentUtils.clearHost();
  }

}
