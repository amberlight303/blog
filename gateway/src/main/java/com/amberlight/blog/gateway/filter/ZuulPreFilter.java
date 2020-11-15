package com.amberlight.blog.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Base64;

@Component
public class ZuulPreFilter extends ZuulFilter {

        @Autowired
        private SessionRepository repository;

        @Override
        public String filterType() {
                return FilterConstants.PRE_TYPE;
        }

        @Override
        public int filterOrder() {
                return 0;
        }

        @Override
        public boolean shouldFilter() {

                return true;
        }

        @Override
        public Object run() {
                RequestContext context = RequestContext.getCurrentContext();
                HttpSession httpSession = context.getRequest().getSession();
                httpSession.setAttribute(
                        HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                        SecurityContextHolder.getContext()
                );
                repository.findById(httpSession.getId());
                context.addZuulRequestHeader("cookie", "SESSION=" + base64Encode(httpSession.getId()));
                context.getZuulRequestHeaders().put("X-Forwarded-For", context.getRequest().getRemoteAddr());
                return null;
        }

        private static String base64Encode(String value) {
                byte[] encodedCookieBytes = Base64.getEncoder().encode(value.getBytes());
                return new String(encodedCookieBytes);
        }

}
