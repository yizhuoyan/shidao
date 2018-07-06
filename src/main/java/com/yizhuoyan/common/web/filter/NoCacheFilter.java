
package com.yizhuoyan.common.web.filter;

import javax.servlet.FilterChain;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author root@yizhuoyan.com
 */
@WebFilter("/*")
public class NoCacheFilter extends AbstractFilterSupport {
@Override
public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws Exception{
  resp.setDateHeader("Expires", 0);
  resp.setHeader("Cache-Control", "no-cache");
  resp.setHeader("Pragma", "no-cache");
  chain.doFilter(req, resp);
}
}
